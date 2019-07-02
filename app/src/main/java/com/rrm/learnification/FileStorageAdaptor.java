package com.rrm.learnification;

import java.io.IOException;
import java.util.List;

interface FileStorageAdaptor {
    boolean doesFileExist(String fileName);

    void appendLines(String fileName, List<String> lines) throws IOException;

    List<String> readLines(String fileName) throws IOException;

    void deleteFile(String fileName);
}
