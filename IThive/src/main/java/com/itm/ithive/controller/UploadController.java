package com.itm.ithive.controller;
import com.itm.ithive.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;


@Controller
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;


    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("img_UUID") String img_UUID) {

        return uploadService.uploadFile(file, img_UUID);
    }

}