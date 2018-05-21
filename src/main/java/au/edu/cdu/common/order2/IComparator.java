package au.edu.cdu.common.order2;


import java.util.Comparator;

public interface IComparator extends Comparator<VertexPriorityBean> {

    int compare(VertexPriorityBean a, VertexPriorityBean b);

}
