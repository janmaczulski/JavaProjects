package com.machulski.filler.controller;

import com.machulski.filler.model.FileWrapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class FileManager {
    @Setter
    private String currentDirectory = null;
    private Map<String, WeakReference<FileWrapper>> files = new HashMap<>();

    public void scanDirectory(String directory, String fileType) {
        this.currentDirectory = directory;
        File[] directoryContent = new File(directory).listFiles();
        String fileTypePhysicalExtension = Utils.comboBoxExtensionElementToPhysicalExtensionName(fileType);
        if (Optional.ofNullable(directoryContent).isPresent() && directoryContent.length != 0) {
            List<Path> paths = Stream.of(directoryContent)
                    .filter(file -> (!file.isDirectory()
                            && fileTypePhysicalExtension.equals(FilenameUtils.getExtension(file.getName())))
                            || file.isDirectory())
                    .map(File::toPath)
                    .collect(Collectors.toList());
            System.out.println(paths);
            for (Path path : paths)
                files.put(path.normalize().toString(), Utils.mapPathToFileWrapper(path, fileType));
        }
    }
}
