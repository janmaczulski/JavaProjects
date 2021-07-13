package Forms;

import lombok.Getter;

import javax.swing.*;
@Getter
public class ThirdQuestion {
    private JFrame frame;
    private JPanel panelThirdQuestion;
    private JLabel questionLabel;
    private JComboBox comboBox1;
    private JTextField textFieldLess;
    private JButton checkAnswerButton;
    private JTextField userAnswerTextField;
    private JLabel userAnswerLabel;
    private JLabel answerLabel;
    private JLabel answerLabel1;
    private JTextField textFieldMore;

    public ThirdQuestion(){
        frame = new JFrame("GEOquiz APP");
        frame.setContentPane(panelThirdQuestion);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(700, 250);
    }
}
