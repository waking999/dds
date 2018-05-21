package au.edu.cdu.problems.dds;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import au.edu.cdu.common.util.AlgoUtil;
import au.edu.cdu.common.util.ConstantValue;
import au.edu.cdu.common.util.GlobalVariable;
import au.edu.cdu.common.util.Util;
import au.edu.cdu.problems.IAlgorithm;

/**
 ** this class implements the greedy vote algorithm for dominating set, where the
 * order of weight is listed from lowest to highest. during the period, a new
 * graph starting from empty to the final graph step by step of adding vertices.
 * In addition, a dds fpt subroutine will be invoked.
 * the difference btw dds2 and dds is that:
 * in dds, we add v to new position whatever it is domed or not, which leads to
 * some position of stepU is null, which in turn may not get enough number of u
 * in k range to be bigger. On the other hand, the size of dom-a-vc will be
 * O(2k)
 * than r;
 * in dds2, we add v to existing position if it is domed, which will get filled
 * stepU without null value intervally. This may cause the size of dom-a-vc to
 * be O(dk). d is the max degree in the graph, it makes the size of dom-a-vc out
 * of control. So it is deprecated.
 * 
 * 
 * @author kwang
 */
@Deprecated
public class GreedyVoteL2HDDS2 implements IAlgorithm {
    private GlobalVariable g; // to represent the original graph
    private GlobalVariable gi; // to represent the graph at each round

	// parameters for fpt subroutine
	int k;
	int r;
	int momentRegretThreshold;

	GreedyVoteL2HDDS2() {
	}

	@Override
	public void setKRM(int k, int r,int momentRegretThreshold) {
		this.k = k;
		this.r = r;
		this.momentRegretThreshold=momentRegretThreshold;
	}

	@Override
	public void setGlobalVariable(GlobalVariable gv) {
		this.g = gv;

		// initialize the graph representing at each round
		this.gi = new GlobalVariable();
		int verCnt = gv.getVerCnt();
		AlgoUtil.initGlobalVariable(gi, verCnt);

		/*
		 * although most internal variables of gi are of the same value as g,
		 * some of gi
		 * is of own values
		 */
		AlgoUtil.adjustGIInitStatus(gi);
	}

