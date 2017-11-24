package au.edu.cdu.dds.io;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import au.edu.cdu.dds.util.AlgoUtil;
import au.edu.cdu.dds.util.ConstantValue;
import au.edu.cdu.dds.util.ISGlobalVariable;

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
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public ISGlobalVariable readGraphByEdgePair(String filePath) throws IOException {
		// access the input file
		Path path = Paths.get(filePath);
		List<String> lines = Files.readAllLines(path, Charset.defaultCharset());
		String line0 = lines.get(0);
		String[] line0Array = line0.split(BLANK);
		/*
		 * the first line shows vertex count and edge count, which are separated
		 * by a
		 * blank
		 */
		String vCountStr = line0Array[0];
		String eCountStr = line0Array[1];

		int vCount = Integer.parseInt(vCountStr);
		int eCount = Integer.parseInt(eCountStr);

		// initialize the global variables
		ISGlobalVariable gv = new ISGlobalVariable();

		AlgoUtil.initGlobalVariable(gv, vCount);

		int[] verLst = gv.getLabLst();
		int[][] idxIM = gv.getIdxIM();
		int[] idxDegree = gv.getIdxDegree();
		int[][] idxAL = gv.getIdxAL();

		// read each line of the input file
		String tmpLine = null;
		int currentVCount = 0;
		for (int i = 1; i <= eCount; i++) {
			tmpLine = lines.get(i);
			// the edge pair is presented by two vertex labels separated by a
			// blank
			String[] tmpLineArray = tmpLine.split(BLANK);
			String uStr = tmpLineArray[0];
			String vStr = tmpLineArray[1];

			if (!uStr.equals(vStr)) {
				// we don't allow self circle of each vertex

				// we get the index of the vertices
				int uIdx = AlgoUtil.getIdxByLab(gv, Integer.parseInt(uStr));
				int vIdx = AlgoUtil.getIdxByLab(gv, Integer.parseInt(vStr));

				// if this vertex is not in the list, add it to vertex list
				if (uIdx == ConstantValue.IMPOSSIBLE_VALUE) {
					verLst[currentVCount] = Integer.parseInt(uStr);
					uIdx = currentVCount;
					currentVCount++;
				}
				if (vIdx == ConstantValue.IMPOSSIBLE_VALUE) {
					verLst[currentVCount] = Integer.parseInt(vStr);
					vIdx = currentVCount;
					currentVCount++;
				}

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

		return gv;
	}

}