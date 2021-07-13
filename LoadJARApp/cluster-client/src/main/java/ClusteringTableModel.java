import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ClusteringTableModel extends AbstractTableModel {
    private List<Cluster> clusters = new ArrayList<>();
    private String[] columns;

    public ClusteringTableModel() {
        super();
        this.columns = new String[]{"A", "B", "ClusterID"};
    }

    private void prepareTestData() {
        Cluster c1 = new Cluster("1", "2", 1);
        Cluster c2 = new Cluster("2", "4", 2);
        Cluster c3 = new Cluster("1", "3", 3);
        clusters = List.of(c1, c2, c3);
    }

    public int getRowCount() {
        return clusters.size();
    }

    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Cluster cluster = clusters.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return cluster.getElA();
            case 1:
                return cluster.getElB();
            case 2:
                return cluster.getClusterID();
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void reloadData(List<Cluster> clusters) {
        this.clusters = clusters;
        fireTableDataChanged();
    }
}
