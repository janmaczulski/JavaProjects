package com.md5checker.ui;

import com.md5checker.checker.Snapshot;
import com.md5checker.checker.Utilities;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class UserInterface extends JFrame {

    private Path openFile = null;

    private List<Snapshot> savedSnapshots = new LinkedList<>();
    private boolean saved = true;

    private JTextArea jTextArea = new JTextArea();

    private JFileChooser getFileChooser() {
        JFileChooser jFileChooser = new JFileChooser(System.getProperty("user.dir"));

        jFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                Path path = Paths.get(file.getAbsolutePath());
                return path.toString().endsWith(".md5");
            }
            @Override
            public String getDescription() {
                return "*.md5";
            }
        });

        return jFileChooser;
    }

    private void init() {
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initMenu();
        setVisible(true);
    }

    private void showErrorMessage(String message, String tittle) {
        JOptionPane.showMessageDialog(jTextArea, message, tittle, JOptionPane.ERROR_MESSAGE);
    }

    private void showInformationMessage(String message, String tittle) {
        JOptionPane.showMessageDialog(jTextArea, message, tittle, JOptionPane.INFORMATION_MESSAGE);
    }

    private Snapshot addSnapshot() throws IOException {
        JFileChooser fileChooser = getFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(jTextArea);
        if (result == JFileChooser.APPROVE_OPTION) {
            Path file = fileChooser.getSelectedFile().toPath().toAbsolutePath();
            return new Snapshot(file);
        }
        return null;
    }

    private void selectFile() {
        selectFile(false);
    }

    private void selectFile(boolean load) {
        JFileChooser fileChooser = getFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(jTextArea);
        if(result == JFileChooser.APPROVE_OPTION) {
            Path file = fileChooser.getSelectedFile().toPath().toAbsolutePath();
            if (!file.toString().endsWith(".md5"))
                file = Paths.get(file.toString() + ".md5");
            if (!load) {
                if (Utilities.createFile(file))
                    setFile(file);
                else
                    showErrorMessage("Something went wrong", "Error while creating");
            } else {
                savedSnapshots.clear();
                if (Files.exists(file))
                    savedSnapshots.addAll(Utilities.loadFile(file));
                else
                    showInformationMessage("file does not exist", "Error while loading");
            }
        }
    }

    private void loadFile() {
        if (!saved)
            updateFile();
        selectFile(true);
        updateTextArea();
    }

    private void updateWindow() {
        invalidate();
        repaint();
    }

    private void initMenu() {
        JMenuBar jMenuBar = new JMenuBar();

        JMenu jMenuFile = new JMenu("File");
        JMenu jMenuChecker = new JMenu("Checker");

        JMenuItem jMenuItemCreate = jMenuFile.add("Create new snapshots file.");
        JMenuItem jMenuItemOpen = jMenuFile.add("Open snapshots file.");
        JMenuItem jMenuItemSave = jMenuFile.add("Save snapshots file.");

        JMenuItem jMenuItemAddSnapshot = jMenuChecker.add("Add new snapshot.");
        JMenuItem jMenuItemRefreshSnapshots = jMenuChecker.add("Refresh snapshots.");

        jMenuItemAddSnapshot.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    savedSnapshots.add(addSnapshot());
                    updateTextArea();
                    updateWindow();
                } catch (IOException ex) {
                    showErrorMessage("File not exists.", "Bad file path.");
                }
            }
        });

        jMenuItemRefreshSnapshots.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTextArea();
                //updateWindow();
            }
        });

        jMenuItemCreate.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile();
                //updateWindow();
            }
        });

        jMenuItemOpen.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFile();
                //updateWindow();
            }
        });

        jMenuItemSave.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (openFile == null) {
                    showInformationMessage("No save file selected", "Select a save file");
                    selectFile();
                } else {
                    updateFile();
                }
                //updateWindow();
            }
        });

        jMenuFile.add(jMenuItemCreate);
        jMenuFile.add(jMenuItemOpen);
        jMenuFile.add(jMenuItemSave);
        jMenuBar.add(jMenuFile);

        jMenuChecker.add(jMenuItemAddSnapshot);
        jMenuChecker.add(jMenuItemRefreshSnapshots);
        jMenuBar.add(jMenuChecker);

        jTextArea.setEditable(false);

        add(jTextArea);

        setJMenuBar(jMenuBar);
    }

    private void setFile(Path path) {
        setTitle("MD5 : File opened : " + path.toString());
        updateTextArea();
        openFile = path;
    }

    private void updateSnapshots() {
        for (Snapshot snapshot : savedSnapshots)
            snapshot.update();
    }

    private void updateFile() {
        if (openFile == null)
            showInformationMessage("No save file selected", "Select a save file");
        else if (savedSnapshots.size() == 0)
            showInformationMessage("Select files to be saved", "No file selected");
        else if (!Utilities.createFile(openFile))
            showErrorMessage("Error with file " + openFile.toString(), "Error while saving");
        else {
            updateSnapshots();
            Utilities.saveFile(openFile, savedSnapshots);
            updateTextArea();
            saved = true;
        }
    }

    private void updateTextArea() {
        jTextArea.setText("");
        if (savedSnapshots.size() == 0) {
            jTextArea.append("No files selected.");
        } else {
            jTextArea.append("Files:\n");
            for (Snapshot snapshot : savedSnapshots) {
                jTextArea.append(snapshot.getFilePath() + " : " + snapshot.getHash() + " ");
                try {
                    if (snapshot.check())
                        jTextArea.append("not modified.\n");
                    else
                        jTextArea.append("modified and new hash : " + snapshot.getNewHash() + "\n");
                } catch (IOException ex) {
                    jTextArea.append("deleted.\n");
                }
            }
        }
    }

    public UserInterface() {
        super(Utilities.Algorithm);
        init();
    }

}
