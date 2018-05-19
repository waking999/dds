package au.edu.cdu.common.order;

import au.edu.cdu.common.util.AlgoUtil;

public class PriorityDegree implements IPriority {

	@Override
	public <V,E> float getPriority(PriorityBean<V,E> pb,V v) {
		//return pb.getG().degree(v);
		return AlgoUtil.getVertexDegree(pb.getG(), v);
	}

}
