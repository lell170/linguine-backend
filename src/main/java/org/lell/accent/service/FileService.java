package org.lell.accent.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class FileService {

    public File createFileFromMultipartData(final MultipartFile multipartFile, final String filename) throws IOException {
        try (final FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            fileOutputStream.write(multipartFile.getBytes());
            return new File(filename);
        }
    }
}
