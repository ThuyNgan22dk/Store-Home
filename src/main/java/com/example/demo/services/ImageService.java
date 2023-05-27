package com.example.demo.services;
import java.io.IOException;
import java.util.List;

import com.example.demo.entities.Image;
import com.example.demo.entities.User;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    List<Image> getListImage();

    Image getImageById(long id);

    Image save(Image image);
    User saveUser(String username);

    List<Image> getListByUser(long userId);

    void deleteImage(long id);
}
