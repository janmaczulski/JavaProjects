import org.example.api.LogService;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;

public class MainForm {
    private JTable tableCluster;
    private JComboBox comboBoxChooseAlg;
    private JButton useClusterAlgorithmButton;
    private JPanel panel;

    public MainForm() {
        useClusterAlgorithmButton.addActionListener(e -> {
            ServiceLoader<LogService> loader = ServiceLoader.load(LogService.class);

            JFileChooser fileChooser = new JFileChooser();
            int returnedValue = fileChooser.showOpenDialog(null);
            if(returnedValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    for (LogService service : loader) {
                        service.execute(file.getAbsolutePath());
                    }
                    List<Cluster> result = new ArrayList<>();
                    List<String> clusters = Files.readAllLines(Path.of("sampleClustered.csv"));
                    clusters.forEach(c -> {
                        try {
                            List<String> cluster = Arrays.asList(c.split(","));
                            Cluster cl = new Cluster(cluster.get(0), cluster.get(1), Integer.valueOf(cluster.get(2)));
                            result.add(cl);
                        } catch (Exception ex1) {
                            System.err.println("Invalid file res line: " + ex1.getMessage());
                        }
                    });

                    ((ClusteringTableModel) tableCluster.getModel()).reloadData(result);
                } catch (Exception ex) {
                    JOptionPane optionPane = new JOptionPane();
                    optionPane.setMessage(ex.getMessage());
                    optionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
                    JDialog dialog = optionPane.createDialog(null, "Error");
                    dialog.setVisible(true);
                    System.err.println(ex.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500,700);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        tableCluster = new JTable();
        tableCluster.setModel(new ClusteringTableModel());
    }
}
