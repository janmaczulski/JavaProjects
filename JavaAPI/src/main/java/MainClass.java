import Backend.Action;
import Forms.MainForm;
import lombok.extern.java.Log;

import javax.swing.*;
import java.util.ResourceBundle;
@Log
public class MainClass {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                Action action = new Action();
                action.control();
            } catch (Exception e) {
                log.severe(e.getMessage());
            }
        });
//        Action action = new Action();
//        action.control();


    }
}
