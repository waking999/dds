package au.edu.cdu.dds.algo.ds;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import au.edu.cdu.dds.util.AlgoUtil;
import au.edu.cdu.dds.util.ConstantValue;
import au.edu.cdu.dds.util.GlobalVariable;
import au.edu.cdu.dds.util.Ruler;
import au.edu.cdu.dds.util.Util;

/**
 ** this class implements the greedy vote algorithm for dominating set, where the
 * order of weight is listed from lowest to highest. during the period, a new
 * graph starting from empty to the final graph step by step of adding vertices.
 * In addition, a dds fpt subroutine will be invoked.
 * 
 * @author kwang
 */
public class GreedyVoteL2HDDS implements IAlgorithm {
	GlobalVariable g; // to represent the original graph
	GlobalVariable gi; // to represent the graph at each round

	// parameters for fpt subroutine
	int k;
	int r;

	public GreedyVoteL2HDDS() {
	}

	@Override
	public void setKR(int k, int r) {
		this.k = k;
		this.r = r;
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
		AlgoUtil.adjustGIInitStatus(g, gi);
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
		int[] giStepV = new int[k];
		Arrays.fill(giStepV, ConstantValue.IMPOSSIBLE_VALUE);

		// /*
		// * the array to store dominating vertex and dominated vertices of each
		// * step in g
		// */
		// int[] gStepU = new int[k];
		// Arrays.fill(gStepU, ConstantValue.IMPOSSIBLE_VALUE);
		// int[] gStepV = new int[k];
		// Arrays.fill(gStepV, ConstantValue.IMPOSSIBLE_VALUE);
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
			int giVIdx = AlgoUtil.getIdxByLab(gi, vIdx);

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

						if (p >= k) {
							p = p % k;
						}
						gStepWeight[p] = Arrays.copyOf(g.getIdxWeight(), g.getVerCnt());
						// adjust weight;
						AlgoUtil.adjustWeight(g, uIdx);
						// store uIdx, vIdx to step[p+1];
						/*
						 * since uIdx, vIdx are stored gvi.verLst, we need get
						 * the index of them and put
						 * it in the step
						 */
						// Set<Integer> gviStepVSet = new HashSet<>();
						// Set<Integer> gvStepVSet = new HashSet<>();

						// gviStepVSet.add(AlgoUtil.getIdxByLab(gi, vIdx));
						// gvStepVSet.add(vIdx);

						giStepU[p] = giUIdx;
						giStepV[p] = giVIdx;

						// gStepU[p] = uIdx;
						// gStepV[p] = vIdx;
						// gStepWeight[p] = Arrays.copyOf(g.getIdxWeight(), g.getVerCnt());
						p++;

						if (isMomentOfRegret(p, giStepV)) {

							// 1.copy gi -> gi*
							GlobalVariable giS = AlgoUtil.copyGloablVariable(gi);
							// 2. d=gi*.sol <- gv.sol, idx->getIndexBy(idx)
							int[] giSIdxSol = giS.getIdxSol();
							Arrays.fill(giSIdxSol, ConstantValue.IMPOSSIBLE_VALUE);

							for (int i = 0; i < idxSolSize; i++) {
								giSIdxSol[i] = AlgoUtil.getIdxByLab(gi, idxSol[i]);
							}
							int giSIdxSolSize = g.getIdxSolSize();
							giS.setIdxSolSize(giSIdxSolSize);

							int[] giSD2 = new int[k];
							Arrays.fill(giSD2, ConstantValue.IMPOSSIBLE_VALUE);
							int giD2Len = 0;
							for (int i = 0; i < k; i++) {
								if (giStepU[i] != ConstantValue.IMPOSSIBLE_VALUE) {
									giSD2[giD2Len++] = giStepU[i];
								}
							}

							if (giD2Len <= r) {
								continue;
							}

							// 3.d1 = gi*Sol\gi.stepU (d1=d\d2) in gi*
							int[] giSD1 = Util.set1Minus2(giSIdxSol, giSIdxSolSize, giSD2, giD2Len);
							int giSD1Len = giSD1.length;

							// 4. get N[d1] in gi*
							Set<Integer> d1GIStarNeigs = getD1Neigs(giS, giSD1);

							// 5. c= v(gi*)\N[d1]
							int[] c = Util.set1Minus2(giS.getIdxLst(), giS.getVerCnt(), d1GIStarNeigs);

							// 6. b=N[d1]\d1
							int[] b = Util.set1Minus2(d1GIStarNeigs, giSD1, giSD1Len);

							// 7. r1: delete d1 from gi*
							for (int d1VIdx : giSD1) {
								AlgoUtil.deleteVertex(giS, d1VIdx);
							}

							/*
							 * 8. r2: if v in b and N(v) not intersect c, delete
							 * v from gi*
							 */
							b = ddsR2(giS, c, b);

							/*
							 * 9. r3: if edge (u,v) in b, delete the edge from
							 * gi*
							 */
							ddsR3(giS, b);

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

							getNeighType(giStarIdxAL, c, b, neigTypeDomedMap, neigTypeDomingMap);

							/*
							 * 11. choose all possible combinations of r out of
							 * the neighTypeDomedMap to check if there is a
							 * solution for dom-a-vc problem
							 */
							Ruler[] validCombin = domAVCBruteForce(neigTypeDomedMap);

							if (validCombin == null) {
								/*
								 * 12. if we can't find a r size solution,continue;
								 */

								continue;
							} else {
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

								int[] d2Rm = convertGIIdxToGIdx(giD2Rm, giLabLst);
								int[] d2Add = convertGIIdxToGIdx(giD2Add, giLabLst);

								// for the vertices to be removed：
								/*
								 * for N[d2Rm]\ N[g.sol\d2Rm ] in g:
								 * domed=false;
								 * added=false;
								 */
								// TestUtil.printGlobalVariableStatus(g);
								int[] idxSolToKeep = Util.set1Minus2(idxSol, idxSolSize, d2Rm, d2Rm.length);
								Set<Integer> idxSolToKeepNeigs = AlgoUtil.getCloseNeigs(g, idxSolToKeep);
								Set<Integer> d2RmNeigs = AlgoUtil.getCloseNeigs(g, d2Rm);
								int[] idxSetF = Util.set1Minus2(d2RmNeigs, idxSolToKeepNeigs);
								for (int vIdxF : idxSetF) {
									idxDomed[vIdxF] = false;
									idxAdded[vIdxF] = false;
								}
								// TestUtil.printGlobalVariableStatus(g);
								/*
								 * for d2Rm in g:
								 * remove from g.sol；
								 * solsize-d2Rm.size
								 */
								for (int vIdxRm : d2Rm) {
									int pos = Util.findPos(idxSol, idxSolSize, vIdxRm);
									int tmp = idxSol[pos];
									idxSol[pos] = idxSol[idxSolSize - 1];
									idxSol[idxSolSize - 1] = tmp;
									idxSolSize--;
								}

								// TestUtil.printGlobalVariableStatus(g);

								// recover weight
								if (p >= k) {
									p = p % k;
								}
								g.setIdxWeight(gStepWeight[p]);
								// TestUtil.printGlobalVariableStatus(g);

								/*
								 * N[d2ToRemove]\N[gi.sol\d2ToRemove]\giD2Add in gi:
								 * delete from gi
								 */
								// TestUtil.printGlobalVariableStatus(gi);
								int[] giIdxSolToKeep = Util.set1Minus2(giIdxSol, giIdxSolSize, giD2Rm, giD2Rm.length);
								Set<Integer> giIdxSolToKeepNeigs = AlgoUtil.getCloseNeigs(gi, giIdxSolToKeep);
								Set<Integer> giD2RmNeigs = AlgoUtil.getCloseNeigs(gi, giD2Rm);
								int[] giIdxSetD = Util.set1Minus2(giD2RmNeigs, giIdxSolToKeepNeigs);
								giIdxSetD = Util.set1Minus2(giIdxSetD, giIdxSetD.length, giD2Add, giD2Add.length);
								for (int vIdxD : giIdxSetD) {
									AlgoUtil.deleteVertex(gi, vIdxD);
								}
								// TestUtil.printGlobalVariableStatus(gi);

								/*
								 * for giD2Rm in gi:
								 * remove from gi.sol
								 * giSolSize-giD2Rm.size
								 */
								for (int vIdxRm : giD2Rm) {
									int pos = Util.findPos(giIdxSol, giIdxSolSize, vIdxRm);
									int tmp = giIdxSol[pos];
									giIdxSol[pos] = giIdxSol[giIdxSolSize - 1];
									giIdxSol[giIdxSolSize - 1] = tmp;
									giIdxSolSize--;
								}
								// TestUtil.printGlobalVariableStatus(gi);

								// the vertices to be added:
								/*
								 * for N[d2ToAdd] in g:
								 * domed=true;
								 */
								// TestUtil.printGlobalVariableStatus(g);
								Set<Integer> d2AddNeigs = AlgoUtil.getCloseNeigs(g, d2Add);
								for (int vIdxT : d2AddNeigs) {
									idxDomed[vIdxT] = true;
								}
								/* for d2ToAdd in g:
								 * added=true;
								 * add to sol
								 * adjustWeight;
								 */
								// TestUtil.printGlobalVariableStatus(g);
								for (int uIdxT : d2Add) {
									idxAdded[uIdxT] = true;
									AlgoUtil.adjustWeight(g, uIdxT);
									idxSol[idxSolSize++] = uIdxT;
								}
								g.setIdxSol(idxSol);
								g.setIdxSolSize(idxSolSize);
								// TestUtil.printGlobalVariableStatus(g);
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
								Arrays.fill(giStepV, ConstantValue.IMPOSSIBLE_VALUE);
								// Arrays.fill(gStepU, ConstantValue.IMPOSSIBLE_VALUE);
								// Arrays.fill(gStepV, ConstantValue.IMPOSSIBLE_VALUE);
								gStepWeight = new float[k][];
								// TestUtil.printGlobalVariableStatus(g);

								giD2P = null;
								giD2Rm = null;
								giD2Add = null;

							}

							giSD2 = null;
							neigTypeDomedMap = null;
							neigTypeDomingMap = null;
							b = null;
							c = null;
							giS = null;

						}

					}

				} else {

					if (p >= k) {
						p = p % k;
					}
					// add vIdx to step[p]
					giStepV[p] = giVIdx;
					giStepU[p] = ConstantValue.IMPOSSIBLE_VALUE;
					// gStepV[p] = vIdx;
					// gStepU[p] = ConstantValue.IMPOSSIBLE_VALUE;
					gStepWeight[p] = Arrays.copyOf(g.getIdxWeight(), g.getVerCnt());
					p++;

				}

			}

		} while (!AlgoUtil.isAllDominated(g));

		g.setIdxSol(idxSol);
		g.setIdxSolSize(idxSolSize);
	}

	private int[] convertGIIdxToGIdx(int[] giIdxSet, int[] giLabLst) {
		int[] gIdxSet = new int[giIdxSet.length];
		for (int i = 0; i < giIdxSet.length; i++) {
			gIdxSet[i] = giLabLst[giIdxSet[i]];
		}
		return gIdxSet;
	}

	private Set<Integer> getD1Neigs(GlobalVariable giStar, int[] d1) {
		int[][] giStarIdxAL = giStar.getIdxAL();
		int[] giStaIdxDegree = giStar.getIdxDegree();
		Set<Integer> d1GIStarNeigs = new HashSet<>();

		for (int d1VIdx : d1) {
			int[] d1VNeigs = giStarIdxAL[d1VIdx];
			d1GIStarNeigs.add(d1VIdx);
			for (int j = 0; j < giStaIdxDegree[d1VIdx]; j++) {
				int d1UIdx = d1VNeigs[j];
				d1GIStarNeigs.add(d1UIdx);
			}
		}
		return d1GIStarNeigs;

	}

	private int[] ddsR2(GlobalVariable giStar, int[] c, int[] b) {
		int[][] giStarIdxAL = giStar.getIdxAL();
		int[] gviStarIdxDegree = giStar.getIdxDegree();

		int cLen = c.length;
		int bLen = b.length;

		int[] bRem = new int[bLen];
		Arrays.fill(bRem, ConstantValue.IMPOSSIBLE_VALUE);
		int bRemLen = 0;
		for (int i = 0; i < bLen; i++) {
			int bVIdx = b[i];
			int[] bVNeigs = giStarIdxAL[bVIdx];
			int bVIdxDegree = gviStarIdxDegree[bVIdx];
			boolean isIntersected = Util.setsIntersect(bVNeigs, bVIdxDegree, c, cLen);
			if (!isIntersected) {
				bRem[bRemLen++] = bVIdx;
				AlgoUtil.deleteVertex(giStar, bVIdx);
			}

		}
		// remove the deleted vertices from b
		b = Util.set1Minus2(b, bLen, bRem, bRemLen);
		return b;
	}

	private void ddsR3(GlobalVariable giStar, int[] b) {
		int[][] giStarIdxAL = giStar.getIdxAL();
		int[] gviStarIdxDegree = giStar.getIdxDegree();
		int bLen = b.length;

		for (int i = 0; i < bLen; i++) {
			int bVIdx = b[i];
			int bVIdxDegree = gviStarIdxDegree[bVIdx];
			int[] bVNeigs = giStarIdxAL[bVIdx];
			for (int j = bVIdxDegree - 1; j >= 0; j--) {
				int bVNeigIdx = bVNeigs[j];
				AlgoUtil.deleteEdge(giStar, bVIdx, bVNeigIdx);
			}

		}
	}

	private void getNeighType(int[][] giStarIdxAL, int[] c, int[] b, Map<String, boolean[]> neigTypeDomedMap,
			Map<String, Set<Integer>> neigTypeDomingMap) {
		int cLen = c.length;
		/*
		 * because the vertices in the vertex cover can
		 * dominate other vertices in the
		 * vertex cover,they are considered
		 */
		for (int cVIdx : c) {

			int[] cVNeigs = giStarIdxAL[cVIdx];

			boolean ruler[] = new boolean[cLen];
			Arrays.fill(ruler, ConstantValue.UNMARKED);

			// the vertex can dominated by itself
			int pos = Util.findPos(c, cLen, cVIdx);
			ruler[pos] = ConstantValue.MARKED;
			for (int cVNeigIdx : cVNeigs) {
				/*
				 * the position of the dominated vertex in
				 * the vertex cover will set 1
				 * a neighbour of the vertex is likely not
				 * to be in the vertex cover
				 */
				pos = Util.findPos(c, cLen, cVNeigIdx);
				if (pos != ConstantValue.IMPOSSIBLE_VALUE) {
					ruler[pos] = ConstantValue.MARKED;
				}
			}

			String rulerStr = Util.arrayToString(ruler);
			if (!neigTypeDomedMap.containsKey(rulerStr)) {

				neigTypeDomedMap.put(rulerStr, ruler);
				Set<Integer> domingVerSet = new HashSet<>();
				domingVerSet.add(cVIdx);
				neigTypeDomingMap.put(rulerStr, domingVerSet);
			} else {
				Set<Integer> domingVerSet = neigTypeDomingMap.get(rulerStr);
				domingVerSet.add(cVIdx);
				neigTypeDomingMap.put(rulerStr, domingVerSet);
			}
		}

		/*
		 * because the vertices in the independent set can
		 * dominate vertices in the
		 * vertex cover,they are considered
		 */
		for (int bVIdx : b) {
			int[] bVNeigs = giStarIdxAL[bVIdx];

			boolean ruler[] = new boolean[cLen];
			Arrays.fill(ruler, ConstantValue.UNMARKED);

			for (int bVNeigIdx : bVNeigs) {
				/*
				 * the position of the dominate vertex in
				 * the vertex cover will set 1
				 */
				int pos = Util.findPos(c, cLen, bVNeigIdx);
				if (pos != ConstantValue.IMPOSSIBLE_VALUE) {
					ruler[pos] = ConstantValue.MARKED;
				}
			}

			String rulerStr = Util.arrayToString(ruler);
			if (!neigTypeDomedMap.containsKey(rulerStr)) {

				neigTypeDomedMap.put(rulerStr, ruler);
				Set<Integer> domingVerSet = new HashSet<>();
				domingVerSet.add(bVIdx);
				neigTypeDomingMap.put(rulerStr, domingVerSet);
			} else {
				Set<Integer> domingVerSet = neigTypeDomingMap.get(rulerStr);
				domingVerSet.add(bVIdx);
				neigTypeDomingMap.put(rulerStr, domingVerSet);
			}

		}
	}

	private Ruler[] domAVCBruteForce(Map<String, boolean[]> neigTypeDomedMap) {
		Ruler[] rulers = Util.converRulerMapToArray(neigTypeDomedMap);
		List<Ruler[]> allRCombins = Util.getAllRoutOfNCombines(rulers, r);
		boolean hasValidCombin = false;
		Ruler[] validCombin = null;
		for (Ruler[] combin : allRCombins) {
			hasValidCombin = Util.validCombin(combin);
			if (hasValidCombin) {
				validCombin = combin;
				break;
			}
		}
		return validCombin;
	}

	private boolean isMomentOfRegret(int p, int[] stepV) {
		/*
		 * 1.p==k:p previously points to the last element of step; 2.p>1:p
		 * pointing at
		 * least at 1 means there are at least 2 dominating vertices between
		 * current
		 * graph and graph we will go back;
		 */
		if ((p == k) && (p > 1)) {
			return true;
		}

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
		if ((p < k) && (stepV[p] != ConstantValue.IMPOSSIBLE_VALUE) && (p > 1)) {
			return true;
		}

		return false;

	}
}
