package Forms;

import lombok.Getter;

import javax.swing.*;
@Getter
public class SecondQuestion {
    private JFrame frame;
    private JPanel panelSecondQuestion;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JLabel label;
    private JTextField userAnswerTextField;
    private JButton checkAnswerButton;
    private JLabel userAnswerLabel;
    private JLabel answerLabel;
    private JLabel answerLabel1;

    public SecondQuestion() {
        frame = new JFrame("GEOquiz APP");
        frame.setContentPane(panelSecondQuestion);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(700, 250);
    }
}
