package au.edu.cdu.common.order2;


import au.edu.cdu.common.util.GlobalVariable;

/**
 * The class to represent priority (degree/utility/weight...)
 *
 * @author kwang1
 */
public interface IPriority {
    float getPriority(GlobalVariable gv, int vIdx);
}
