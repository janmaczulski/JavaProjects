package Forms;

import lombok.Getter;

import javax.swing.*;
import java.util.Locale;

@Getter
public class FirstQuestion {
    private JFrame frameFirstQuestion;
    private JPanel panelFirstQuestion;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JButton checkAnswerButton;
    private JLabel label;
    private JLabel answerLabel;
    private JLabel userAnswerLabel;
    private JTextField userAnswerTextField;

    public FirstQuestion () {
        frameFirstQuestion = new JFrame("GEOquiz APP");
        JComboBox.setDefaultLocale(Locale.forLanguageTag("PL"));
        frameFirstQuestion.setContentPane(panelFirstQuestion);
        frameFirstQuestion.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameFirstQuestion.pack();
        frameFirstQuestion.setLocationRelativeTo(null);
        frameFirstQuestion.setSize(700, 250);
    }
}
