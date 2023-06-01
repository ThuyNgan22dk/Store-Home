package com.example.demo.services;
import java.util.List;

import com.example.demo.entities.Image;
import com.example.demo.entities.User;

public interface ImageService {
    List<Image> getListImage();

    Image getImageById(long id);

    Image save(Image image);

    List<Image> getListByUser(String username);

    void deleteImage(long id);
}
