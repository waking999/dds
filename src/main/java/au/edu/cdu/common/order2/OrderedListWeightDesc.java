package au.edu.cdu.common.order2;



import au.edu.cdu.common.util.GlobalVariable;



/**
 * 
 * @author kwang1 A class for implementing ordering vertices by degree from high
 *         to low;
 *

 */
public class OrderedListWeightDesc implements IOrderedList {
	@Override
	public int[] getOrderedVertexList(GlobalVariable gv) {
		IPriority pcb = new PriorityWeight();
		IOrder  ocb = (IOrder) new OrderDesc ();
		return OrderPackageUtil.getOrderedVertexList(gv, pcb, ocb);
	}
}
