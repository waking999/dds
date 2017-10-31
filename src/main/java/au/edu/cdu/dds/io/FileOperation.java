package au.edu.cdu.dds.io;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import au.edu.cdu.dds.util.ConstantValue;
import au.edu.cdu.dds.util.GlobalVariable;
import au.edu.cdu.dds.util.Util;

/**
 * implement operation system file operations
 */
public class FileOperation {
	private static final String BLANK = " ";

	/**
	 * read data file to initalize the global variables for the data structure and
	 * algorithms
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public GlobalVariable<String> readGraphByEdgePair(String filePath) throws IOException {
		// access the input file
		Path path = Paths.get(filePath);
		List<String> lines = Files.readAllLines(path, Charset.defaultCharset());
		String line0 = lines.get(0);
		String[] line0Array = line0.split(BLANK);
		// the first line shows vertex count and edge count, which are separated by a
		// blank
		String vCountStr = line0Array[0];
		String eCountStr = line0Array[1];

		int vCount = Integer.parseInt(vCountStr);
		int eCount = Integer.parseInt(eCountStr);

		// initialize the global variables
		GlobalVariable<String> gv = new GlobalVariable<String>();

		Util.initGlobalVariable(gv, vCount);

		String[] verLst = gv.getVerLst();
		int[][] idxIM = gv.getIdxIM();
		int[] idxDegree = gv.getIdxDegree();
		int[][] idxAL = gv.getIdxAL();

		// read each line of the input file
		String tmpLine = null;
		int currentVCount = 0;
		for (int i = 1; i <= eCount; i++) {
			tmpLine = lines.get(i);
			// the edge pair is presented by two vertex labels separated by a blank
			String[] tmpLineArray = tmpLine.split(BLANK);
			String uStr = tmpLineArray[0];
			String vStr = tmpLineArray[1];

			if (!uStr.equals(vStr)) {
				// we don't allow self circle of each vertex

				// we get the index of the vertices
				int uIdx = Util.getIndexByVertex(gv, uStr);
				int vIdx = Util.getIndexByVertex(gv, vStr);

				// if this vertex is not in the list, add it to vertex list
				if (uIdx == ConstantValue.IMPOSSIBLE_VALUE) {
					verLst[currentVCount] = uStr;
					uIdx = currentVCount;
					currentVCount++;
				}
				if (vIdx == ConstantValue.IMPOSSIBLE_VALUE) {
					verLst[currentVCount] = vStr;
					vIdx = currentVCount;
					currentVCount++;
				}

				// we set the incident matrix cells of the two vertices are set to not null
				// first
				idxIM[uIdx][vIdx] = ConstantValue.NOT_NULL;
				idxIM[vIdx][uIdx] = ConstantValue.NOT_NULL;

				// the degree of the two vertices will increase
				idxDegree[uIdx]++;
				idxDegree[vIdx]++;

			}

		}
		// the utility of each vertex is the same as degree at beginning
		int[] idxUtil = Arrays.copyOf(idxDegree, vCount);
		gv.setIdxUtil(idxUtil);

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
		Util.initWeight(gv);

		return gv;
	}

}