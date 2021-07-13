package com.machulski.filler.model;

import com.machulski.filler.controller.Constants;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileWrapper {
    @Getter
    private String presentationString;
    @Getter
    private Path path;
    @Setter
    @Getter
    private Content content = null;
    @Setter
    @Getter
    private String type;
    @Getter
    @Setter
    private boolean isInMemory = false;

    public FileWrapper(Path path, String type) {
        this.path = path;
        this.type = type;
        buildPresentationString();
    }

    private void buildPresentationString() {
        this.presentationString =
                String.format(Constants.FILE_WRAPPER_PRESENTATION_STRING,
                        Files.isDirectory(path) ? Constants.FILE_WRAPPER_DIR_STR : Constants.FILE_WRAPPER_FILE_STR,
                        isInMemory ? Constants.FILE_WRAPPER_IN_MEMORY_STR : Constants.FILE_WRAPPER_NOT_IN_MEMORY_STR,
                        path.normalize().toString());
    }
}
