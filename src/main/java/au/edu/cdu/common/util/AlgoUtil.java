package au.edu.cdu.common.util;

import au.edu.cdu.common.exception.ArraysNotSameLengthException;
import au.edu.cdu.common.order.OrderPackageUtil;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import org.apache.commons.collections15.CollectionUtils;

import java.util.*;

/**
 * this is a util class for algorithm relevant methods
 *
 * @author kwang1
 */
public class AlgoUtil {
    /**
     * get the closed neighborhood of a set of vIdx
     *
     * @param g,      variables representing a graph
     * @param idxSet, a set of vertex indices
     * @return the closed neighborhood
     */
    public static Set<Integer> getCloseNeigs(GlobalVariable g, int[] idxSet) {
        Set<Integer> neigs = new HashSet<>();
        int[][] idxAL = g.getIdxAL();
        int[] idxDegree = g.getIdxDegree();

        for (int vIdx : idxSet) {
            neigs.add(vIdx);
            int degree = idxDegree[vIdx];
            int[] vNeigs = idxAL[vIdx];
            for (int j = 0; j < degree; j++) {
                neigs.add(vNeigs[j]);
            }

        }

        return neigs;
    }

    /**
     * convert a giIdx array to gIdx array. It implies that the label of vertices in graph gi is the indices of vertices in graph g.
     *
     * @param giIdxSet, the indices of vertices in graph gi
     * @param giLabLst, the labels of vertices in graph gi
     * @return the indices of vertices in g
     */
    public static int[] convertGIIdxToGIdx(int[] giIdxSet, int[] giLabLst) {
        int[] gIdxSet = new int[giIdxSet.length];
        for (int i = 0; i < giIdxSet.length; i++) {
            gIdxSet[i] = giLabLst[giIdxSet[i]];
        }
        return gIdxSet;
    }

    private static int getFirstUnusedIdx(GlobalVariable g) {
        int[] idxLst = g.getIdxLst();
        int actVerCnt = g.getActVerCnt();
        int verCnt = g.getVerCnt();
        if (actVerCnt == verCnt) {
            return verCnt;
        }
        return idxLst[actVerCnt];

    }

    /**
     * add a vertex v to a new graph
     *
     * @param g,    the global variable of a source graph
     * @param gi,   the global variable of a graph to add vertex
     * @param vIdx, the index of v in gv
     */
    public static void addVerToGI(GlobalVariable g, GlobalVariable gi, int vIdx) {
        boolean[] idxAdded = g.getIdxAdded();
        if (idxAdded[vIdx]) {
            return;
        }
        int[][] idxAL = g.getIdxAL();
        int[] idxDegree = g.getIdxDegree();

        int[][] giIdxAL = gi.getIdxAL();
        int[][] giIdxIM = gi.getIdxIM();
        int[] giIdxDegree = gi.getIdxDegree();
        int[] giLabLst = gi.getLabLst();
        int[] giIdxLst = gi.getIdxLst();

        // mark v is added to gvi
        idxAdded[vIdx] = true;
        /*
         * since we start index from 0, the current vertex count is also the
         * position
         * index for the new vertex;
         */
        int giCurrVerCnt = gi.getActVerCnt();

        /*
         * since gi is a new graph, we use the index of g as the label of gi
         * such that
         * we can keep the connection between gi and g ( gi.lab pointing to
         * g.idx)
         */
        giLabLst[giCurrVerCnt] = vIdx;
        giIdxLst[giCurrVerCnt] = getFirstUnusedIdx(gi);

        // increase the the number of current vertex;
        giCurrVerCnt++;
        gi.setActVerCnt(giCurrVerCnt);
        if (gi.getVerCnt() < gi.getActVerCnt()) {
            gi.setVerCnt(giCurrVerCnt);
        }

        // get the new index of v in gi
        int giVIdx = AlgoUtil.getIdxByLab(gi, vIdx);

        // the neighbor count of v in gv
        int vGVNeigCnt = idxDegree[vIdx];
        // prepare the same-size neighbor array of v in gi;
        giIdxAL[giVIdx] = new int[vGVNeigCnt];
        Arrays.fill(giIdxAL[giVIdx], ConstantValue.IMPOSSIBLE_VALUE);

        // get the neighbours of v in g
        int[] vGNeighs = idxAL[vIdx];

        for (int uIdx : vGNeighs) {
            // for any neighbour u of v in g
            int uIdxPos = Util.findPos(giLabLst, gi.getActVerCnt(), uIdx);
            if (uIdxPos != ConstantValue.IMPOSSIBLE_VALUE) {
                // if u in gi, get the index of u in gi
                int giUIdx = giIdxLst[uIdxPos];

                // set the AL of giUIdx, giVIdx
                int giUIdxDegree = giIdxDegree[giUIdx];
                giIdxAL[giUIdx][giUIdxDegree] = giVIdx;
                int giVIdxDegree = giIdxDegree[giVIdx];
                giIdxAL[giVIdx][giVIdxDegree] = giUIdx;

                // set the IM of giUIdx, giVIdx
                giIdxIM[giVIdx][giUIdx] = giUIdxDegree;
                giIdxIM[giUIdx][giVIdx] = giVIdxDegree;

                // set the degree of giUIdx, giVIdx;
                giIdxDegree[giUIdx]++;
                giIdxDegree[giVIdx]++;

            }
        }

    }

