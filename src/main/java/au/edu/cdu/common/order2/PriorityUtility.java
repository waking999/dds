package au.edu.cdu.common.order2;


import au.edu.cdu.common.util.AlgoUtil;
import au.edu.cdu.common.util.GlobalVariable;

public class PriorityUtility implements IPriority {

    @Override
    public float getPriority(GlobalVariable gv, int vIdx) {
        return AlgoUtil.getVertexUtility(gv, vIdx);
    }

}
