package au.edu.cdu.common.order2;


import au.edu.cdu.common.util.GlobalVariable;

public class PriorityWeight implements IPriority {

    @Override
    public float getPriority(GlobalVariable gv, int vIdx) {

        return gv.getIdxWeight()[vIdx];
    }

}
