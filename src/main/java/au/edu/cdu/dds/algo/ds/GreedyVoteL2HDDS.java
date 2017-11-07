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
		@SuppressWarnings("unchecked")
		Set<Integer>[] giStepV = new HashSet[k];

		/*
		 * the array to store dominating vertex and dominated vertices of each
		 * step in g
		 */
		int[] gStepU = new int[k];
		Arrays.fill(gStepU, ConstantValue.IMPOSSIBLE_VALUE);
		@SuppressWarnings("unchecked")
		Set<Integer>[] gStepV = new HashSet[k];

		int currentVCount = 0;
		int[] giIdxLst = gi.getIdxLst();

		int p = 0;
		do {

			int vIdx = AlgoUtil.getUnaddedLowestWeightVertexIdx(g);

			if (vIdx != ConstantValue.IMPOSSIBLE_VALUE) {
				if (!idxDomed[vIdx]) {

					int uIdx = AlgoUtil.getHighestWeightNeighIdx(g, vIdx);
					if (uIdx != ConstantValue.IMPOSSIBLE_VALUE) {
						/*
						 * if this vertex is not in the list, add it to vertex
						 * list
						 */
						// add uIdx to gvi;
						if (Util.findPos(giIdxLst, currentVCount, uIdx) == ConstantValue.IMPOSSIBLE_VALUE) {
							AlgoUtil.addVerToGI(g, gi, uIdx);
						}

						// add vIdx to gvi;
						if (Util.findPos(giIdxLst, currentVCount, vIdx) == ConstantValue.IMPOSSIBLE_VALUE) {
							AlgoUtil.addVerToGI(g, gi, vIdx);
						}

						// add uIdx into solution
						idxSol[idxSolSize++] = uIdx;
						g.setIdxSolSize(idxSolSize);

						// adjust weight;
						AlgoUtil.adjustWeight(g, uIdx);
						// store uIdx, vIdx to step[p+1];
						/*
						 * since uIdx, vIdx are stored gvi.verLst, we need get
						 * the index of them and put
						 * it in the step
						 */
						Set<Integer> gviStepVSet = new HashSet<>();
						Set<Integer> gvStepVSet = new HashSet<>();

						gviStepVSet.add(AlgoUtil.getIdxByLab(gi, vIdx));
						gvStepVSet.add(vIdx);

						if (p >= k) {
							p = p % k;
						}
						giStepU[p] = AlgoUtil.getIdxByLab(gi, uIdx);
						giStepV[p] = gviStepVSet;
						gStepU[p] = uIdx;
						gStepV[p] = gvStepVSet;
						p++;

						if (isMomentOfRegret(p, giStepV)) {

							// 1.copy gi -> gi*
							GlobalVariable giStar = AlgoUtil.copyGloablVariable(gi);
							// 2. d=gi*.sol <- gv.sol, idx->getIndexBy(idx)
							int[] giStarIdxSol = giStar.getIdxSol();
							Arrays.fill(giStarIdxSol, ConstantValue.IMPOSSIBLE_VALUE);
							 
							for (int i = 0; i < idxSolSize; i++) {
								giStarIdxSol[i] = AlgoUtil.getIdxByLab(gi, idxSol[i]);
							}
							int giStarIdxSolSize = g.getIdxSolSize();
							giStar.setIdxSolSize(giStarIdxSolSize);

							// 3.d1 = gi*Sol\gi.stepU (d\d2) in gi*
							int[] d1 = Util.set1Minus2(giStarIdxSol, giStarIdxSolSize, giStepU, k);
							int d1Len = d1.length;

							// if (d1Len == 0) {
							// continue;
							// }

							// 4. get N[d1] in gi*
							Set<Integer> d1GIStarNeigs = new HashSet<>();
							int[][] giStarIdxAL = giStar.getIdxAL();
							int[] giStaIdxDegree = giStar.getIdxDegree();

							for (int d1VIdx : d1) {
								int[] d1VNeigs = giStarIdxAL[d1VIdx];
								d1GIStarNeigs.add(d1VIdx);
								for (int j = 0; j < giStaIdxDegree[d1VIdx]; j++) {
									int d1UIdx = d1VNeigs[j];
									d1GIStarNeigs.add(d1UIdx);
								}
							}

							// 5. c= v(gi*)\N[d1]
							int[] c = Util.set1Minus2(giStar.getIdxLst(), giStar.getVerCnt(), d1GIStarNeigs);

							// 6. b=N[d1]\d1
							int[] b = Util.set1Minus2(d1GIStarNeigs, d1, d1Len);

							// 7. r1. delete d1 from gi*
							for (int d1VIdx : d1) {
								AlgoUtil.deleteVertex(giStar, d1VIdx);
							}
							// 8. r2. if v in b and N(v) not intersect c, delete
							// v from gi*
							int bLen = b.length;
							int cLen = c.length;
							int[] bRem = new int[bLen];
							Arrays.fill(bRem, ConstantValue.IMPOSSIBLE_VALUE);
							int bRemLen = 0;
							int[] gviStarIdxDegree = giStar.getIdxDegree();
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
							bLen = b.length;

							// 9. r3. if edge (u,v) in b, delete the edge from
							// gi*
							for (int i = 0; i < bLen; i++) {
								int bVIdx = b[i];
								int bVIdxDegree = gviStarIdxDegree[bVIdx];
								int[] bVNeigs = giStarIdxAL[bVIdx];
								for (int j = bVIdxDegree - 1; j >= 0; j--) {
									int bVNeigIdx = bVNeigs[j];
									AlgoUtil.deleteEdge(giStar, bVIdx, bVNeigIdx);
								}

							}

							/*
							 * c is a vertex cover and b is independent set
							 * after applying r1,r2,r3
							 */
							/*
							 * 10. get neighbor type:
							 * typeDomedMap (string key representing neigh type,
							 * a ruler representing dominated vertices of such
							 * type),
							 * typeDomingMap (string key representing neigh
							 * type, dominating vertices of such type)
							 */
							Map<String, boolean[]> neigTypeDomedMap = new HashMap<>();
							Map<String, Set<Integer>> neigTypeDomingMap = new HashMap<>();

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

							/*
							 * 11. choose all possible combinations of r out of
							 * the neighTypeDomedMap to check if there is a
							 * solution for dom-a-vc problem
							 */
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

							if (!hasValidCombin) {
								// 12. if we can't find a r size solution, continue;
								continue;
							} else {
								//d2Prime is the replacement of d2
								int[] d2Prime = Util.convertCombinToIdxSet(validCombin, neigTypeDomingMap);
								System.out.println(d2Prime);

								// 13.a gv.sol \ gv.stepU
								// 13.b gv.domed = false for N[gv.stepU] in gv
								// 13.c gv.added= false for N[gv.stepU] in gv
								// 13.c.1 resetweight(gv)
								// 13.d delete gvi.stepU,gvi.stepV from gvi
								// 13.e gvi.add N[d3]
								// 13.f gvi.stepU=d3, gvi.StepV=N[d3] staring
								// from 0
								// 13.d gv.sol U N[d3]
								// 13.e gv.domed=true for N[d3->getverbyidx] in
								// gv
								// 13.f gv.added=true for N[d3->getverbyidx] in
								// gv
							}

							neigTypeDomedMap=null;
							neigTypeDomingMap=null;
							b = null;
							c = null;
							giStar = null;

						}

					}

				} else {
					// add vIdx to gvi;
					if (Util.findPos(giIdxLst, currentVCount, vIdx) == ConstantValue.IMPOSSIBLE_VALUE) {

						AlgoUtil.addVerToGI(g, gi, vIdx);
					}

					// add vIdx to step[p]
					if (p == 0) {
						p = k;
					}
					Set<Integer> gviStepVSet = giStepV[p - 1];
					gviStepVSet.add(AlgoUtil.getIdxByLab(gi, vIdx));
					Set<Integer> gvStepVSet = gStepV[p - 1];
					gvStepVSet.add(vIdx);

				}

			}

		} while (!AlgoUtil.isAllDominated(g));

		g.setIdxSol(idxSol);
		g.setIdxSolSize(idxSolSize);
	}

	private boolean isMomentOfRegret(int p, Set<Integer>[] step) {
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

		// /*
		// * 1.p<k: p previously pointing a middle element of step;
		// 2.step[p]==null: and
		// * the later element in step being not used; 3.p>1:p pointing at least
		// at 1
		// * means there are at least 2 dominating vertices between current
		// graph and
		// * graph we will go back;
		// */
		// // we don't allow this to be true, otherwise, it always get a blank
		// d1 at the
		// // first k.
		// if ((p < k) && (step[p] == null) && (p > 1)) {
		// return false;
		// }

		/*
		 * 1.p<k: p previously pointing a middle element of step;
		 * 2.step[p]!=null: and
		 * the later element in step having been used; 3.p>1:p pointing at least
		 * at 1
		 * means there are at least 2 dominating vertices between current graph
		 * and
		 * graph we will go back;
		 */
		if ((p < k) && (step[p] != null) && (p > 1)) {
			return true;
		}

		return false;

	}
}
