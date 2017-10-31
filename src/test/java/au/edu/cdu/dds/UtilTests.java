package au.edu.cdu.dds;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import au.edu.cdu.dds.io.DBOperationTest;
import au.edu.cdu.dds.io.FileOperationTest;
import au.edu.cdu.dds.util.UtilTest;

/**
 * a test suit for utilities classes
 */
@RunWith(Suite.class)
@SuiteClasses({ UtilTest.class, FileOperationTest.class, DBOperationTest.class })
public class UtilTests {

}
