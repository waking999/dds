package au.edu.cdu.dds;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import au.edu.cdu.dds.algo.ds.GreedyVoteH2LTest;
import au.edu.cdu.dds.algo.ds.GreedyVoteL2HAddTest;
import au.edu.cdu.dds.algo.ds.GreedyVoteL2HDDS;
import au.edu.cdu.dds.algo.ds.GreedyVoteL2HTest;

/**
 * a test suit for algorithms
 */
@RunWith(Suite.class)
@SuiteClasses({ GreedyVoteH2LTest.class, GreedyVoteL2HTest.class, GreedyVoteL2HAddTest.class, GreedyVoteL2HDDS.class })
public class AlgoTests {

}
