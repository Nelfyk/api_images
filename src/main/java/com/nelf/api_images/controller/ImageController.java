package com.nelf.api_images.controller;

import com.nelf.api_images.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class ImageController {

    @Autowired
    StorageService storageService;

    @GetMapping("/{id}")
    public String getImage(@PathVariable String id) {
        return storageService.getUrl(id+".jpg");
    }
}
