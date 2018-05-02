package au.edu.cdu;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import au.edu.cdu.problems.dds.GreedyVoteH2LTest;
import au.edu.cdu.problems.dds.GreedyVoteL2HAddTest;
import au.edu.cdu.problems.dds.GreedyVoteL2HDDSTest;
import au.edu.cdu.problems.dds.GreedyVoteL2HTest;

/**
 * a test suit for algorithms
 */
@RunWith(Suite.class)
@SuiteClasses({ GreedyVoteH2LTest.class, GreedyVoteL2HTest.class, GreedyVoteL2HAddTest.class, GreedyVoteL2HDDSTest.class })
public class AlgoTests {

}
