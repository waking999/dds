package au.edu.cdu.common.order2;


import au.edu.cdu.common.util.ConstantValue;
import au.edu.cdu.common.util.GlobalVariable;

public class PriorityWeight implements IPriority {

    @Override
    public float getPriority(GlobalVariable gv, int vIdx) {
        if(vIdx==ConstantValue.IMPOSSIBLE_VALUE){
            return 0;
        }
        return gv.getIdxWeight()[vIdx];
    }

}
