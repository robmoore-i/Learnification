package com.rrm.learnification.learnification.publication;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class LearnificationPublishingServiceTest {
    @Test
    public void notUsedAnywhereOutsideTheLearnificationPackage() throws IOException {
        String unwantedImportLine = "import com.rrm.learnification.learnification.publication.LearnificationPublishingService;";
        String srcPath = "../app/src/main/java";
        List<Path> sourceJavaFilePaths = Files.walk(Paths.get(srcPath))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
        for (Path path : sourceJavaFilePaths) {
            Files.lines(path)
                    .forEach(line -> {
                        String failureMessage = "The LearnificationPublishingService is unexpectedly imported in the file " + path;
                        assertNotEquals(failureMessage, unwantedImportLine, line);
                    });
        }
    }

    @Test
    public void registeredInTheAndroidManifest() throws IOException {
        String expectedLine = "android:name=\".learnification.publication.LearnificationPublishingService\"";
        String manifestPath = "../app/src/main/AndroidManifest.xml";
        String failureMessage = "Did not find LearnificationPublishingService declaration in the AndroidManifest.xml";
        assertTrue(failureMessage, Files.lines(Paths.get(manifestPath)).anyMatch(line -> expectedLine.equals(line.trim())));
    }
}