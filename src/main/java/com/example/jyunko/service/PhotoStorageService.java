package com.example.jyunko.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.imageio.ImageIO;
import com.example.jyunko.entity.StoredPhoto;
import com.example.jyunko.repository.StoredPhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/** Persists uploads in the database because free hosts have ephemeral disks. */
@Service
public class PhotoStorageService {
    private static final long MAX_BYTES = 4 * 1024 * 1024;
    private static final Map<String, String> CONTENT_TYPES = Map.of(
        "jpeg", "image/jpeg", "png", "image/png", "gif", "image/gif");
    private final StoredPhotoRepository photos;

    public PhotoStorageService(StoredPhotoRepository photos) {
        this.photos = photos;
    }

    public String save(MultipartFile file) throws IOException {
        if (file.isEmpty() || file.getSize() > MAX_BYTES) {
            throw new IOException("画像は4MB以下にしてください。");
        }
        byte[] data = file.getBytes();
        String contentType;
        try (var input = ImageIO.createImageInputStream(new ByteArrayInputStream(data))) {
            var readers = ImageIO.getImageReaders(input);
            if (!readers.hasNext()) {
                throw new IOException("JPEG・PNG・GIFの画像を選択してください。");
            }
            var reader = readers.next();
            try {
                reader.setInput(input);
                contentType = CONTENT_TYPES.get(reader.getFormatName().toLowerCase(Locale.ROOT));
                long pixels = (long) reader.getWidth(0) * reader.getHeight(0);
                if (contentType == null || pixels > 25_000_000 || reader.read(0) == null) {
                    throw new IOException("対応していない画像です。");
                }
            } finally {
                reader.dispose();
            }
        }
        StoredPhoto photo = new StoredPhoto();
        photo.setId(UUID.randomUUID().toString());
        photo.setContentType(contentType);
        photo.setData(data);
        photos.save(photo);
        return "/photos/" + photo.getId();
    }
}
