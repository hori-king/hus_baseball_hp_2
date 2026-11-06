package com.example.jyunko.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "stored_photos")
@Data
public class StoredPhoto {
    @Id
    private String id;
    private String contentType;
    @Lob
    private byte[] data;
}
