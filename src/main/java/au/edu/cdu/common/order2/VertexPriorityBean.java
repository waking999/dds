package au.edu.cdu.common.order2;

public class VertexPriorityBean {
    private final static double ZERO_DIFF = 1E-12;
    /**
     * the index of a vertex
     */
    private int vIdx;

    int getVIdx() {
        return vIdx;
    }

    /**
     * the priority of the vertex
     */
    private float priority;


    public float getPriority() {
        return priority;
    }


    public VertexPriorityBean(int vIdx, float priority) {
        this.vIdx = vIdx;
        this.priority = priority;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getVIdx()).append("-");

        float priority = this.getPriority();
        int round = Math.round(priority);
        float diff = Math.abs(priority - round);
        if (diff <= ZERO_DIFF) {
            sb.append(round);
        } else {
            sb.append(priority);
        }


        return sb.toString();
    }

}
