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
 * @author kwang
 */
public class GreedyVoteL2HDDS implements IAlgorithm {
    private GlobalVariable g; // to represent the original graph
	private GlobalVariable gi; // to represent the graph at each round

	// parameters for fpt subroutine
	int k;
	int r;

	int momentRegretThreshold;

	GreedyVoteL2HDDS() {
	}

//	@Override
//	public void setKR(int k, int r) {
//		this.k = k;
//		this.r = r;
//	}

    @Override
    public void setKRM(int k, int r, int momentRegretThreshold) {
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
		 * some of gi is of own values
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
		 * the array to store dominating vertex and dominated vertices of each
		 * step in gi
		 */
		int[] giStepU = new int[k];
		Arrays.fill(giStepU, ConstantValue.IMPOSSIBLE_VALUE);
		int[] giStepV = new int[k];
		Arrays.fill(giStepV, ConstantValue.IMPOSSIBLE_VALUE);

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
			// gi.lab pointing to g.idx
			int giVIdx = AlgoUtil.getIdxByLab(gi, vIdx);

			if (vIdx != ConstantValue.IMPOSSIBLE_VALUE) {
				if (!idxDomed[vIdx]) {

					int uIdx = AlgoUtil.getHighestWeightNeighIdx(g, vIdx);
					if (uIdx != ConstantValue.IMPOSSIBLE_VALUE) {
						// if this vertex is not in the list, add it to vertex list

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

						if (p >= k) {
							p = p % k;
						}
						gStepWeight[p] = Arrays.copyOf(g.getIdxWeight(), g.getVerCnt());
						// adjust weight;
						AlgoUtil.adjustWeight(g, uIdx);

						giStepU[p] = giUIdx;
						giStepV[p] = giVIdx;
						p++;

						if (isMomentOfRegret(p, giStepV)) {

							// 1.copy gi -> gi*
							GlobalVariable giS = AlgoUtil.copyGraphInGloablVariable(gi);
							// 2.get d2 from stepU (since some positions of stepU could be null)
							int[] giSD2 = new int[k];
							Arrays.fill(giSD2, ConstantValue.IMPOSSIBLE_VALUE);
							int giD2Len = 0;
							for (int i = 0; i < k; i++) {
								if (giStepU[i] != ConstantValue.IMPOSSIBLE_VALUE) {
									giSD2[giD2Len++] = giStepU[i];
								}
							}

							// if (giD2Len <= r) {
							// continue;
							// }
							r = Math.min(r, giD2Len - 1);

							// 3.d1 = giSol\gi.stepU (d1=d\d2) in gi*
							int[] giSD1 = Util.set1Minus2(giIdxSol, giIdxSolSize, giSD2, giD2Len);
							int giSD1Len = giSD1.length;

							// 4. get N[d1] in gi*
							Set<Integer> d1GIStarNeigs = AlgoUtil.getCloseNeigs(giS, giSD1);

							// 5. c= v(gi*)\N[d1]
							int[] c = Util.set1Minus2(giS.getIdxLst(), giS.getVerCnt(), d1GIStarNeigs);

							// 6. b=N[d1]\d1
							int[] b = Util.set1Minus2(d1GIStarNeigs, giSD1, giSD1Len);

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

                                if (validCombin != null) {
									// giD2P is the replacement of giD2
									int[] giD2P = Util.convertCombinToIdxSet(validCombin, neigTypeDomingMap, giSD2);
									int giD2PLen = giD2P.length;

									/*
									 * the common part of giD2 and giD2P should be kept,
									 * the parts in d2 not in d2p should be removed,
									 * and that in d2p not in d2 should be added
									 */
									int[] giD2Rm = Util.set1Minus2(giSD2, giD2Len, giD2P, giD2PLen);
									int[] giD2Add = Util.set1Minus2(giD2P, giD2PLen, giSD2, giD2Len);

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
                                    idxSolSize = getIdxSolSize(idxSol, idxSolSize, d2Rm);

                                    // recover weight
									if (p >= k) {
										p = p % k;
									}
									g.setIdxWeight(gStepWeight[p]);

									/*
									 * N[d2ToRemove]\N[gi.sol\d2ToRemove]\giD2Add in gi:
									 * delete from gi
									 */
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
                                    giIdxSolSize = getIdxSolSize(giIdxSol, giIdxSolSize, giD2Rm);

                                    // the vertices to be added:
									/*
									 * for N[d2ToAdd] in g:
									 * domed=true;
									 * not necessary to process specially since they will change in adjustWeight;
									 */
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
									// reset moment of regret
									p = 0;
									// stepU clean, stepV clean
									Arrays.fill(giStepU, ConstantValue.IMPOSSIBLE_VALUE);
									Arrays.fill(giStepV, ConstantValue.IMPOSSIBLE_VALUE);
									gStepWeight = new float[k][];


                                    break; // jump out of the tryR loop

								}
                                /*
                                 * 12. if we can't find a r size solution,continue;
                                 */
                            }

                        }

					}

				} else {

					if (p >= k) {
						p = p % k;
					}
					// add vIdx to step[p]
					giStepV[p] = giVIdx;
					giStepU[p] = ConstantValue.IMPOSSIBLE_VALUE;
					gStepWeight[p] = Arrays.copyOf(g.getIdxWeight(), g.getVerCnt());
					p++;

				}

			}

		} while (!AlgoUtil.isAllDominated(g));
		
		g.setIdxSol(idxSol);
		g.setIdxSolSize(idxSolSize);
		
		//try to get a smaller solution (1 smaller)
		//int[] tmpIdxSol=AlgoUtil.minimal(g, 1);
		
		//g.setIdxSol(tmpIdxSol);
		//g.setIdxSolSize(tmpIdxSol.length);
	}

    static int getIdxSolSize(int[] idxSol, int idxSolSize, int[] d2Rm) {
        for (int vIdxRm : d2Rm) {
            int pos = Util.findPos(idxSol, idxSolSize, vIdxRm);
            int tmp = idxSol[pos];
            idxSol[pos] = idxSol[idxSolSize - 1];
            idxSol[idxSolSize - 1] = tmp;
            idxSolSize--;
        }
        return idxSolSize;
    }

    private boolean isMomentOfRegret(int p, int[] stepV) {
		/*
		 * 1.p==k:p previously points to the last element of step; 2.p>1:p
		 * pointing at
		 * least at 1 means there are at least 2 dominating vertices between
		 * current
		 * graph and graph we will go back;
		 */
		if ((p == k) && (p >=momentRegretThreshold)) return true;

		/*
		 * 1.p<k: p previously pointing a middle element of step;
		 * 2.step[p]==null: and
		 * the later element in step being not used; 3.p>1:p pointing at least
		 * at 1
		 * means there are at least 2 dominating vertices between current
		 * graph and
		 * graph we will go back;
		 */
		// we don't allow this to be true, otherwise, it always get a blank
		// d1 at the first k.
		if ((p < k) && (stepV[p] == ConstantValue.IMPOSSIBLE_VALUE) && (p > 1)) {
			return false;
		}

        /*

         * 1.p<k: p previously pointing a middle element of step;
         * 2.step[p]!=null: and
         * the later element in step having been used; 3.p>1:p pointing at least
         * at 1
         * means there are at least 2 dominating vertices between current graph
         * and
         * graph we will go back;

         */
        if ((stepV[p] != ConstantValue.IMPOSSIBLE_VALUE)) {
            return (p < k) && (p >=momentRegretThreshold);
        }

		return false;

	}
}
