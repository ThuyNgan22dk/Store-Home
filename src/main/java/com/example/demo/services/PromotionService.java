package com.example.demo.services;

import com.example.demo.entities.Promotion;
import com.example.demo.model.request.CreatePromotionRequest;

import java.util.List;

public interface PromotionService {
    List<Promotion> findAll();

    List<Promotion> getListEnabled();

    public Promotion findCode(String code);

    Promotion createPromotion(CreatePromotionRequest request);

    Promotion updatePromotion(long id, CreatePromotionRequest request);

    void enablePromotion(long id);

    void deletePromotion(long id);
}
