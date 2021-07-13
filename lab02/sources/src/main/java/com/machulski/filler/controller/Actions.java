package com.machulski.filler.controller;

import com.machulski.filler.model.FileWrapper;
import com.machulski.filler.model.ImageContent;
import com.machulski.filler.model.TextContent;
import com.machulski.filler.view.FillerForm;
import lombok.extern.java.Log;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log
public class Actions {
    private FillerForm fillerForm;
    private boolean deactivateDirectoryListListener = false;

    private FileManager fileManager = new FileManager();

    public void control() {
        openFillerForm();

        fillerForm.getDirectoryList().getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting() && !deactivateDirectoryListListener)
                onChangeDirectoryList();
        });
        fillerForm.getComboBoxExtension().addActionListener(e -> onChangeComboBoxExtension());
        fillerForm.getChooseStartingDirectoryButton().addActionListener(e -> onClickButtonChooseStartingDirectoryButton());
    }

    private void openFillerForm() {
        if (fillerForm == null || (fillerForm != null && !fillerForm.getFrame().isDisplayable())) {
            fillerForm = new FillerForm();
            fillerForm.getFrame().setVisible(true);
        } else
            fillerForm.getFrame().toFront();
    }

    private void onChangeDirectoryList() {
        System.out.println(fillerForm.getDirectoryList().getSelectedIndex());
        if (fillerForm.getDirectoryList().getSelectedIndex() != -1) {
            String selectedItem = fillerForm.getDirectoryList().getSelectedValue().toString();
            if (!Constants.LIST_MODEL_UP_DIRECTORY_ELEMENT.equals(selectedItem)
                    && Files.isDirectory(Utils.presentationStringToPath(fillerForm.getDirectoryList().getSelectedValue().toString()))) {
                reloadDirectoryPanel(Utils.presentationStringToPath(fillerForm.getDirectoryList().getSelectedValue().toString()).normalize().toString());
            } else if(Constants.LIST_MODEL_UP_DIRECTORY_ELEMENT.equals(selectedItem)) {
                String currentDirectory =Path.of( fileManager.getCurrentDirectory()).getParent().toString();
                reloadDirectoryPanel(currentDirectory);
            } else if(!Files.isDirectory(Utils.presentationStringToPath(fillerForm.getDirectoryList().getSelectedValue().toString()))
            && fillerForm.getDirectoryList().getSelectedValue().toString().endsWith(
                    Utils.comboBoxExtensionElementToPhysicalExtensionName(Constants.COMBO_BOX_TXT_ELEMENT))) {

                loadText(fillerForm.getDirectoryList().getSelectedValue().toString());
            } else if(!Files.isDirectory(Utils.presentationStringToPath(fillerForm.getDirectoryList().getSelectedValue().toString()))
                    && fillerForm.getDirectoryList().getSelectedValue().toString().endsWith(
                    Utils.comboBoxExtensionElementToPhysicalExtensionName(Constants.COMBO_BOX_PNG_ELEMENT))) {
                loadGraphic(fillerForm.getDirectoryList().getSelectedValue().toString());
            }
        }
    }

    private void onChangeComboBoxExtension() {
        onChangeDirectoryList();
    }

    private void reloadDirectoryPanel(String directory) {
        fileManager.scanDirectory(directory, Optional.ofNullable(fillerForm.getComboBoxExtension().getSelectedItem()).isPresent()
                ? fillerForm.getComboBoxExtension().getSelectedItem().toString() : null);
        try {
            DefaultListModel<String> directoryListModel = (DefaultListModel<String>) fillerForm.getDirectoryList().getModel();
            if (directoryListModel.getSize() != 0) {
                deactivateDirectoryListListener = true;
                directoryListModel.removeAllElements();
            }
            deactivateDirectoryListListener = false;
            directoryListModel.addElement(Constants.LIST_MODEL_UP_DIRECTORY_ELEMENT);
            if (!fileManager.getFiles().isEmpty())
                for (WeakReference<FileWrapper> fileWrapper : fileManager.getFiles().values()) {
                    String[] currentDirCatalogues = directory.split("/");
                    FileWrapper fw = Objects.requireNonNull(fileWrapper.get());
                    // Add only the files from current directory
                    if ((!Files.isDirectory(fw.getPath()) && fw.getPath().getParent()
                            .endsWith(currentDirCatalogues[currentDirCatalogues.length - 1])
                            && fw.getPath().toString().split("/")[fw.getPath().toString().split("/").length - 1]
                            .endsWith(Utils.comboBoxExtensionElementToPhysicalExtensionName(fillerForm.getComboBoxExtension().getSelectedItem().toString())))
                        || (Files.isDirectory(fw.getPath()) && fw.getPath().getParent()
                            .endsWith(currentDirCatalogues[currentDirCatalogues.length - 1])))
                            directoryListModel.addElement(Objects.requireNonNull(fileWrapper.get()).getPresentationString());
                }
        } catch (Exception e) {
            log.severe(e.getMessage());
        } finally {
            deactivateDirectoryListListener = false;
        }
    }

    private void onClickButtonChooseStartingDirectoryButton() {
        reloadDirectoryPanel(Utils.displayFileChooser());
    }

    private void loadText(String path) {
        FileWrapper fileWrapper = fileManager.getFiles().get(Utils.presentationStringToPath(path).toString()).get();
        if(!fileWrapper.isInMemory()) {
            try {
                List<String> fileTextContent = Files.readAllLines(Path.of(Utils.presentationStringToPath(path).toString()), StandardCharsets.UTF_8);
                StringBuilder sb = new StringBuilder();
                for(String line : fileTextContent)
                    sb.append(line);
                fileWrapper.setContent(new TextContent(sb.toString()));
                fileWrapper.setInMemory(true);
            } catch (IOException e) {
                log.severe(e.getMessage());
            }
        }
        fillerForm.getPreviewTextPanel().setText(((TextContent)fileWrapper.getContent()).getText());
        fillerForm.reloadPreviewPanel(Constants.COMBO_BOX_TXT_ELEMENT);
    }

    private void loadGraphic(String path) {
        try {
            FileWrapper fileWrapper = fileManager.getFiles().get(Utils.presentationStringToPath(path).toString()).get();
            if(!fileWrapper.isInMemory()) {
                try {
                    fillerForm.getPreviewImagePanel().loadImage(Utils.presentationStringToPath(path).normalize().toString());
                    fileWrapper.setContent(new ImageContent(fillerForm.getPreviewImagePanel().getImage()));
                    fileWrapper.setInMemory(true);
                } catch (IOException e) {
                    log.severe(e.getMessage());
                }
            } else
                fillerForm.getPreviewImagePanel().loadImage(((ImageContent)fileWrapper.getContent()).getImage());
            fillerForm.getPreviewImagePanel().paint(fillerForm.getPreviewImagePanel().getGraphics());
            fillerForm.reloadPreviewPanel(Constants.COMBO_BOX_PNG_ELEMENT);
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
    }
}
