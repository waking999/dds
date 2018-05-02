package au.edu.cdu.common.io;

import au.edu.cdu.common.util.AlgoUtil;
import au.edu.cdu.common.util.ConstantValue;
import au.edu.cdu.common.util.GlobalVariable;

import java.io.*;
import java.util.Arrays;

/**
 * implement operation system file operations
 */
public class FileOperation {
    private static final String BLANK = " ";

    /**
     * read data file to initialize the global variables for the data structure
     * and
     * algorithms
     *
     * @param filePath, file path and name
     * @return a series of variables  wrapped global variable to present a graph
     * @throws IOException, ioexception if io error happens
     */
    public static GlobalVariable readGraphByEdgePair(String filePath) throws IOException {
        // access the input file
        // Path path = Paths.get(filePath);

        BufferedInputStream fis;
        GlobalVariable gv = null;

        fis = new BufferedInputStream(new FileInputStream(filePath));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {


            String line;
            line = reader.readLine();

            String[] line0Array = line.split(BLANK);

            /*
             * the first line shows vertex count and edge count, which are separated
             * by a blank
             */
            String vCountStr = line0Array[0].trim();
            int vCount = Integer.parseInt(vCountStr);

            // initialize the global variables
            gv = new GlobalVariable();

            AlgoUtil.initGlobalVariable(gv, vCount);

            int[] verLst = gv.getLabLst();
            int[][] idxIM = gv.getIdxIM();
            int[] idxDegree = gv.getIdxDegree();
            int[][] idxAL = gv.getIdxAL();

            // read each line of the input file

            int currentVCount = 0;
            // int min = Integer.MAX_VALUE;
            // int max = Integer.MIN_VALUE;

            while ((line = reader.readLine()) != null) {

                // the edge pair is presented by two vertex labels separated by a
                // blank
                String[] tmpLineArray = line.split(BLANK);
                String uStr = tmpLineArray[0];
                String vStr = tmpLineArray[1];

                int uLab = Integer.parseInt(uStr);
                // if (uLab > max) {
                // max = uLab;
                // }
                // if (uLab < min) {
                // min = uLab;
                // }
                int vLab = Integer.parseInt(vStr);
                // if (vLab > max) {
                // max = vLab;
                // }
                // if (vLab < min) {
                // min = vLab;
                // }
                // we get the index of the vertices
                int uIdx = AlgoUtil.getIdxByLab(gv, uLab);
                // if this vertex is not in the list, add it to vertex list
                if (uIdx == ConstantValue.IMPOSSIBLE_VALUE) {
                    verLst[currentVCount] = Integer.parseInt(uStr);
                    uIdx = currentVCount;

                    currentVCount++;
                }

                int vIdx = AlgoUtil.getIdxByLab(gv, vLab);
                if (vIdx == ConstantValue.IMPOSSIBLE_VALUE) {
                    verLst[currentVCount] = Integer.parseInt(vStr);
                    vIdx = currentVCount;
                    currentVCount++;
                }

                if (uIdx != vIdx) {
                    // we don't allow self circle of each vertex

                    // we set the incident matrix cells of the two vertices are set
                    // to not null
                    // first
                    idxIM[uIdx][vIdx] = ConstantValue.NOT_NULL;
                    idxIM[vIdx][uIdx] = ConstantValue.NOT_NULL;

                    // the degree of the two vertices will increase
                    idxDegree[uIdx]++;
                    idxDegree[vIdx]++;

                }

            }

            // calculate im and al
            for (int i = 0; i < vCount; i++) {

                idxAL[i] = new int[idxDegree[i]];
                Arrays.fill(idxAL[i], ConstantValue.IMPOSSIBLE_VALUE);

                int currentPos = 0;
                for (int j = 0; j < vCount; j++) {
                    if (idxIM[j][i] == ConstantValue.NOT_NULL) {
                        idxIM[j][i] = currentPos;
                        idxAL[i][currentPos] = j;
                        currentPos++;
                    }

                }
            }

            // initialize weight
            AlgoUtil.initWeight(gv);
        } catch (Exception e) {

            e.printStackTrace();
            throw e;
        } finally {
            return gv;
        }
    }

    public static void saveFile(String destFile, String outputStr) {
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(destFile, false));
            bw.write(outputStr);
            bw.newLine();
            bw.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally { // always close the file
            if (bw != null)
                try {
                    bw.close();
                } catch (IOException ioe2) {
                    // just ignore it
                }
        }
    }

}