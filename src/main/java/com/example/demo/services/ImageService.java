package com.example.demo.services;
import java.io.IOException;
import java.util.List;

import com.example.demo.entities.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    List<Image> getListImage();

    Image getImageById(long id);

    Image save(Image image);

    List<Image> getListByUser(long userId);

    void deleteImage(long id);
}
