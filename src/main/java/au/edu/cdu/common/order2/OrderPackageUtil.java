package au.edu.cdu.common.order2;


import au.edu.cdu.common.util.GlobalVariable;
import au.edu.cdu.common.util.Util;

import java.util.*;


public class OrderPackageUtil {

    /**
     * Get a list of vertex and its priority by using callback functions
     *
     * @param gv,          global variable representing graph
     * @param pcb,priority call back (decide what really priority is: degree, utility,
     *                     ...)
     * @return a list of vertex and its priority
     */
    private static List<VertexPriorityBean> getVertexPriorityList(GlobalVariable gv, IPriority pcb) {
        List<VertexPriorityBean> vertexPriorityBeanList = new ArrayList<>();
        int[] idxLst = gv.getIdxLst();
        for (int i : idxLst) {
            float priority = pcb.getPriority(gv, i);
            Util.addElementToList(vertexPriorityBeanList, new VertexPriorityBean(i, priority));
        }

        return vertexPriorityBeanList;
    }

    /**
     * Get a priority queue of vertices and their priorities.
     *
     * @param pcb, priority call back (decide what really priority is: degree,
     *             utility, ...)
     * @param ocb, order call back (decide what order to follow: asc, desc, ...)
     * @return a priority queue of vertices and their priorities
     */
    private static TreeSet<VertexPriorityBean> getOrderedVertexPriorityQueue(GlobalVariable gv,
                                                                           IPriority pcb, IOrder ocb) {
        List<VertexPriorityBean> vpList = OrderPackageUtil.getVertexPriorityList(gv, pcb);
        TreeSet<VertexPriorityBean> q = new TreeSet<>(ocb.getComparator());
       for(VertexPriorityBean b:vpList){
           q.add(b);
       }
        return q;
    }

    /**
     * get vertices from an ordered vertex priority queue
     *
     * @param q, a queue of ordered vertex priority
     * @return vertex list
     */
    private static int[] getOrderedVertexList(TreeSet<VertexPriorityBean> q) {
        int qSize = q.size();
        int[] vList = new int[qSize];

        int i = 0;
        while (!q.isEmpty()) {
            vList[i] = q.pollFirst().getVIdx();
            i++;

        }
        return vList;
    }


    /**
     * @return a list of vertices ordered by utility descending
     */
    public static int[] getVertexListUtilityDesc(GlobalVariable gv) {
        IOrderedList pocb = new OrderedListUtilityDesc();

        return pocb.getOrderedVertexList(gv);
    }

    /**
     * @return a list of vertices ordered by weight descending
     */
    public static int[] getVertexListWeightDesc(GlobalVariable gv) {
        IOrderedList pocb = new OrderedListWeightDesc();
        return pocb.getOrderedVertexList(gv);
    }

    /**
     * @return a list of vertices ordered by weight ascending
     */
    public static int[] getVertexListWeightAsc(GlobalVariable gv) {
        IOrderedList pocb = new OrderedListWeightAsc();
        return pocb.getOrderedVertexList(gv);
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
    static int[] getOrderedVertexList(GlobalVariable gv, IPriority pcb,
                                      IOrder ocb) {
        TreeSet<VertexPriorityBean> q = getOrderedVertexPriorityQueue(gv, pcb, ocb);
        return getOrderedVertexList(q);
    }

}
