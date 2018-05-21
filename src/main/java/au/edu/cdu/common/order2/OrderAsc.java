package au.edu.cdu.common.order2;



public class OrderAsc implements IOrder {
    public IComparator getComparator() {
        return  new ComparatorAsc();
    }
}
