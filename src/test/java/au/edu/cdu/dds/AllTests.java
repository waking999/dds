package au.edu.cdu.dds;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * a test suit for all test suits
 */
@RunWith(Suite.class)
@SuiteClasses({ AlgoTests.class, UtilTests.class })
public class AllTests {

}
