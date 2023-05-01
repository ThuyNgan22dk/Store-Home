package com.example.demo.services.Impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Image;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repositories.ImageRepository;
import com.example.demo.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<Image> getListImage() {
        // TODO Auto-generated method stub
        return imageRepository.findAll();
    }

    @Override
    public Image getImageById(long id) {
        // TODO Auto-generated method stub
        return imageRepository.findById(id).orElseThrow(() -> new NotFoundException("Image not found width id :" + id));
    }

    @Override
    public Image save(Image image) {
        // TODO Auto-generated method stub
        return imageRepository.save(image);
    }

    @Override
    public List<Image> getListByUser(long userId) {
        // TODO Auto-generated method stub
        return imageRepository.getListImageOfUser(userId);
    }

    @Override
    public void deleteImage(long id) {
        // TODO Auto-generated method stub
        Image image = imageRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Category With Id: " + id));
        imageRepository.delete(image);
    }
}

