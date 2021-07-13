package com.md5checker.checker;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Snapshot implements Serializable {

    private String filePath;
    private transient Path pFilePath;
    private String hash;
    private transient String newHash;

    public Snapshot(Path filePath) throws IOException {
        this.pFilePath = filePath;
        this.filePath = filePath.toString();
        check();
        update();
    }

    public boolean check() throws IOException {
        if (pFilePath == null)
            pFilePath = Paths.get(filePath);
        newHash = Utilities.getFileHash(pFilePath);
        return newHash.equals(hash);
    }

    public void update() {
        hash = newHash;
    }

    public Path getFilePath() {
        return Paths.get(filePath);
    }

    public String getHash() {
        return hash;
    }

    public String getNewHash() {
        return newHash;
    }
}
