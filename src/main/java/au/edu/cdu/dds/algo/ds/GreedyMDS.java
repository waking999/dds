package au.edu.cdu.dds.algo.ds;

import au.edu.cdu.dds.util.GlobalVariable;
import au.edu.cdu.dds.util.Util;

/**
 * Greedy Native Algorithm for dominating set based hybrid graph representation.
 * 
 * @author kwang1
 *
 * @param <ET>
 * @param <ST>
 */
public class GreedyMDS<ET, ST> implements IMDS<ET, ST> {
	// private static Logger log = LogUtil.getLogger(GreedyMDS.class);

	/**
	 * greedy algorithm for dominating set based hybrid graph representation.
	 * 
	 * @param gv,
	 *            global variable
	 * @param card,
	 *            cardinality
	 * @param freq,
	 *            frequency
	 * @return
	 */
	private int greedy(GlobalVariable<ET, ST> gv, int[] card, int[] freq) {

		int solCount = gv.getSolCount();
		int[] sol = gv.getSol();
		int solPtr = gv.getSolPtr();

		int s1 = card[0];
		int e1 = freq[0];

		while (e1 > 0) {
			//get the set with highest cardinality
			int set = Util.getMaxCardinalitySetIndex(gv, card, s1);
			
			int tempCard = card[set];
			Util.addSetToCover(gv, card, freq, s1, e1, set);
			e1 = e1 - tempCard;
			s1 = s1 - 1;
			sol[solPtr++] = set;
			solCount++;

		}

		card[0] = s1;
		freq[0] = e1;
		gv.setCard(card);
		gv.setFreq(freq);
		gv.setSol(sol);
		gv.setSolCount(solCount);
		gv.setSolPtr(solPtr);
		gv.setBestSol(sol);
		gv.setBestSolCount(solCount);

		return solCount;
	}

	public int run(GlobalVariable<ET, ST> gv) {
		int[] card = gv.getCard();
		int[] freq = gv.getFreq();
		greedy(gv, card, freq);
		return gv.getSolCount();
	}
}
