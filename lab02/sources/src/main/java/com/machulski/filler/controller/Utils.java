package com.machulski.filler.controller;

import com.machulski.filler.model.FileWrapper;
import lombok.experimental.UtilityClass;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.lang.ref.WeakReference;
import java.nio.file.Path;
import java.util.Optional;

@UtilityClass
public class Utils {
    public String displayFileChooser() {
        String homeDirectory = Optional.ofNullable(FileSystemView.getFileSystemView().getHomeDirectory()).isPresent() ?
                FileSystemView.getFileSystemView().getHomeDirectory().toString() : null;
        JFileChooser jFileChooser = new JFileChooser(homeDirectory);
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.setAcceptAllFileFilterUsed(false);
        return jFileChooser.showDialog(null, "Choose directory") == JFileChooser.APPROVE_OPTION
                ? jFileChooser.getCurrentDirectory().getAbsolutePath() : "";
    }

    public String comboBoxExtensionElementToPhysicalExtensionName(String comboBoxExtensionElement) {
        return comboBoxExtensionElement.substring(2);
    }

    public Path presentationStringToPath(String presentationString) {
        return Path.of(presentationString.split("\t")[2]);
    }

    public WeakReference<FileWrapper> mapPathToFileWrapper(Path path, String fileType) {
        return new WeakReference<>(new FileWrapper(path, fileType));
    }
}
