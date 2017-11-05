package au.edu.cdu.dds.algo.sc;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import au.edu.cdu.dds.util.ExistQualifiedSet;
import au.edu.cdu.dds.util.Util;

/**
 * @author kwang1
 *         1. the exactly same as the basic algorithm 2. apply the reduction
 *         rule exhaustedly 3. return not only solution size but also solutions
 */
public class MSC3 implements IMSC{
	Map<String, Set<Integer>> map;

	public MSC3(Map<String, Set<Integer>> map) {
		this.map=map;
		this.rr= new ReturnResult<String>(0, new HashSet<String>());

	}
	public void setMap(Map<String, Set<Integer>> map) {
		this.map = map;
	}

//	public void setRr(ReturnResult<String> rr) {
//		this.rr = rr;
//	}

	ReturnResult<String> rr;

//	public ReturnResult<String> getRr() {
//		return rr;
//	}
	public ReturnResult<String> run() {
		return branch(map, rr);
	}

	private ReturnResult<String> branch(Map<String, Set<Integer>> map, ReturnResult<String> rr) {

		if (map == null || map.size() == 0) {
			return rr;
		}

		// subset rule
		ExistQualifiedSet<String> exist = Util.existSubset(map);
		if (exist.isExist()) {
			do {
				String setKey = exist.getSetKey();
				map.remove(setKey);
				exist = Util.existSubset(map);
			} while (exist.isExist());
			Map<String, Set<Integer>> mapCopy = Util.copyMap(map);

			return branch(mapCopy, rr);
		}
		Set<Integer> uList = Util.unionSets(map);

		// unique set for an element
		exist = Util.existUniqueSetForAElement(uList, map);
		if (exist.isExist()) {
			String setIndex = exist.getSetKey();
			//Set<Integer> si = map.get(setIndex);

			// Util.deleteSet(map, si);
			map.remove(setIndex);

			exist = Util.existSubset(map);
			if (exist.isExist()) {
				do {
					String setIndexPrime = exist.getSetKey();
					map.remove(setIndexPrime);
					exist = Util.existSubset(map);
				} while (exist.isExist());
			}

			rr.getResults().add(setIndex);
			rr.setResultSize(rr.getResultSize() + 1);
			Map<String, Set<Integer>> mapCopy = Util.copyMap(map);

			return branch(mapCopy, rr);
		}
		String siIndex = Util.getMaxCardinalitySetIndex(map);
		// Set<Integer> si = map.get(siIndex);

		// branch
		Map<String, Set<Integer>> mapCopy = Util.copyMap(map);
		mapCopy.remove(siIndex);
		ReturnResult<String> rr1 = branch(mapCopy, rr);

		mapCopy = Util.copyMap(map);
		// Util.deleteSet(mapCopy, si);
		mapCopy.remove(siIndex);

		rr.getResults().add(siIndex);
		rr.setResultSize(rr.getResultSize() + 1);
		ReturnResult<String> rr2 = branch(mapCopy, rr);

		if (rr1.getResultSize() < rr2.getResultSize()) {
			return rr1;
		} else {
			return rr2;
		}

	}

}
