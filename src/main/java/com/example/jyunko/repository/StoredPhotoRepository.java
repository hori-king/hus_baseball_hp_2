package com.example.jyunko.repository;

import com.example.jyunko.entity.StoredPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoredPhotoRepository extends JpaRepository<StoredPhoto, String> {
}
