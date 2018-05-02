package au.edu.cdu.common.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        if(idxLst==null){
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
}