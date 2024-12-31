package com.itm.ithive.service.impl;

import com.itm.ithive.service.UploadService;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class UploadServiceImpl implements UploadService {

    private Path uploadPath = getUploadPath();

    @Override
    public Path getFilePath(String fileName) {
        try {
            Path path = new ClassPathResource("static/appUploads").getFile().toPath();
            return path.resolve(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Path getUploadPath() {
        try {
            File staticdir = new ClassPathResource("static/appUploads").getFile();

            return staticdir.toPath().toAbsolutePath().normalize();
        } catch (IOException e) {
            throw new RuntimeException("can't find static/appUploads");
        }
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
            Path targetLocation = uploadPath.resolve(img_UUID + ".jpeg");
            file.transferTo(targetLocation.toFile());

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
        Path filePath = Paths.get(uploadPath.toString(), UUID);
        File file = filePath.toFile();
        return file.exists();
    }

    @PostConstruct
    public void copyDefaultImageIfNotExists() {
        Path defaultImagePath = Paths.get(uploadPath.toString(), "defaultBlogImage.png");
        System.out.println(defaultImagePath);
        if (Files.notExists(defaultImagePath)) {
            try {
                ClassPathResource defaultImageResource = new ClassPathResource("static/appUploads/defaultBlogImage.png");
                Files.copy(defaultImageResource.getInputStream(), defaultImagePath);
                System.out.println("Default image copied to: " + defaultImagePath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to copy default image", e);
            }
        }
    }
}