    /**
     * delete a vertex from a graph by the index of the vertex
     *
     * @param g,    variables representing a graph
     * @param vIdx, index of a vertex
     */
    public static void deleteVertex(GlobalVariable g, int vIdx) {
        int actVerCnt = g.getActVerCnt();
        int[] labLst = g.getLabLst();
        int[] idxLst = g.getIdxLst();
        int[][] idxAL = g.getIdxAL();

        int[] idxDegre = g.getIdxDegree();
        // get the pos of vIdx in idxLst
        int vIdxPos = Util.findPos(idxLst, actVerCnt, vIdx);
        if (vIdxPos == ConstantValue.IMPOSSIBLE_VALUE) {
            return;
        }

        int d = idxDegre[vIdx];

        // swap v with the bottom of verLst
        int lastVer = labLst[actVerCnt - 1];
        labLst[actVerCnt - 1] = labLst[vIdxPos];
        labLst[vIdxPos] = lastVer;

        // swap vIdx with the bottom of idxLst
        int lastIdx = idxLst[actVerCnt - 1];
        idxLst[actVerCnt - 1] = idxLst[vIdxPos];
        idxLst[vIdxPos] = lastIdx;

        // delete edges of this vertex and its neighbors
        for (int j = d - 1; j >= 0; j--) {
            int uIdx = idxAL[vIdx][j];
            deleteEdge(g, uIdx, vIdx);
        }

        g.setActVerCnt(actVerCnt - 1);

    }

    /**
     * delete the edge between u and v in a graph
     *
     * @param g     , a graph
     * @param uIdx, index of one end vertex
     * @param vIdx, index of another end vertex
     */
    public static void deleteEdge(GlobalVariable g, int uIdx, int vIdx) {
        deleteVFromU(g, uIdx, vIdx);
        deleteVFromU(g, vIdx, uIdx);
    }

    /**
     * delete the edge between u and v in a graph, where is from the u side
     *
     * @param g,    the global variable of the graph
     * @param uIdx, index of one end vertex
     * @param vIdx, index of another end vertex
     */
    private static void deleteVFromU(GlobalVariable g, int uIdx, int vIdx) {

        int[] gvIdxDegree = g.getIdxDegree();
        int[][] gvIdxIM = g.getIdxIM();
        int[][] gvIdxAL = g.getIdxAL();

        // delete from u side
        int uIdxDegree = gvIdxDegree[uIdx];
        int vIdxIMPosOfUIdx = gvIdxIM[vIdx][uIdx];
        int uLastNeigIdx = gvIdxAL[uIdx][uIdxDegree - 1];
        // swap vIdx to the bottom of the neigh in idxAL
        gvIdxAL[uIdx][uIdxDegree - 1] = vIdx;
        gvIdxAL[uIdx][vIdxIMPosOfUIdx] = uLastNeigIdx;
        // swap vIdx's position for neigh in IdxIM
        gvIdxIM[vIdx][uIdx] = uIdxDegree - 1;
        gvIdxIM[uLastNeigIdx][uIdx] = vIdxIMPosOfUIdx;
        // decrement the degree of u
        gvIdxDegree[uIdx]--;
    }

