package au.edu.cdu.common.order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;


import au.edu.cdu.common.util.Util;
import edu.uci.ics.jung.graph.Graph;

public class OrderPackageUtil {

    /**
     * Get a list of vertex and its priority by using callback functions
     *
     * @param pb,          priority bean containing (g, dominated map, weight map)
     * @param pcb,priority call back (decide what really priority is: degree, utility,
     *                     ...)
     * @return a list of vertex and its priority
     */
    private static <V, E> List<VertexPriority<V>> getVertexPriorityList(PriorityBean<V, E> pb, IPriority pcb) {
        List<VertexPriority<V>> vertexPriorityList = new ArrayList<>();
        Collection<V> vertices = pb.getG().getVertices();
        for (V i : vertices) {
            float priority = pcb.getPriority(pb, i);
            Util.addElementToList(vertexPriorityList, new VertexPriority<>(i, priority));
        }

        return vertexPriorityList;
    }

    /**
     * Get a priority queue of vertices and their priorities.
     *
     * @param pcb, priority call back (decide what really priority is: degree,
     *             utility, ...)
     * @param ocb, order call back (decide what order to follow: asc, desc, ...)
     * @return a priority queue of vertices and their priorities
     */
    private static <V, E> Queue<VertexPriority<V>> getOrderedVertexPriorityQueue(PriorityBean<V, E> pb,
                                                                                 IPriority pcb, IOrder<V> ocb) {
        List<VertexPriority<V>> vpList = OrderPackageUtil.getVertexPriorityList(pb, pcb);
        Queue<VertexPriority<V>> q = new PriorityQueue<>(ocb.getComparator());
        q.addAll(vpList);
        return q;
    }

    /**
     * get vertices from an ordered vertex priority queue
     *
     * @param q, a queue of ordered vertex priority
     * @return vertex list
     */
    private static <V> List<V> getOrderedVertexList(Queue<VertexPriority<V>> q) {
        List<V> vList = new ArrayList<>(q.size());
        while (!q.isEmpty()) {
            vList.add(q.poll().getVertex());
        }
        return vList;
    }

    /**
     * @param g, graph instance
     * @return a list of vertex by ascending degree
     */
    public static <V, E> List<V> getVertexListDegreeAsc(Graph<V, E> g) {
        IPriorityOrder<V, E> pocb = new DegreeAsc<>();
        /*
         * there is no need for a dominated map (<vertex(V),
         * dominated(boolean)>), and weight map for ordering by degree
         */
        return pocb.getOrderedVertexList(new PriorityBean<V, E>(g, null, null));
    }

    /**
     * @param g, graph instance
     * @return a list of vertices ordered by degree descending
     */
    public static <V, E> List<V> getVertexListDegreeDesc(Graph<V, E> g) {
        IPriorityOrder<V, E> pocb = new DegreeDesc<>();
        /*
         * there is no need for a dominated map (<vertex(V),
         * dominated(boolean)>), and weight map for ordering by degree
         */
        List<V> vList = pocb.getOrderedVertexList(new PriorityBean<V, E>(g, null, null));
        return vList;
    }

    /**
     * @param g, graph instance
     * @param dominatedMap, a map showing vertices dominated status
     * @return a list of vertices ordered by utility descending
     */
    public static <V, E> List<V> getVertexListUtilityDesc(Graph<V, E> g, Map<V, Boolean> dominatedMap) {
        IPriorityOrder<V, E> pocb = new UtilityDesc<>();
        /*
         * there is no need for a weight map (<vertex(V), weigh(float)>) for
         * ordering by utility
         */
        return pocb.getOrderedVertexList(new PriorityBean<V, E>(g, dominatedMap, null));
    }

    /**
     * @param g, graph instance
     * @param weightMap s, a map showing vertices and their weights
     * @return a list of vertices ordered by weight descending
     */
    public static <V, E> List<V> getVertexListWeightDesc(Graph<V, E> g, Map<V, Float> weightMap) {
        IPriorityOrder<V, E> pocb = new WeightDesc<>();
        List<V> vList = pocb.getOrderedVertexList(new PriorityBean<V, E>(g, null, weightMap, null));
        return vList;
    }

    /**
     * @param g, graph instance
     * @param weightMap, a map containing vertices and their weights
     * @return a list of vertices ordered by weight ascending
     */
    public static <V, E> List<V> getVertexListWeightAsc(Graph<V, E> g, Map<V, Float> weightMap) {
        IPriorityOrder<V, E> pocb = new WeightAsc<>();
        return pocb.getOrderedVertexList(new PriorityBean<>(g, null, weightMap, null));
    }


    /**
     * get vertices from an ordered vertex priority queue obtained by pcb and
     * ocb
     *
     * @param pcb, priority call back (decide what really priority is: degree,
     *             utility, ...)
     * @param ocb, order call back (decide what order to follow: asc, desc, ...)
     * @return a list of vertices order by their priorities
     */
    public static <V, E> List<V> getOrderedVertexList(PriorityBean<V, E> pb, IPriority pcb,
                                                      IOrder<V> ocb) {
        Queue<VertexPriority<V>> q = getOrderedVertexPriorityQueue(pb, pcb, ocb);
        return getOrderedVertexList(q);
    }

}
