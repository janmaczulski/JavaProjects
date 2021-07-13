package Forms;


import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

import java.util.Locale;
import java.util.ResourceBundle;

//import static Backend.Action.onClickButtonLanguageButton;
@Getter
@Setter
public class MainForm {
    private JFrame frameMainForm;
    public JButton question1Button;
    public JButton question2Button;
    public JButton question3Button;
    private JPanel mainPanel;
    private JButton languageButton;
    public static ResourceBundle bundleDefault = ResourceBundle.getBundle("resources");
    public static ResourceBundle bundleEn = ResourceBundle.getBundle("resources",new Locale("en", "US"));

    public MainForm ()  {
        frameMainForm = new JFrame("API App");
        frameMainForm.setContentPane(mainPanel);
        frameMainForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameMainForm.pack();
        frameMainForm.setLocationRelativeTo(null);
        frameMainForm.setSize(301,301);
        question1Button.setText(bundleDefault.getString("q1"));
        question2Button.setText(bundleDefault.getString("q2"));
        question3Button.setText(bundleDefault.getString("q3"));
        languageButton.setText(bundleDefault.getString("l"));
        //frameMainForm.setVisible(true);
        //languageButton.addActionListener(e -> onClickButtonLanguageButton());

    }





}
