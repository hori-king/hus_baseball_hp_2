package com.example.jyunko.controller;

import java.time.Duration;
import com.example.jyunko.repository.StoredPhotoRepository;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhotoController {
    private final StoredPhotoRepository photos;

    public PhotoController(StoredPhotoRepository photos) {
        this.photos = photos;
    }

    @GetMapping("/photos/{id}")
    public ResponseEntity<byte[]> photo(@PathVariable String id) {
        return photos.findById(id).map(photo -> ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(photo.getContentType()))
            .header("X-Content-Type-Options", "nosniff")
            .cacheControl(CacheControl.maxAge(Duration.ofDays(30)).cachePublic())
            .body(photo.getData())).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
