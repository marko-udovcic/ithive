package com.itm.ithive.service.impl;

import com.itm.ithive.service.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadServiceImpl implements UploadService {

    private Path uploadPath = getUploadPath();

    public Path getUploadPath() {
        Path path = Paths.get("appUploads/").toAbsolutePath().normalize();
        return Path.of(path + "\\");
        // for some reason it won't put separator at the end
    }


    @Override
    public ResponseEntity<String> uploadFile(MultipartFile file, String img_UUID) {
        if (!isSupportedContentType(file.getContentType())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unsupported file type.");
        }

        if (file.getSize() > 1_000_000) { // 1 MB size limit
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File size exceeds limit.");
        }

        try {
            file.transferTo(new java.io.File(uploadPath + "\\" + img_UUID + ".jpeg"));
            return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    @Override
    public boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/jpeg") || contentType.equals("application/pdf");
    }

    @Override
    public boolean imageExists(String UUID) {
        File file = new File(uploadPath + "\\" + UUID);

        System.out.println(uploadPath + "\\" + UUID);
        if (file.exists()){
            return true;
        }

        return false;
    }
}