    /**
     * need a new copy of a graph so that the changes on the new copy will not
     * affect the original graph
     * It is not necessary to copy all fields in global variable since not all
     * information is needed in the new copy. just the part relevant to graph.
     *
     * @param g, variables representing a graph
     * @return the copy of variables representing the graph
     */
    public static GlobalVariable copyGraphInGloablVariable(GlobalVariable g) {
        GlobalVariable gNew = new GlobalVariable();

        gNew.setVerCnt(g.getVerCnt());
        // labLst
        int[] labLst = g.getLabLst();
        gNew.setLabLst(Arrays.copyOf(labLst, labLst.length));
        // idxLst
        int[] idxLst = g.getIdxLst();
        gNew.setIdxLst(Arrays.copyOf(idxLst, idxLst.length));
        // degree
        int[] idxDegree = g.getIdxDegree();
        gNew.setIdxDegree(Arrays.copyOf(idxDegree, idxDegree.length));
        // IM
        int[][] idxIM = g.getIdxIM();
        int idxIMLen = idxIM.length;
        int[][] gvStarIdxIM = new int[idxIMLen][];
        for (int i = 0; i < idxIMLen; i++) {
            int[] idxIMRow = idxIM[i];
            gvStarIdxIM[i] = Arrays.copyOf(idxIMRow, idxIMRow.length);
        }
        gNew.setIdxIM(gvStarIdxIM);
        // AL
        int[][] idxAL = g.getIdxAL();
        int idxALLen = idxAL.length;
        int[][] gvStarIdxAL = new int[idxALLen][];
        for (int i = 0; i < idxALLen; i++) {
            int[] idxALRow = idxAL[i];
            if (idxALRow != null) {
                gvStarIdxAL[i] = Arrays.copyOf(idxALRow, idxALRow.length);
            } else {
                break;
            }
        }
        gNew.setIdxAL(gvStarIdxAL);

        return gNew;
    }

    /**
     * get the index of a vertex by its label
     *
     * @param g,   variables representing a graph
     * @param lab, label of a vertex
     * @return index of the vertex
     */
    public static int getIdxByLab(GlobalVariable g, int lab) {
        int actVerCnt = g.getActVerCnt();
        int[] labLst = g.getLabLst();
        int[] idxLst = g.getIdxLst();

        for (int i = 0; i < actVerCnt; i++) {
            if (lab == labLst[i]) {
                return idxLst[i];
            }
        }
        return ConstantValue.IMPOSSIBLE_VALUE;
    }

    /**
     * initialize the global variables with the number of vertex
     *
     * @param g,      variables representing a graph
     * @param vCount, number of vertices in the graph
     */
    public static void initGlobalVariable(GlobalVariable g, int vCount) {
        // labLst does not have valid value
        int[] labLst = new int[vCount];
        Arrays.fill(labLst, ConstantValue.IMPOSSIBLE_VALUE);
        // the index of each vertex is the sequence no. initially
        int[] idxLst = new int[vCount];
        for (int i = 0; i < vCount; i++) {
            idxLst[i] = i;
        }

        // the degree of each vertex is 0 initially
        int[] idxDegree = new int[vCount];
        Arrays.fill(idxDegree, 0);

        // the vote of each vertex is 0 initially
        float[] idxVote = new float[vCount];
        Arrays.fill(idxVote, 0);

        // the weight of each vertex is 0 initially
        float[] idxWeight = new float[vCount];
        Arrays.fill(idxWeight, 0);

        // the dominated status of each vertex is false initially
        boolean[] idxDomed = new boolean[vCount];
        Arrays.fill(idxDomed, false);


        // the added status of each vertex is false initially
        boolean[] idxAdded = new boolean[vCount];
        Arrays.fill(idxAdded, false);

        /*
         * the incident matrix of each vertex is set to be impossible value
         * initially
         */
        int[][] idxIM = new int[vCount][vCount];
        for (int i = 0; i < vCount; i++) {
            Arrays.fill(idxIM[i], ConstantValue.IMPOSSIBLE_VALUE);
        }

        // the adjacent list
        int[][] idxAL = new int[vCount][];

        // the solution
        int[] idxSol = new int[vCount];
        Arrays.fill(idxSol, ConstantValue.IMPOSSIBLE_VALUE);
        int idxSolSize = 0;

        g.setIdxSol(idxSol);
        g.setIdxSolSize(idxSolSize);
        g.setVerCnt(vCount);
        g.setActVerCnt(vCount);
        g.setIdxAL(idxAL);
        g.setIdxDomed(idxDomed);
        g.setIdxAdded(idxAdded);
        g.setIdxIM(idxIM);
        g.setIdxDegree(idxDegree);
        g.setIdxLst(idxLst);
        g.setLabLst(labLst);
        g.setIdxVote(idxVote);
        g.setIdxWeight(idxWeight);
    }

