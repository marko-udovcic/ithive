package com.itm.ithive.service.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface UploadService {
    public ResponseEntity<String> uploadFile(MultipartFile file, String img_UUID);

    public boolean isSupportedContentType(String contentType);

    public boolean imageExists(String UUID);

    public Path getFilePath(String fileName);
}