	@Override
	public void compute() {
		int[] idxSol = g.getIdxSol();
		int idxSolSize = g.getIdxSolSize();
		boolean[] idxDomed = g.getIdxDomed();
		boolean[] idxAdded = g.getIdxAdded();

		int[] giIdxSol = gi.getIdxSol();
		int giIdxSolSize = gi.getIdxSolSize();

		/*
		 * the reason to use dual arrays in both gi and g is because there is a
		 * mapping between idx of gi and g (that is gi.lab pointing to g.idx).
		 * use such storage to save time to convert lab and idx forth and back
		 */
		/*
		 * the array to store dominating vertex and dominated vertices of each
		 * step in gi
		 */
		int[] giStepU = new int[k];
		Arrays.fill(giStepU, ConstantValue.IMPOSSIBLE_VALUE);
		// @SuppressWarnings("unchecked")
		// Set<Integer>[] giStepV = new HashSet[k];
		// Arrays.fill(giStepV, null);

		float[][] gStepWeight = new float[k][];

		int currentVCount = 0;
		int[] giIdxLst = gi.getIdxLst();

		int p = 0;
		do {

			int vIdx = AlgoUtil.getUnaddedLowestWeightVertexIdx(g);
			// add vIdx to gi;
			if (Util.findPos(giIdxLst, currentVCount, vIdx) == ConstantValue.IMPOSSIBLE_VALUE) {

				AlgoUtil.addVerToGI(g, gi, vIdx);
			}
			// int giVIdx = AlgoUtil.getIdxByLab(gi, vIdx);

			if (vIdx != ConstantValue.IMPOSSIBLE_VALUE) {
				if (!idxDomed[vIdx]) {

					int uIdx = AlgoUtil.getHighestWeightNeighIdx(g, vIdx);
					if (uIdx != ConstantValue.IMPOSSIBLE_VALUE) {
						/*
						 * if this vertex is not in the list, add it to vertex
						 * list
						 */
						// add uIdx to gi;
						if (Util.findPos(giIdxLst, currentVCount, uIdx) == ConstantValue.IMPOSSIBLE_VALUE) {
							AlgoUtil.addVerToGI(g, gi, uIdx);
						}

						int giUIdx = AlgoUtil.getIdxByLab(gi, uIdx);
						// add uIdx into solution
						idxSol[idxSolSize++] = uIdx;
						g.setIdxSolSize(idxSolSize);
						giIdxSol[giIdxSolSize++] = giUIdx;
						gi.setIdxSolSize(giIdxSolSize);

						gStepWeight[p] = Arrays.copyOf(g.getIdxWeight(), g.getVerCnt());
						// adjust weight;
						AlgoUtil.adjustWeight(g, uIdx);
						// store uIdx, vIdx to step[p+1];
						/*
						 * since uIdx, vIdx are stored gvi.verLst, we need get
						 * the index of them and put
						 * it in the step
						 */
						// Set<Integer> giStepVSet = new HashSet<>();
						// Set<Integer> gvStepVSet = new HashSet<>();

						// giStepVSet.add(giVIdx);
						// gvStepVSet.add(vIdx);

						giStepU[p] = giUIdx;
						// giStepV[p] = giStepVSet;

						// gStepU[p] = uIdx;
						// gStepV[p] = vIdx;

						p++;
						if (p >= k) {
							p = p % k;
						}

						if (isMomentOfRegret(p, giStepU)) {

							// 1.copy gi -> gi*
							GlobalVariable giS = AlgoUtil.copyGraphInGloablVariable(gi);

							int[] giD2;
							int giD2Len;
							if (giStepU[p] == ConstantValue.IMPOSSIBLE_VALUE) {
								giD2Len = r + 1;
								giD2 = Arrays.copyOfRange(giStepU, p - r - 1, p);

							} else {
								giD2 = giStepU;
								giD2Len = k;
							}

							// 3.d1 = giSol\gi.stepU (d1=d\d2) in gi*
							int[] giSD1 = Util.set1Minus2(giIdxSol, giIdxSolSize, giD2, giD2Len);
							int giSD1Len = giSD1.length;

							// 4. get N[d1] in gi*
							Set<Integer> d1GISNeigs = AlgoUtil.getCloseNeigs(giS, giSD1);

							// 5. c= v(gi*)\N[d1]
							int[] c = Util.set1Minus2(giS.getIdxLst(), giS.getVerCnt(), d1GISNeigs);

							// 6. b=N[d1]\d1
							int[] b = Util.set1Minus2(d1GISNeigs, giSD1, giSD1Len);

							// 7. r1: delete d1 from gi*
							DomAVC.ddsR1(giS, giSD1);

							/*
							 * 8. r2: if v in b and N(v) not intersect c, delete
							 * v from gi*
							 */
							b = DomAVC.ddsR2(giS, c, b);

							/*
							 * 9. r3: if edge (u,v) in b, delete the edge from
							 * gi*
							 */
							DomAVC.ddsR3(giS, b);

							/*
							 * c is a vertex cover and b is independent set
							 * after applying r1,r2,r3
							 */
							/*
							 * 10. get neighbor type:
							 * typeDomedMap (string key representing neigh type, a ruler representing
							 * dominated vertices of such type),
							 * typeDomingMap (string key representing neigh
							 * type, dominating vertices of such type)
							 */
							Map<String, boolean[]> neigTypeDomedMap = new HashMap<>();
							Map<String, Set<Integer>> neigTypeDomingMap = new HashMap<>();
							int[][] giStarIdxAL = giS.getIdxAL();

							DomAVC.getNeighType(giStarIdxAL, c, b, neigTypeDomedMap, neigTypeDomingMap);

							for (int tryR = 1; tryR <= r; tryR++) {
								/*
								 * 11. choose all possible combinations of r out of
								 * the neighTypeDomedMap to check if there is a
								 * solution for dom-a-vc problem
								 */
								String[] validCombin = DomAVC.domAVCBruteForce(neigTypeDomedMap, tryR);

								/*
								 * 12. if we can't find a r size solution,continue;
								 */
								if (validCombin != null) {

									// giD2P is the replacement of giD2
									int[] giD2P = Util.convertCombinToIdxSet(validCombin, neigTypeDomingMap, giD2);
									int giD2PLen = giD2P.length;

									/*
									 * the common part of giD2 and giD2P should be kept,
									 * the parts in d2 not in d2p should be removed,
									 * and that in d2p not in d2 should be added
									 */
									int[] giD2Rm = Util.set1Minus2(giD2, giD2Len, giD2P, giD2PLen);
									int[] giD2Add = Util.set1Minus2(giD2P, giD2PLen, giD2, giD2Len);

									int[] giLabLst = gi.getLabLst();

									int[] d2Rm = AlgoUtil.convertGIIdxToGIdx(giD2Rm, giLabLst);
									int[] d2Add = AlgoUtil.convertGIIdxToGIdx(giD2Add, giLabLst);

									// for the vertices to be removed：
									/*
									 * for N[d2Rm]\ N[g.sol\d2Rm ] in g:
									 * domed=false;
									 * added=false;
									 */

									int[] idxSolToKeep = Util.set1Minus2(idxSol, idxSolSize, d2Rm, d2Rm.length);
									Set<Integer> idxSolToKeepNeigs = AlgoUtil.getCloseNeigs(g, idxSolToKeep);
									Set<Integer> d2RmNeigs = AlgoUtil.getCloseNeigs(g, d2Rm);
									int[] idxSetF = Util.set1Minus2(d2RmNeigs, idxSolToKeepNeigs);
									for (int vIdxF : idxSetF) {
										idxDomed[vIdxF] = false;
										idxAdded[vIdxF] = false;
									}

									/*
									 * for d2Rm in g:
									 * remove from g.sol；
									 * solsize-d2Rm.size
									 */
                                    idxSolSize = GreedyVoteL2HDDS.getIdxSolSize(idxSol, idxSolSize, d2Rm);

                                    // recover weight
									g.setIdxWeight(gStepWeight[p]);

									/*
									 * N[d2ToRemove]\N[gi.sol\d2ToRemove]\giD2Add in gi:
									 * delete from gi
									 */
									// TestUtil.printGlobalVariableStatus(gi);
									int[] giIdxSolToKeep = Util.set1Minus2(giIdxSol, giIdxSolSize, giD2Rm,
											giD2Rm.length);
									Set<Integer> giIdxSolToKeepNeigs = AlgoUtil.getCloseNeigs(gi, giIdxSolToKeep);
									Set<Integer> giD2RmNeigs = AlgoUtil.getCloseNeigs(gi, giD2Rm);
									int[] giIdxSetD = Util.set1Minus2(giD2RmNeigs, giIdxSolToKeepNeigs);
									giIdxSetD = Util.set1Minus2(giIdxSetD, giIdxSetD.length, giD2Add, giD2Add.length);
									for (int vIdxD : giIdxSetD) {
										AlgoUtil.deleteVertex(gi, vIdxD);
									}

									/*
									 * for giD2Rm in gi:
									 * remove from gi.sol
									 * giSolSize-giD2Rm.size
									 */
                                    giIdxSolSize = GreedyVoteL2HDDS.getIdxSolSize(giIdxSol, giIdxSolSize, giD2Rm);

                                    // the vertices to be added:
									/*
									 * for N[d2ToAdd] in g:
									 * domed=true;
									 */

									// Set<Integer> d2AddNeigs = AlgoUtil.getCloseNeigs(g, d2Add);
									// for (int vIdxT : d2AddNeigs) {
									// idxDomed[vIdxT] = true;
									// }
									/*
									 * for d2ToAdd in g:
									 * added=true;
									 * add to sol;
									 * adjustWeight;
									 */

									for (int uIdxT : d2Add) {
										idxAdded[uIdxT] = true;
										AlgoUtil.adjustWeight(g, uIdxT);
										idxSol[idxSolSize++] = uIdxT;
									}
									g.setIdxSol(idxSol);
									g.setIdxSolSize(idxSolSize);

									/*
									 * for giD2ToAdd in gi:
									 * add to gisol
									 */
									for (int giUIdxT : giD2Add) {
										giIdxSol[giIdxSolSize++] = giUIdxT;
									}
									gi.setIdxSol(giIdxSol);
									gi.setIdxSolSize(giIdxSolSize);

									// 13.e gi.add N[d2ToAdd]
									// 13.d g.sol U N[d2ToAdd]
									// 13.e g.domed=true for
									// N[d2ToAdd->gi.getverbyidx] in g
									// 13.f g.added=true for
									// N[d2ToAdd->gi.getverbyidx] in g

									// 13.c.1 recover the weight for g
									// g.setIdxWeight(gStepWeight[p]);

									// reset moment of regret
									p = 0;
									// stepU clean, stepV clean
									Arrays.fill(giStepU, ConstantValue.IMPOSSIBLE_VALUE);
									// Arrays.fill(giStepV, null);

									gStepWeight = new float[k][];


									break; // jump out of the tryR loop
								}


							}



						}

					}

				}
				// else {
				// // add vIdx to step[p]
				// int preP = p - 1;
				// if (p == 0) {
				// preP = k - 1;
				// }
				// Set<Integer> giStepVSet = giStepV[preP];
				// giStepVSet.add(giVIdx);
				//
				// }

			}

		} while (!AlgoUtil.isAllDominated(g));

		g.setIdxSol(idxSol);
		g.setIdxSolSize(idxSolSize);
	}

	private boolean isMomentOfRegret(int p, int[] stepV) {
		// we can go back at least r+1 step and choose r out of r+1
		if (p > r + 1) {
			return true;
		}

		// we can go back k step and choose r out of k
        return (p <= r + 1) && (stepV[p] != ConstantValue.IMPOSSIBLE_VALUE);

    }
}
