package com.itm.ithive.controller;

import com.itm.ithive.service.UploadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;


@Controller
@AllArgsConstructor
@RequestMapping("/upload")
public class UploadController {

    private UploadService uploadService;


    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("img_UUID") String img_UUID) {
        return uploadService.uploadFile(file, img_UUID);
    }

}