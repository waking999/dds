package au.edu.cdu.common.order;

public interface IOrder<V> {
	public IValueComparator<V> getComparator();
}
