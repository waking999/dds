package au.edu.cdu.common.order2;


public class ComparatorDesc implements IComparator {
    @Override
    public int compare(VertexPriorityBean a, VertexPriorityBean b) {
        if (a.getPriority() <= b.getPriority()) {
            return 1;
        } else {
            return -1;
        } // returning 0 would merge keys
    }


}
