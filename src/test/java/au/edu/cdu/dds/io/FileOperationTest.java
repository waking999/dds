package au.edu.cdu.dds.io;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

import org.junit.Test;

import au.edu.cdu.dds.TestUtil;
import au.edu.cdu.dds.io.FileOperation;
import au.edu.cdu.dds.util.GlobalVariable;

/**
 * a test class for file operation class
 */
public class FileOperationTest {
	@Test(expected = IOException.class)
	public void testRetriveProblemInfoByEdgePairIOExp() throws IOException {
		String filePath = "";
		new FileOperation().readGraphByEdgePair(filePath);
	}

	@Test(expected = NoSuchFileException.class)
	public void testRetriveProblemInfoByEdgePairFoundExp() throws IOException {
		String filePath = TestUtil.getCurrentPath() + "/src/test/resources/samplea.txt";

		new FileOperation().readGraphByEdgePair(filePath);
	}

	@Test
	public void testRetriveProblemInfoByEdgePairNormal() throws IOException {

		String filePath = TestUtil.getCurrentPath() + "/src/test/resources/sample1.txt";

		GlobalVariable<String, String> gv = new FileOperation().readGraphByEdgePair(filePath);
		TestUtil.printGlobalVariableStatus(gv);

	}
}