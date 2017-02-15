package au.edu.cdu.dds.io;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import au.edu.cdu.dds.util.ConstantValue;
import au.edu.cdu.dds.util.GlobalVariable;

/**
 * implement operation system file operations
 */
public class FileOperation {
	private static final String BLANK = " ";

	/**
	 * read edge pair information from a file to generate graph representations
	 * 
	 * @param filePath,
	 *            file path and name
	 * @return graph representation
	 * @throws IOException
	 */
	public GlobalVariable<String, String> readGraphByEdgePair(String filePath) throws IOException {

		GlobalVariable<String, String> gv = new GlobalVariable<String, String>();

		Path path = Paths.get(filePath);
		List<String> lines = Files.readAllLines(path, Charset.defaultCharset());
		String line0 = lines.get(0);
		String[] line0Array = line0.split(BLANK);
		String numOfVerStr = line0Array[0];
		String numOfEdgStr = line0Array[1];

		int numOfVer = Integer.parseInt(numOfVerStr);
		int numOfEdg = Integer.parseInt(numOfEdgStr);

		int mallocsize = numOfVer + 1; // valid index starts from 1

		int[] card = new int[mallocsize];
		int[] freq = new int[mallocsize];
		int[] sL = new int[mallocsize];
		int[] sIL = new int[mallocsize];
		int[] eL = new int[mallocsize];
		int[] eIL = new int[mallocsize];
		int[][] sIM = new int[mallocsize][mallocsize];
		int[][] eIM = new int[mallocsize][mallocsize];

		for (int i = 0; i <= numOfVer; i++) {
			if (i > 0) {
				card[i] = 1;
				freq[i] = 1;
			} else {
				card[i] = numOfVer;
				freq[i] = numOfVer;
			}

			if (i > 0) {
				sL[i] = i;
				sIL[i] = i;
				eL[i] = i;
				eIL[i] = i;
			} else {
				sL[i] = ConstantValue.IMPOSSIBLE_VALUE;
				sIL[i] = ConstantValue.IMPOSSIBLE_VALUE;
				eL[i] = ConstantValue.IMPOSSIBLE_VALUE;
				eIL[i] = ConstantValue.IMPOSSIBLE_VALUE;
			}

			for (int j = 0; j <= numOfVer; j++) {
				sIM[i][j] = ConstantValue.IMPOSSIBLE_VALUE;
				eIM[i][j] = ConstantValue.IMPOSSIBLE_VALUE;
			}
		}

		String tmpLine = null;
		for (int i = 1; i <= numOfEdg; i++) {
			tmpLine = lines.get(i);
			String[] tmpLineArray = tmpLine.split(BLANK);
			String u1Str = tmpLineArray[0];
			String v1Str = tmpLineArray[1];
			int u = Integer.parseInt(u1Str);
			int v = Integer.parseInt(v1Str);

			if (u != v) {
				sIM[u][v] = 1;
				sIM[v][u] = 1;
				eIM[u][v] = 1;
				eIM[v][u] = 1;

				card[u]++;
				card[v]++;
				freq[u]++;
				freq[v]++;
			}
		}

		for (int i = 1; i <= numOfVer; i++) {

			sIM[i][i] = 1;
			eIM[i][i] = 1;
		}

		int[][] sAL = new int[mallocsize][];
		int[][] eAL = new int[mallocsize][];

		for (int i = 1; i <= numOfVer; i++) {
			sAL[i] = new int[card[i] + 1];
			eAL[i] = new int[freq[i] + 1];

			sAL[i][0] = ConstantValue.IMPOSSIBLE_VALUE;
			eAL[i][0] = ConstantValue.IMPOSSIBLE_VALUE;

			int sCur = 1;
			int eCur = 1;
			for (int j = 1; j <= numOfVer; j++) {
				if (sIM[j][i] == 1) {
					sIM[j][i] = sCur;
					sAL[i][sCur] = j;
					sCur++;
				}
				if (eIM[j][i] == 1) {
					eIM[j][i] = eCur;
					eAL[i][eCur] = j;
					eCur++;
				}
			}
		}

		int[] mate = new int[mallocsize];
		for (int i = 0; i <= numOfVer; i++) {
			mate[i] = ConstantValue.IMPOSSIBLE_VALUE;
		}

		int[] sol = new int[mallocsize];
		int[] bestSol = new int[mallocsize];
		for (int i = 0; i <= numOfVer; i++) {
			sol[i] = ConstantValue.IMPOSSIBLE_VALUE;
			bestSol[i] = ConstantValue.IMPOSSIBLE_VALUE;
		}
		gv.setSol(sol);
		gv.setBestSol(bestSol);
		gv.setSolPtr(1); // valid index starts from 1;

		gv.setsCount(numOfVer);
		gv.setsAL(sAL);
		gv.setsIL(sIL);
		gv.setsIM(sIM);
		gv.setsL(sL);
		gv.setsIL(sIL);
		gv.setCard(card);

		gv.seteCount(numOfVer);
		gv.seteAL(eAL);
		gv.seteIL(eIL);
		gv.seteIM(eIM);
		gv.seteL(eL);
		gv.seteIL(eIL);
		gv.setFreq(freq);

		gv.setBestSolCount(numOfVer);
		gv.setSolCount(0);
		gv.setMate(mate);

		return gv;

	}
}