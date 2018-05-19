package au.edu.cdu.common.order;

import java.util.Comparator;

public interface IValueComparator<V> extends Comparator<VertexPriority<V>> {

	public int compare(VertexPriority<V> a, VertexPriority<V> b) ;

}