    /**
     * adjust values of gi's own variables at initialization
     */
    public static void adjustGIInitStatus(GlobalVariable gi) {
        // active vertex count and vertex count should be 0
        gi.setActVerCnt(0);
        gi.setVerCnt(0);

        // idxLst does not have valid values
        int[] giIdxLst = gi.getIdxLst();
        Arrays.fill(giIdxLst, ConstantValue.IMPOSSIBLE_VALUE);

    }

    /**
     * convert the idx solution into label solution
     *
     * @param g, the variables representing a graph
     * @return vertex solution in label format
     */
    public static int[] getLabSolution(GlobalVariable g) {
        int idxSolSize = g.getIdxSolSize();
        int[] idxSol = g.getIdxSol();
        int[] labLst = g.getLabLst();
        int[] sol = new int[idxSolSize];
        for (int i = 0; i < idxSolSize; i++) {
            sol[i] = labLst[idxSol[i]];
        }
        return sol;
    }

    public static String getLabSolutionStr(GlobalVariable g) {
        StringBuilder sb = new StringBuilder();
        int[] sol = AlgoUtil.getLabSolution(g);

        for (int aSol : sol) {
            sb.append(aSol).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * initialize the vote and weight after a graph loaded
     *
     * @param g, variables representing a graph
     */
    public static void initWeight(GlobalVariable g) {
        int actVerCnt = g.getActVerCnt();
        int[] idxDegree = g.getIdxDegree();
        float[] idxVote = g.getIdxVote();
        float[] idxWeight = g.getIdxWeight();
        int[][] idxAL = g.getIdxAL();

        for (int i = 0; i < actVerCnt; i++) {
            int degree = idxDegree[i];
            float vote = 1.0f / (1 + degree);
            idxVote[i] = vote;
            idxWeight[i] = vote;
        }

        for (int i = 0; i < actVerCnt; i++) {
            int[] iIdxNeigh = idxAL[i];

            float iWeight = idxWeight[i];
            int iNeighCnt = idxDegree[i];

            for (int j = 0; j < iNeighCnt; j++) {
                int jIdx = iIdxNeigh[j];
                float jVote = idxVote[jIdx];
                iWeight += jVote;
            }
            idxWeight[i] = iWeight;
        }
    }

    /**
     * adjust the weight of the graph after the domination status of some
     * vertices change
     *
     * @param g,    variables representing a graph
     * @param uIdx, index of a vertex which will be adjusted
     */
    public static void adjustWeight(GlobalVariable g, int uIdx) {
        int[][] idxAL = g.getIdxAL();
        int[] idxDegree = g.getIdxDegree();
        int uDegree = idxDegree[uIdx];

        int[] uIdxNeigs = idxAL[uIdx];
        boolean[] idxDomed = g.getIdxDomed();

        float[] idxWeight = g.getIdxWeight();
        float[] idxVote = g.getIdxVote();

        idxWeight[uIdx] = 0f;

        for (int i = 0; i < uDegree; i++) {
            int vIdx = uIdxNeigs[i];
            if (idxWeight[vIdx] - 0 > ConstantValue.FLOAT_NO_DIFF) {
                if (!idxDomed[uIdx]) {
                    idxWeight[vIdx] -= idxVote[uIdx];
                }
                if (!idxDomed[vIdx]) {
                    idxDomed[vIdx] = true;
                    idxWeight[vIdx] -= idxVote[vIdx];

                    int[] vIdxNeigs = idxAL[vIdx];

                    int vDegree = idxDegree[vIdx];

                    for (int j = 0; j < vDegree; j++) {
                        int wIdx = vIdxNeigs[j];

                        if (idxWeight[wIdx] - 0 > ConstantValue.FLOAT_NO_DIFF) {
                            idxWeight[wIdx] -= idxVote[vIdx];
                        }
                    }
                }
            }
        }
        idxDomed[uIdx] = true;

    }

    /**
     * to check if all vertices are dominated
     *
     * @param g, variables representing a graph
     * @return true if the graph is dominated, false otherwise
     */
    public static boolean isAllDominated(GlobalVariable g) {
        int verCnt = g.getVerCnt();
        for (int i = 0; i < verCnt; i++) {
            if (g.getIdxDomed()[i]) {
                continue;
            }
            return false;
        }
        return true;
    }

    /**
     * get a vertex with the highest weight among the vertices
     *
     * @param g, variables representing a graph
     * @return index of a vertex with the highest weight
     */
    public static int getHighestWeightVertexIdx(GlobalVariable g) {
        int actVerCount = g.getActVerCnt();
        int[] idxLst = g.getIdxLst();

        float[] idxWeight = g.getIdxWeight();

        float maxWeight = Float.MIN_VALUE;

        int retIdx = 0;

        for (int i = 0; i < actVerCount; i++) {
            int vIdx = idxLst[i];
            if ((idxWeight[vIdx] - 0 > ConstantValue.FLOAT_NO_DIFF)
                    && (idxWeight[vIdx] - maxWeight > ConstantValue.FLOAT_NO_DIFF)) {
                maxWeight = idxWeight[vIdx];
                retIdx = vIdx;
            }
        }

        if (Math.abs(maxWeight - 0f) <= ConstantValue.FLOAT_NO_DIFF) {
            return ConstantValue.IMPOSSIBLE_VALUE;
        }

        return retIdx;
    }

    /**
     * get a vertex with the lowest weight among unadded vertices
     *
     * @param g, variables representing a graph
     * @return a vertex with the lowest weight among unadded vertices
     */
    public static int getUnaddedLowestWeightVertexIdx(GlobalVariable g) {
        int actVerCount = g.getActVerCnt();
        int[] idxLst = g.getIdxLst();
        boolean[] idxAdded = g.getIdxAdded();

        float[] idxWeight = g.getIdxWeight();

        float minWeight = Float.MAX_VALUE;

        int retIdx = ConstantValue.IMPOSSIBLE_VALUE;

        for (int i = 0; i < actVerCount; i++) {
            int vIdx = idxLst[i];

            if ((!idxAdded[vIdx]) && (idxWeight[vIdx] >= 0) && (idxWeight[vIdx] < minWeight)) {
                minWeight = idxWeight[vIdx];
                retIdx = vIdx;
            }
        }

        return retIdx;
    }

    /**
     * get a vertex with the lowest weight among undominated vertices
     *
     * @param g, variables representing a graph
     * @return a vertex with the lowest weight among undominated vertices
     */
    public static int getUndomedLowestWeightVertexIdx(GlobalVariable g) {
        int actVerCount = g.getActVerCnt();
        int[] idxLst = g.getIdxLst();
        boolean[] idxDomed = g.getIdxDomed();

        float[] idxWeight = g.getIdxWeight();

        float minWeight = Float.MAX_VALUE;

        int retIdx = ConstantValue.IMPOSSIBLE_VALUE;

        for (int i = 0; i < actVerCount; i++) {
            int vIdx = idxLst[i];

            if ((!idxDomed[vIdx]) && (idxWeight[vIdx] > 0) && (idxWeight[vIdx] < minWeight)) {
                minWeight = idxWeight[vIdx];
                retIdx = vIdx;
            }
        }

        if (Math.abs(minWeight - 0f) <= ConstantValue.FLOAT_NO_DIFF) {
            return ConstantValue.IMPOSSIBLE_VALUE;
        }

        return retIdx;
    }

    /**
     * get an neighbor with the highest weight of a vertex
     *
     * @param g,    variables representing a graph
     * @param vIdx, index of a vertex
     * @return an neighbor with the highest weight of a vertex
     */
    public static int getHighestWeightNeighIdx(GlobalVariable g, int vIdx) {
        int[][] idxAL = g.getIdxAL();

        int[] idxDegree = g.getIdxDegree();

        int vDegree = idxDegree[vIdx];
        int[] vIdxNeigs = idxAL[vIdx];
        float[] idxWeight = g.getIdxWeight();
        float minWeight = idxWeight[vIdx];

        int retIdx = vIdx;

        for (int i = 0; i < vDegree; i++) {
            int uIdx = vIdxNeigs[i];
            if ((idxWeight[uIdx] > minWeight)) {
                minWeight = idxWeight[uIdx];
                retIdx = uIdx;

            }
        }

        return retIdx;
    }

    /**
     * to check if a solution is valid
     *
     * @param g, variables representing a graph
     *           global variables
     * @return true if it is valid, otherwise false
     */
    public static boolean isValidSolution(GlobalVariable g) {

        int[] idxLst = g.getIdxLst();
        if (idxLst == null) {
            return false;
        }
        Set<Integer> idxLstList = new HashSet<>();

        int[] idxSol = g.getIdxSol();
        int idxSolSize = g.getIdxSolSize();
        int[][] idxAL = g.getIdxAL();

        putVerticesIntoASet(idxSol, idxLstList, idxSolSize, idxAL);

        return (idxLstList.size() == idxLst.length);
    }

    /*
    public static boolean isValidSolution(GlobalVariable g, int[][] sols) {

        int[] idxLst = g.getIdxLst();
        Set<Integer> idxLstList = new HashSet<>();

        int[][] idxAL = g.getIdxAL();

        for (int[] sol : sols) {
            int solSize = sol.length;
            putVerticesIntoASet(sol, idxLstList, solSize, idxAL);
        }

        return (idxLstList.size() == idxLst.length);
    }
    */

    /**
     * to check if a solution is valid
     *
     * @param g, variables reprsenting a graph
     * @return true if it is valid, otherwise false
     */
    public static boolean isValidSolution(GlobalVariable g, int[] solTry) {

        int[] idxLst = g.getIdxLst();
        Set<Integer> idxLstList = new HashSet<>();

        int solTrySize = solTry.length;
        int[][] idxAL = g.getIdxAL();

        putVerticesIntoASet(solTry, idxLstList, solTrySize, idxAL);

        return (idxLstList.size() == idxLst.length);
    }

    private static void putVerticesIntoASet(int[] solTry, Set<Integer> idxLstList, int solTrySize, int[][] idxAL) {
        for (int i = 0; i < solTrySize; i++) {
            int vIdx = solTry[i];
            int[] vNeigs = idxAL[vIdx];
            for (int uIdx : vNeigs) {
                idxLstList.add(uIdx);
            }
            idxLstList.add(vIdx);
        }
    }

    /**
     * try to remove distance to 1 vertices from the solution to see if we can get
     * smaller result
     *
     * @param g,        variables representing a graph
     * @param distance, how many vertices to be removed from solution.
     * @return a minimized solution if applicable
     */
    static int[] minimal(GlobalVariable g, int distance) {

        int[] idxSol = g.getIdxSol();
        int idxSolSize = g.getIdxSolSize();
        int[] ds = Arrays.copyOf(idxSol, idxSolSize);

        for (int d = distance; d >= 1; d--) {
            List<int[]> combins = Util.getAllRoutOfNCombines(ds, idxSolSize - d);

            for (int[] com : combins) {
                boolean flag = AlgoUtil.isValidSolution(g, com);
                if (flag) {
                    return com;
                }
            }
        }

        return ds;
    }

    /**
     * get the edge label by the two end points
     *
     * @param v1, vertex 1 label
     * @param v2, vertex 2 label
     * @return edge label between the two end points
     */
    private static String getEdgeLabelBy2VerticesLabel(int v1, int v2) {
        int min = Math.min(v1, v2);
        int max = Math.max(v1, v2);
        return String.valueOf(min) + ConstantValue.UNDERLINE + max;
    }

    /**
     * prepare a graph (supporting adding/deleting vertices and edges)
     *
     * @param adjacencyMatrix, the adjacency matrix
     * @return a jung graph
     */
    public static edu.uci.ics.jung.graph.Graph<Integer, String> prepareGenericGraph(List<String[]> adjacencyMatrix) {

        int numOfVertices = adjacencyMatrix.size();
        edu.uci.ics.jung.graph.Graph<Integer, String> g = new SparseMultigraph<>();
        for (int i = 0; i < numOfVertices; i++) {
            g.addVertex(i);
        }

        for (int i = 0; i < numOfVertices; i++) {
            String[] rowArr = adjacencyMatrix.get(i);
            for (int j = i + 1; j < numOfVertices; j++) {
                if (ConstantValue.CONNECTED.equals(rowArr[j].trim())) {
                    /*
                     * the label of edge is decided by the label of the two
                     * endpoints
                     */
                    String edge = getEdgeLabelBy2VerticesLabel(i, j);
                    g.addEdge(edge, i, j);

                }
            }
        }
        return g;
    }

    /**
     * decide if the vertices in dominatedMap are all marked as dominated.
     *
     * @param dominatedMap
     * @return true, all vertices are dominated;false,no
     */
    public static boolean isAllDominated(Map<Integer, Boolean> dominatedMap) {

        Collection<Boolean> values = dominatedMap.values();
        for (Boolean b : values) {
            if (!b) {
                return false;
            }
        }

        return true;
    }

    /**
     * get a copy of source graph
     *
     * @param src , source graph
     * @return a copy of source graph
     */
    public static <V, E> Graph<V, E> copyGraph(Graph<V, E> src) {
        Graph<V, E> dest = new SparseMultigraph<V, E>();
        for (V v : src.getVertices())
            dest.addVertex(v);

        for (E e : src.getEdges())
            dest.addEdge(e, src.getIncidentVertices(e));

        return dest;
    }

    /**
     * decide if it is a dominating set solution of a graph
     *
     * @param g  , graph
     * @param ds , a potential dominating set
     * @return true, is dominating set ; no, not
     */
    public static <V, E> boolean isDS(Graph<V, E> g, List<V> ds) {
        Collection<V> vertices = g.getVertices();
        Collection<V> complementaryDS = CollectionUtils.subtract(vertices, ds);

        for (V v : ds) {
            // get neighbours of the vertices in dominating set
            Collection<V> neighborsOfV = g.getNeighbors(v);
            // remove the neighbours from the complementary set
            if (neighborsOfV != null) {
                complementaryDS = CollectionUtils.subtract(complementaryDS, neighborsOfV);
            }
        }
        /*
         * if the complementary set is not empty, it means there are some
         * vertices are not dominated and in turn the input set is not a
         * dominating set for the graph
         */
        if (!complementaryDS.isEmpty()) {
            return false;
        }
        return true;

    }

    /**
     * @param g
     * @param v
     * @param dominatedMap
     * @return
     */
    public static <V, E> int getVertexUtility(Graph<V, E> g, V v, Map<V, Boolean> dominatedMap) {
        Collection<V> vNeigs = g.getNeighbors(v);
        int unDominatedDegree = 0;
        for (V u : vNeigs) {
            if (!dominatedMap.get(u)) {
                unDominatedDegree++;
            }
        }
        return unDominatedDegree;
    }

    /**
     * minimalization to reduce redundant vertices
     *
     * @param g
     * @param ds
     * @return
     * @throws ArraysNotSameLengthException
     */
    public static <V, E> List<V> minimal(Graph<V, E> g, List<V> ds) throws ArraysNotSameLengthException {

        int distance = 1;
        int dsSize = ds.size();
        boolean[] chosen = verifySubDS(ds, dsSize, dsSize - distance, g);
        if (chosen == null) {
            return ds;
        } else {
            List<V> tempDs = new ArrayList<V>(dsSize - distance);

            for (int i = 0; i < dsSize; i++) {
                if (chosen[i]) {
                    tempDs.add(ds.get(i));
                }
            }
            return tempDs;
        }
    }

    /**
     * @param ds, a potential dominating set
     * @param n,  chose n
     * @param m,  totally m
     * @param g,  graph instance
     * @return a boolean array showing the combination
     * @throws ArraysNotSameLengthException
     */
    public static <V, E> boolean[] verifySubDS(List<V> ds, int n, int m, Graph<V, E> g)
            throws ArraysNotSameLengthException {
        if (m > n) {
            m = n;
        }

        boolean isSolution = false;
        boolean isEnd = false;

        boolean[] chosen = new boolean[n];
        Arrays.fill(chosen, ConstantValue.UNCHOSEN);

        Arrays.fill(chosen, 0, m, ConstantValue.CHOSEN);

        // int count = 0;
        // count++;
        isSolution = verifyChosen(ds, chosen, m, n, g);

        if (isSolution) {
            return chosen;
        }

        do {
            int pose = 0;
            int sum = 0;
            for (int i = 0; i < (n - 1); i++) {
                if (chosen[i] == ConstantValue.CHOSEN && chosen[i + 1] == ConstantValue.UNCHOSEN) {
                    chosen[i] = ConstantValue.UNCHOSEN;
                    chosen[i + 1] = ConstantValue.CHOSEN;
                    pose = i;
                    break;
                }
            }
            // count++;

            isSolution = verifyChosen(ds, chosen, m, n, g);

            if (isSolution) {
                return chosen;
            }

            for (int i = 0; i < pose; i++) {
                if (chosen[i] == ConstantValue.CHOSEN) {
                    sum++;
                }
            }

            boolean[] copyOfChosen = Arrays.copyOf(chosen, chosen.length);

            Arrays.fill(chosen, 0, sum, ConstantValue.CHOSEN);
            Arrays.fill(chosen, sum, pose, ConstantValue.UNCHOSEN);

            if (!Arrays.equals(copyOfChosen, chosen)) {
                // count++;
                isSolution = verifyChosen(ds, chosen, m, n, g);

                if (isSolution) {
                    return chosen;
                }
            }

            isEnd = true;
            for (int i = n - m; i < n; i++) {

                if (chosen[i] == ConstantValue.UNCHOSEN) {
                    isEnd = false;
                    break;
                }

            }

        } while (!isEnd);
        if (!isSolution) {
            return null;
        } else {
            return chosen;
        }

    }


    /**
     * @param ds,     a potential dominating set
     * @param chosen, a combination
     * @param m,      totally m
     * @param n,      chose n
     * @param g,      graph instance
     * @return
     * @throws ArraysNotSameLengthException
     */
    private static <V, E> boolean verifyChosen(List<V> ds, boolean[] chosen, int m, int n, Graph<V, E> g)
            throws ArraysNotSameLengthException {
        List<V> tempDs = new ArrayList<V>(m);

        for (int i = 0; i < n; i++) {
            if (chosen[i]) {
                tempDs.add(ds.get(i));
            }
        }

        return isDS(g, tempDs);

    }

    /**
     * @param g
     * @param v
     * @return
     */
    public static <V, E> int getVertexDegree(Graph<V, E> g, V v) {

        int unDominatedDegree = g.degree(v);
        return unDominatedDegree;
    }

    /**
     * get the highest utility neighbor of a vertex
     *
     * @param v,            the vertex
     * @param g,            the graph instance
     * @param dominatedMap, the dominated map (a map keeping pair of <vertex, dominated>)
     * @return
     */
    public static <V, E> V getHighestUtilityNeighborOfAVertex(V v, Graph<V, E> g, Map<V, Boolean> dominatedMap) {

        List<V> vList = OrderPackageUtil.getVertexListUtilityDesc(g, dominatedMap);

        Collection<V> vNeg = g.getNeighbors(v);
        vNeg.add(v);

        for (V u : vList) {
            if (vNeg.contains(u)) {
                return u;
            }
        }

        return null;

    }


    /**
     * a GRASP local search
     *
     * @param g, the graph
     * @param d, the dominating set
     * @return the dominating set after local search
     */
    public static <V, E> List<V> grasp(Graph<V, E> g, List<V> d) {
        Collection<V> vertices = g.getVertices();
        int numOfVertices = vertices.size();
        Map<V, Integer> coveredbyMap = new HashMap<V, Integer>(numOfVertices);

        for (V v : vertices) {
            coveredbyMap.put(v, 0);
        }

        for (V w : d) {

            coveredbyMap.put(w, coveredbyMap.get(w).intValue() + 1);
            Collection<V> wNeig = g.getNeighbors(w);
            for (V v : wNeig) {
                coveredbyMap.put(v, coveredbyMap.get(v).intValue() + 1);
            }
        }
        int dSize = d.size();
        for (int i = 0; i < dSize - 1; i++) {
            V vi = d.get(i);
            for (int j = i + 1; j < dSize; j++) {
                V vj = d.get(j);
                if (!vi.equals(vj)) {
                    List<V> U = new ArrayList<V>();
                    for (V vk : vertices) {
                        int covby = coveredbyMap.get(vk);
                        if (isAVertexDominateAVertex(vi, vk, g)) {
                            covby--;
                        }
                        if (isAVertexDominateAVertex(vj, vk, g)) {
                            covby--;
                        }
                        if (covby == 0) {
                            Util.addElementToList(U, vk);
                        }
                    }
                    if (U.isEmpty()) {
                        d.remove(vi);
                        d.remove(vj);
                        // log.debug("ds changed here.");
                        return grasp(g, d);
                    } else {
                        for (V vk : vertices) {
                            if (isAVertexDominateASet(vk, U, g)) {
                                d.remove(vi);
                                d.remove(vj);
                                d.add(vk);
                                // log.debug("ds changed here.");
                                return grasp(g, d);
                            }
                        }
                    }
                }
            }
        }

        return d;
    }

    /**
     * if a vertex set(vList) is dominated by another vertex (v)
     *
     * @param v,     the dominating vertex
     * @param vList, the dominated vertex set
     * @param g,     the graph instance
     * @return true: vList is dominated by v;false, no
     */
    public static <V, E> boolean isAVertexDominateASet(V v, Collection<V> vList, Graph<V, E> g) {
        Collection<V> vNeig = g.getNeighbors(v);
        if (CollectionUtils.subtract(vList, vNeig).isEmpty()) {
            return true;
        }
        return false;

    }

    /**
     * if a vertex (u) is dominated by another vertex (v)
     *
     * @param v, the dominating vertex
     * @param u, the dominated vertex
     * @param g, the graph instance
     * @return true: u is dominated by v;false, no
     */
    public static <V, E> boolean isAVertexDominateAVertex(V v, V u, Graph<V, E> g) {
        if (u.equals(v)) {
            // a vertex always dominates itself
            return true;
        }
        Collection<V> vNeig = g.getNeighbors(v);
        if (vNeig.contains(u)) {
            return true;
        }
        return false;

    }
}
