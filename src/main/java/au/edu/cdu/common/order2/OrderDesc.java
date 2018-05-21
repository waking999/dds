package au.edu.cdu.common.order2;


public class OrderDesc implements IOrder {
    public IComparator getComparator() {
        return new ComparatorDesc();
    }

}
