public class Cluster {
    private String elA;
    private String elB;
    private int clusterID;

    public Cluster(String elA, String elB, int clusterID) {
        this.elA = elA;
        this.elB = elB;
        this.clusterID = clusterID;
    }

    public String getElA() {
        return elA;
    }

    public void setElA(String elA) {
        this.elA = elA;
    }

    public String getElB() {
        return elB;
    }

    public void setElB(String elB) {
        this.elB = elB;
    }

    public int getClusterID() {
        return clusterID;
    }

    public void setClusterID(int clusterID) {
        this.clusterID = clusterID;
    }
}
