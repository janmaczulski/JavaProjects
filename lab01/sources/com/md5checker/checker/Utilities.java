package com.md5checker.checker;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

public class Utilities {
    public static final String Algorithm = "MD5";

    private static MessageDigest messageDigest = null;

    private static String getHash(byte[] bytes) {
        if (messageDigest == null) {
            try {
                messageDigest = MessageDigest.getInstance(Algorithm);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        messageDigest.reset();
        messageDigest.update(bytes);
        String hash = new BigInteger(1, messageDigest.digest()).toString(16);
        while (hash.length() < 32)
            hash = "0" + hash;
        return hash;
    }

    public static String getFileHash(Path filePath) throws IOException {
        byte[] fileBytes = Files.readAllBytes(filePath);
        return getHash(fileBytes);
    }

    public static boolean createFile(Path file) {
        try {
            if (Files.exists(file))
                Files.delete(file);
            Files.createFile(file);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public static boolean saveFile(Path file, List<Snapshot> snapshotList) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            for(Snapshot snapshot : snapshotList) {
                objectOutputStream.writeObject(snapshot);
            }
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public static List<Snapshot> loadFile(Path file) {
        try (ObjectInputStream objectOutputStream = new ObjectInputStream(new FileInputStream(file.toFile()))) {
            List<Snapshot> loadedSnapshotList = new LinkedList<>();
            try {
                for(;;) {
                    Snapshot snapshot = (Snapshot) objectOutputStream.readObject();
                    loadedSnapshotList.add(snapshot);
                }
            } catch (EOFException ex) {

            }
            return loadedSnapshotList;
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }
}
