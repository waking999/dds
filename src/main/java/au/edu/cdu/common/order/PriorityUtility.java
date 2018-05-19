package au.edu.cdu.common.order;


import au.edu.cdu.common.util.AlgoUtil;

public class PriorityUtility implements IPriority {

	@Override
	public <V,E> float getPriority(PriorityBean<V,E> pb,V v) {

		return AlgoUtil.getVertexUtility(pb.getG(), v, pb.getDominatedMap());
	}

}
