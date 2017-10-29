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

		String[] verLst = new String[vCount];
		Arrays.fill(verLst, null);
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
		int undomCnt = vCount;

		// the incident matrix of each vertex is set to be impossible value initially
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

		gv.setIdxSol(idxSol);
		gv.setIdxSolSize(idxSolSize);
		gv.setVerCnt(vCount);
		gv.setActVerCnt(vCount);
		gv.setIdxAL(idxAL);
		gv.setIdxDomed(idxDomed);
		gv.setIdxIM(idxIM);
		gv.setIdxDegree(idxDegree);
		gv.setIdxLst(idxLst);
		gv.setVerLst(verLst);
		gv.setUndomCnt(undomCnt);
		gv.setIdxVote(idxVote);
		gv.setIdxWeight(idxWeight);

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

		//calculate im and al
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

		//init weight
		Util.initWeight(gv);

		return gv;
	}

	

	// /**
	// * read edge pair information from a file to generate graph representations
	// *
	// * @param filePath,
	// * file path and name
	// * @return graph representation
	// * @throws IOException
	// */
	// public GlobalVariable<String, String> readGraphByEdgePair(String filePath)
	// throws IOException {
	//

	// gv.setSol(sol);
	// gv.setBestSol(bestSol);
	// gv.setSolPtr(1); // valid index starts from 1;
	//
	// gv.setsCount(numOfVer);
	// gv.setsAL(sAL);
	// gv.setsIL(sIL);
	// gv.setsIM(sIM);
	// gv.setsL(sL);
	// gv.setsIL(sIL);
	// gv.setCard(card);
	//
	// gv.seteCount(numOfVer);
	// gv.seteAL(eAL);
	// gv.seteIL(eIL);
	// gv.seteIM(eIM);
	// gv.seteL(eL);
	// gv.seteIL(eIL);
	// gv.setFreq(freq);
	//
	// gv.setBestSolCount(numOfVer);
	// gv.setSolCount(0);
	// gv.setMate(mate);
	//
	// return gv;
	//
	// }
}