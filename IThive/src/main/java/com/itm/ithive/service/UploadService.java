package com.itm.ithive.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    public ResponseEntity<String> uploadFile(MultipartFile file, String img_UUID);
    public boolean isSupportedContentType(String contentType);
    public boolean imageExists(String UUID);
}
