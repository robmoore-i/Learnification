package com.rrm.learnification.files;

import java.io.IOException;
import java.util.List;

public interface FileStorageAdaptor {
    void appendLines(String fileName, List<String> lines) throws IOException;

    List<String> readLines(String fileName) throws IOException;

    void deleteFile(String fileName);

    void overwriteLines(String fileName, List<String> lines) throws IOException;
}
