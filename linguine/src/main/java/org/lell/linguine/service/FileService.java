package org.lell.linguine.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FileService {

    public File createFileFromMultipartData(final MultipartFile multipartFile, final String filename) throws IOException {
        try (final FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            fileOutputStream.write(multipartFile.getBytes());
            return new File(filename);
        }
    }

    public Set<String> getLinesForCsvFile(final File resourceFile) throws IOException {
        final Stream<String> lines = Files.lines(resourceFile.toPath());
        final Set<String> words = lines.collect(Collectors.toSet());
        lines.close();
        return words;
    }
}
