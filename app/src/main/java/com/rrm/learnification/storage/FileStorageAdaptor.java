package com.rrm.learnification.storage;

import java.io.IOException;
import java.util.List;

public interface FileStorageAdaptor {
    boolean doesFileExist(String fileName);

    void appendLines(String fileName, List<String> lines) throws IOException;

    List<String> readLines(String fileName) throws IOException;

    void deleteFile(String fileName);

    void overwriteLines(String fileName, List<String> lines) throws IOException;
}
