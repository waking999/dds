package au.edu.cdu;

import au.edu.cdu.common.io.DBOperationTest;
import au.edu.cdu.common.io.FileOperationTest;
import au.edu.cdu.common.util.AlgoUtilTest;
import au.edu.cdu.common.util.ConnectComponentsTest;
import au.edu.cdu.common.util.QueueTest;
import au.edu.cdu.common.util.UtilTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * a test suit for utilities classes
 */
@RunWith(Suite.class)
@SuiteClasses({UtilTest.class, FileOperationTest.class, DBOperationTest.class, AlgoUtilTest.class, QueueTest.class, ConnectComponentsTest.class})
public class UtilTests {

}
