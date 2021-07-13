package controllers;

import classLoaders.AppClassLoader;

import newPackage.NewClass;
import processing.StatusListener;
import processing.impl.StatusListenerImpl;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

public class Controller extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTable table;
    private JTextPane userTextPane;
    private JTextPane changedTextPane;
    private JButton generateButton;
    private JProgressBar progressBar;
    private JButton loadButton;
    private JButton unloadClass;
    public JButton loadPath;
    private JLabel classInfo;
    private AppClassLoader appClassLoader;
    private List<String> classList;
    private Class<?> testClass;
    private Object testObject;
    private JComboBox comboBox;
    //DefaultListModel<String> model;
    //private JList<String> jList;
    public String path;
    StatusListener statusListener;
    JLabel progressLable;
    private Timer clearTimer = new Timer(5000, this);
    Method method;



    public static void main(String[] args) {
        NewClass.CountTo(100);

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Controller frame = new Controller();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public Controller() throws Exception {
        clearTimer.setRepeats(false);


        statusListener = new StatusListenerImpl(this);

        classList = new ArrayList<>();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 550);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        userTextPane = new JTextPane();
        userTextPane.setBounds(20, 25, 300, 200);
        contentPane.add(userTextPane);

        changedTextPane = new JTextPane();
        changedTextPane.setEditable(false);
        changedTextPane.setBounds(20, 300, 300, 200);
        contentPane.add(changedTextPane);

        generateButton = new JButton("make smth with text");
        generateButton.setBounds(20, 225, 200, 25);
        generateButton.addActionListener(e -> {
                    try {
                        generateText();
                    }  catch (NoSuchMethodException noSuchMethodException) {
                        noSuchMethodException.printStackTrace();
                    } catch (InvocationTargetException invocationTargetException) {
                        invocationTargetException.printStackTrace();
                    } catch (IllegalAccessException illegalAccessException) {
                        illegalAccessException.printStackTrace();
                    }

        });
        contentPane.add(generateButton);

        progressLable = new JLabel("Progress:");
        //progressLable.setFont(new Font("Dialog", Font.BOLD, 14));
        progressLable.setBounds(350, 220, 120, 15);
        contentPane.add(progressLable);

        progressBar = new JProgressBar();
        progressBar.setBounds(350, 240, 150, 10);
        contentPane.add(progressBar);
        progressBar.setValue(0);

        comboBox = new JComboBox();
        comboBox.setBounds(350, 25, 150, 30);
        contentPane.add(comboBox);

        JLabel sourceLable = new JLabel("write ur text here:");
        sourceLable.setBounds(20, 10, 150, 15);
        contentPane.add(sourceLable);

        JLabel resultLable = new JLabel("changed text:");
        resultLable.setBounds(20, 270, 100, 15);
        contentPane.add(resultLable);

        JLabel classLable = new JLabel("classes:");
        classLable.setBounds(350, 10, 100, 15);
        contentPane.add(classLable);

        loadButton = new JButton("load class");
        loadButton.setBounds(350, 60, 150, 25);
        loadButton.addActionListener(
                e -> {
                    try {
                        load();
                    } catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException instantiationException) {
                        instantiationException.printStackTrace();
                    }
                });
        contentPane.add(loadButton);

        findInDir("/Users/janmaczulski/Desktop/Classes/");
        unloadClass = new JButton("unload class");
        unloadClass.setBounds(350, 90, 146, 25);
        unloadClass.addActionListener(e -> {
            try {
                unload();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        contentPane.add(unloadClass);

        JLabel classInfoLable = new JLabel("getInfo Method:");
        classInfoLable.setBounds(350, 150, 200, 20);
        contentPane.add(classInfoLable);

        classInfo = new JLabel("");
        classInfo.setBounds(350, 175, 200, 20);
        contentPane.add(classInfo);
        changedTextPane.setVisible(false);
        generateButton.setEnabled(false);
    }

    public void findInDir(String dirPath){
        path = dirPath;
        System.out.println("PATH: " + path);
        try {
            appClassLoader = new AppClassLoader(path);
            findClasses();

            for (String str: classList) {
                comboBox.addItem(str);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public void setProgress(int progress) {
        progressBar.setValue(progress);
    }

    public void findClasses() throws Exception {
        classList = AppClassLoader.returnListFileOfDir();
        System.out.println(classList.toString());
    }



    private void unload() throws Throwable {
        testObject = null;
        testClass = null;
        appClassLoader = new AppClassLoader(path);
        method = null;
        System.gc();

        classInfo.setText("Unloaded");
        generateButton.setEnabled(false);
        loadButton.setEnabled(true);
        unloadClass.setEnabled(false);
    }

    public void generateText() throws  NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        changedTextPane.setText("");
        changedTextPane.setVisible(false);

        Method submitTask = testClass.getDeclaredMethod("submitTask",String.class, StatusListener.class);
        Method getResult = testClass.getDeclaredMethod("getResult");


        submitTask.invoke(testObject, userTextPane.getText(), statusListener);
        changedTextPane.setText((String) getResult.invoke(testObject));
        changedTextPane.setVisible(true);
    }

    public void setTextResult(boolean setVisible) {
        changedTextPane.setVisible(setVisible);
    }

    public void load() throws InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("Selected: " + comboBox.getSelectedItem().toString());
        testClass = appClassLoader.loadClass(comboBox.getSelectedItem().toString());
        testObject = testClass.getDeclaredConstructor().newInstance();


        method = testClass.getMethod("getInfo");

        classInfo.setText((String) method.invoke(testObject));
        generateButton.setEnabled(true);
        loadButton.setEnabled(false);
        unloadClass.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}


