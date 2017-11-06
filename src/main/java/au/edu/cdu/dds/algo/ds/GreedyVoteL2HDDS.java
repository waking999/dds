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
	GlobalVariable gv;
	GlobalVariable gvi;
	int k;
	int r;

	@Override
	public void setKR(int k, int r) {
		this.k = k;
		this.r = r;
	}

	@Override
	public void setGV(GlobalVariable gv) {
		this.gv = gv;
		this.gvi = new GlobalVariable();
		int verCnt = gv.getVerCnt();

		Util.initGlobalVariable(gvi, verCnt);
		adjustGVIInitStatus();
	}

	private void adjustGVIInitStatus() {
		gvi.setActVerCnt(0);
		gvi.setVerCnt(0);
		int[] gviIdxLst = gvi.getIdxLst();
		Arrays.fill(gviIdxLst, ConstantValue.IMPOSSIBLE_VALUE);

		int[] gviIdxDegree = gvi.getIdxDegree();
		int[] gviIdxUtil = Arrays.copyOf(gviIdxDegree, gv.getVerCnt());
		gvi.setIdxUtil(gviIdxUtil);

	}

	public GreedyVoteL2HDDS() {

	}


	@SuppressWarnings("unchecked")
	public void compute() {
		int[] idxSol = gv.getIdxSol();
		int idxSolSize = gv.getIdxSolSize();
		boolean[] idxDomed = gv.getIdxDomed();

		int[] gviStepU = new int[k];
		Arrays.fill(gviStepU, ConstantValue.IMPOSSIBLE_VALUE);
		Set<Integer>[] gviStepV = new HashSet[k];
		int[] gvStepU = new int[k];
		Arrays.fill(gvStepU, ConstantValue.IMPOSSIBLE_VALUE);
		Set<Integer>[] gvStepV = new HashSet[k];

		int currentVCount = 0;
		int[] gviIdxLst = gvi.getIdxLst();

		int p = 0;
		do {

			int vIdx = Util.getUnaddedLowestWeightVertexIdx(gv);

			if (vIdx != ConstantValue.IMPOSSIBLE_VALUE) {
				if (!idxDomed[vIdx]) {

					int uIdx = Util.getHighestWeightNeighIdx(gv, vIdx);
					if (uIdx != ConstantValue.IMPOSSIBLE_VALUE) {
						// if this vertex is not in the list, add it to vertex list
						// add uIdx to gvi;
						if (Util.findPos(gviIdxLst, currentVCount, uIdx) == ConstantValue.IMPOSSIBLE_VALUE) {
							AlgoUtil.addVerToGVI(gv, gvi, uIdx);
						}

						// add vIdx to gvi;
						if (Util.findPos(gviIdxLst, currentVCount, vIdx) == ConstantValue.IMPOSSIBLE_VALUE) {
							AlgoUtil.addVerToGVI(gv, gvi, vIdx);
						}

						// add uIdx into solution
						idxSol[idxSolSize++] = uIdx;
						gv.setIdxSolSize(idxSolSize);

						// adjust weight;
						Util.adjustWeight(gv, uIdx);
						// store uIdx, vIdx to step[p+1];
						// since uIdx, vIdx are stored gvi.verLst, we need get the index of them and put
						// it in the step
						Set<Integer> gviStepVSet = new HashSet<>();
						Set<Integer> gvStepVSet = new HashSet<>();
						// gviStepVList.add(Util.getIndexByVertex(gvi, uIdx));
						gviStepVSet.add(AlgoUtil.getIndexByVertex(gvi, vIdx));
						gvStepVSet.add(vIdx);

						if (p >= k) {
							p = p % k;
						}
						gviStepU[p] = AlgoUtil.getIndexByVertex(gvi, uIdx);
						gviStepV[p] = gviStepVSet;
						gvStepU[p] = uIdx;
						gvStepV[p] = gvStepVSet;
						p++;

						if (isMomentOfRegret(p, gviStepV)) {
							// TestUtil.printGlobalVariableStatus(gvi);
							// 1.copy gvi -> gvi*
							GlobalVariable gviStar = AlgoUtil.copyGloablVariable(gvi);
							// 2. d=gvi*.sol <- gv.sol, idx->getIndexBy(idx)

							int[] gviStarIdxSol = gviStar.getIdxSol();
							Arrays.fill(gviStarIdxSol, ConstantValue.IMPOSSIBLE_VALUE);
							int[] gvIdxSol = gv.getIdxSol();
							int gvIdxSolSize = gv.getIdxSolSize();
							for (int i = 0; i < gvIdxSolSize; i++) {
								gviStarIdxSol[i] = AlgoUtil.getIndexByVertex(gvi, gvIdxSol[i]);
							}
							int gviStarIdxSolSize = gv.getIdxSolSize();
							gviStar.setIdxSolSize(gviStarIdxSolSize);

							// 3.d1 = gvi*Sol(d) \gvi.stepU (d2) in gvi*
							int[] d1 = Util.set1Minus2(gviStarIdxSol, gviStarIdxSolSize, gviStepU, k);
							int d1Len = d1.length;

							if (d1Len == 0) {
								continue;
							}

							// 4. get N[d1] in gvi*
							Set<Integer> gviStarD1Neigs = new HashSet<>();
							int[][] gviStarIdxAL = gviStar.getIdxAL();
							int[] gviStaIdxDegree = gviStar.getIdxDegree();

							for (int d1VIdx : d1) {
								int[] vNeigs = gviStarIdxAL[d1VIdx];
								gviStarD1Neigs.add(d1VIdx);
								for (int j = 0; j < gviStaIdxDegree[d1VIdx]; j++) {
									int d1UIdx = vNeigs[j];
									gviStarD1Neigs.add(d1UIdx);
								}
							}

							// 5. c= v(gvi*)\N[d1]

							int[] c = Util.set1Minus2(gviStar.getIdxLst(), gviStar.getVerCnt(), gviStarD1Neigs);

							// 6. b=N[d1]\d1
							int[] b = Util.set1Minus2(gviStarD1Neigs, d1, d1Len);

							// 7. r1. delete d1 from gvi*
							for (int d1VIdx : d1) {
								AlgoUtil.deleteVertex(gviStar, d1VIdx);
							}
							// 8. r2. delete v in b and N(v) not intersect c
							int bLen = b.length;
							int cLen = c.length;
							int[] bRem = new int[bLen];
							Arrays.fill(bRem, ConstantValue.IMPOSSIBLE_VALUE);
							int bRemLen = 0;
							int[] gviStarIdxDegree = gviStar.getIdxDegree();
							for (int i = 0; i < bLen; i++) {
								int bVIdx = b[i];
								int[] bVNeigs = gviStarIdxAL[bVIdx];
								int bVIdxDegree = gviStarIdxDegree[bVIdx];
								boolean isIntersected = Util.setIntersect(bVNeigs, bVIdxDegree, c, cLen);
								if (!isIntersected) {

									bRem[bRemLen++] = bVIdx;
									AlgoUtil.deleteVertex(gviStar, bVIdx);
								}

							}
							b = Util.set1Minus2(b, bLen, bRem, bRemLen);
							bLen = b.length;

							// 9. r3. delete edge (u,v) in b
							for (int i = 0; i < bLen; i++) {
								int bVIdx = b[i];
								int bVIdxDegree = gviStarIdxDegree[bVIdx];
								int[] bVNeigs = gviStarIdxAL[bVIdx];
								for (int j = bVIdxDegree - 1; j >= 0; j--) {
									int bVNeigIdx = bVNeigs[j];
									AlgoUtil.deleteEdge(gviStar, bVIdx, bVNeigIdx);
								}

							}

							// c is a vertex cover and b is independent set after applying r1,r2,r3
							// 10. get neighbor type: typeDomedMap, typeDomingMap
							boolean[] comparedRuler = new boolean[cLen];
							Arrays.fill(comparedRuler, ConstantValue.MARKED);

							Map<String, boolean[]> neigTypeDomedMap = new HashMap<>();
							Map<String, Set<Integer>> neigTypeDomingMap = new HashMap<>();

							/*
							 * because the vertices in the vertex cover can dominate other vertices in the
							 * vertex cover,they are considered
							 */
							for (int cVIdx : c) {
								// Set<Integer> domedVerSet = new HashSet<>();

								int[] cVNeigs = gviStarIdxAL[cVIdx];

								boolean ruler[] = new boolean[cLen];
								Arrays.fill(ruler, ConstantValue.UNMARKED);

								// the vertex can dominated by itself
								int pos = Util.findPos(c, cLen, cVIdx);
								ruler[pos] = ConstantValue.MARKED;
								// domedVerSet.add(cVIdx);
								for (int cVNeigIdx : cVNeigs) {
									/*
									 * the position of the dominated vertex in the vertex cover will set 1
									 * 
									 * a neighbour of the vertex is likely not to be in the vertex cover
									 */
									pos = Util.findPos(c, cLen, cVNeigIdx);
									if (pos != ConstantValue.IMPOSSIBLE_VALUE) {
										ruler[pos] = ConstantValue.MARKED;
										// domedVerSet.add(cVNeigIdx);
									}
								}

								String rulerStr = Util.arrayToString(ruler);
								if (!neigTypeDomedMap.containsKey(rulerStr)) {
									// neigTypeDomedMap.put(rulerStr, domedVerSet);
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
							 * because the vertices in the independent set can dominate vertices in the
							 * vertex cover,they are considered
							 */
							for (int bVIdx : b) {

								// Set<Integer> domedVerSet = new HashSet<>();

								int[] bVNeigs = gviStarIdxAL[bVIdx];

								boolean ruler[] = new boolean[cLen];
								Arrays.fill(ruler, ConstantValue.UNMARKED);

								for (int bVNeigIdx : bVNeigs) {
									/*
									 * the position of the dominate vertex in the vertex cover will set 1
									 */
									int pos = Util.findPos(c, cLen, bVNeigIdx);
									if (pos != ConstantValue.IMPOSSIBLE_VALUE) {
										ruler[pos] = ConstantValue.MARKED;
										// domedVerSet.add(bVNeigIdx);
									}
								}

								String rulerStr = Util.arrayToString(ruler);
								if (!neigTypeDomedMap.containsKey(rulerStr)) {
									// neigTypeDomedMap.put(rulerStr, domedVerSet);
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

							// 11. choose all possible r out of the neighTypeDomedMap to check if there is a
							// solution
							Ruler[] rulers = Util.converRulerMapToArray(neigTypeDomedMap);
							List<List<Ruler>> allRCombins = Util.combineRuler(rulers, r);
							boolean getRResult=false;
							List<Ruler> validResult=null;
							for(List<Ruler> rulerCom:allRCombins) {
								getRResult= Util.validCombin(rulerCom);
								if(getRResult) {
									validResult=rulerCom;
									break;
								}
							}
							
							
							System.out.println(b);
							if(!getRResult) {
								// 12. if d3==null || |d3|>=|d2| continue;
								continue;
							}else {
								int[] d3=Util.convertRResultToSet(validResult, r, neigTypeDomingMap);
								System.out.println(d3);
								
								
								// 13.a gv.sol \ gv.stepU
								// 13.b gv.domed = false for N[gv.stepU] in gv
								// 13.c gv.added= false for N[gv.stepU] in gv
								// 13.c.1 resetweight(gv)
								// 13.d delete gvi.stepU,gvi.stepV from gvi
								// 13.e gvi.add N[d3]
								// 13.f gvi.stepU=d3, gvi.StepV=N[d3] staring from 0
								// 13.d gv.sol U N[d3]
								// 13.e gv.domed=true for N[d3->getverbyidx] in gv
								// 13.f gv.added=true for N[d3->getverbyidx] in gv
							}

							
							
			
							b = null;
							c = null;
							gviStar = null;

						}

					}

				} else {
					// add vIdx to gvi;
					if (Util.findPos(gviIdxLst, currentVCount, vIdx) == ConstantValue.IMPOSSIBLE_VALUE) {

						AlgoUtil.addVerToGVI(gv, gvi, vIdx);
					}

					// add vIdx to step[p]
					if (p == 0) {
						p = k;
					}
					Set<Integer> gviStepVSet = gviStepV[p - 1];
					gviStepVSet.add(AlgoUtil.getIndexByVertex(gvi, vIdx));
					Set<Integer> gvStepVSet = gvStepV[p - 1];
					gvStepVSet.add(vIdx);

				}

			}

		} while (!Util.isAllDominated(gv));

		gv.setIdxSol(idxSol);
		gv.setIdxSolSize(idxSolSize);
	}

	private boolean isMomentOfRegret(int p, Set<Integer>[] step) {
		/*
		 * 1.p==k:p previously points to the last element of step; 2.p>1:p pointing at
		 * least at 1 means there are at least 2 dominating vertices between current
		 * graph and graph we will go back;
		 */
		if ((p == k) && (p > 1)) {
			return true;
		}

		/*
		 * 1.p<k: p previously pointing a middle element of step; 2.step[p]==null: and
		 * the later element in step being not used; 3.p>1:p pointing at least at 1
		 * means there are at least 2 dominating vertices between current graph and
		 * graph we will go back;
		 */
		// we don't allow this to be true, otherwise, it always get a blank d1 at the
		// first k.
		if ((p < k) && (step[p] == null) && (p > 1)) {
			return false;
		}

		/*
		 * 1.p<k: p previously pointing a middle element of step; 2.step[p]!=null: and
		 * the later element in step having been used; 3.p>1:p pointing at least at 1
		 * means there are at least 2 dominating vertices between current graph and
		 * graph we will go back;
		 */
		if ((p < k) && (step[p] != null) && (p > 1)) {
			return true;
		}

		return false;

	}
}
