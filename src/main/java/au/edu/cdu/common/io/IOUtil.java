package au.edu.cdu.common.io;

import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * this class is used for common methods used for io operations
 * 
 * @author kai wang
 *
 */
public class IOUtil {
	/**
	 * get the adjacent matrix from a file
	 * 
	 * @param inputFile
	 *            , the input file
	 * @return the adjacent matrix
	 */
	public static byte[][] getAMFromFile(String inputFile)
			throws FileNotFoundException, IOException {

		FileOperation fileOperation = getProblemInfo(inputFile);

		byte[][] am = fileOperation.getAdjacencyMatrix();

		return am;
	}

	/**
	 * get the adjacent matrix from an edge pair file
	 * 
	 * @param inputFile
	 *            , the input file
	 * @return the adjacent matrix
	 */
	public static byte[][] getAMFromEdgePairFile(String inputFile)
			throws FileNotFoundException, IOException {

		FileOperation fileOperation = getProblemInfoByEdgePair(inputFile);

		byte[][] am = fileOperation.getAdjacencyMatrix();

		return am;
	}

	/**
	 * get the information from the input file containing vertices number, k,
	 * adjacent matrix and so on.
	 * 
	 * @param inputFile
	 *            , the input file path and name
	 * @return the information from the input file containing vertices number,
	 *         k, adjacent matrix and so on.
	 */
	public static FileOperation getProblemInfoByEdgePair(String inputFile)
			throws FileNotFoundException, IOException {

		FileInfo fileInfo2 = new FileInfo();
		fileInfo2.setInputFile(inputFile);
		FileOperation fileOperation = new FileOperation();
		fileOperation.setFileInfo(fileInfo2);
		fileOperation.retriveProblemInfoByEdgePair();

		return fileOperation;
	}

	/**
	 * get the information from the input file containing vertices number, k,
	 * adjacent matrix and so on.
	 * 
	 * @param inputFile
	 *            , the input file path and name
	 * @return the information from the input file containing vertices number,
	 *         k, adjacent matrix and so on.
	 */
	public static FileOperation getProblemInfo(String inputFile)
			throws FileNotFoundException, IOException {

		FileInfo fileInfo2 = new FileInfo();
		fileInfo2.setInputFile(inputFile);
		FileOperation fileOperation = new FileOperation();
		fileOperation.setFileInfo(fileInfo2);
		fileOperation.retriveProblemInfo();

		return fileOperation;
	}



}