package au.edu.cdu.common.order2;


import au.edu.cdu.common.util.GlobalVariable;



/**
 * @author kwang1 An interface for implementing ordering vertices by
 * (degree/utility/...) from (high to low/low to high/...)
 */
public interface IOrderedList {
    /**
     * get an ordered vertex list
     *
     * @return ordered vertex lists
     */
    int[] getOrderedVertexList(GlobalVariable gv);
}
