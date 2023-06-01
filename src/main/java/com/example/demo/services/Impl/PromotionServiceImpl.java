package com.example.demo.services.Impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.demo.entities.Promotion;
import com.example.demo.model.request.CreatePromotionRequest;
import com.example.demo.repositories.PromotionRepository;
import com.example.demo.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.demo.exception.NotFoundException;

@Service
public class PromotionServiceImpl implements PromotionService {
    private static final String alpha = "abcdefghijklmnopqrstuvwxyz";    // a-z
    private static final String alphaUpperCase = alpha.toUpperCase();    // A-Z
    private static final String digits = "0123456789";                   // 0-9
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static Random generator = new Random();
    public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
    public LocalDateTime now = LocalDateTime.now();

    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public List<Promotion> findAll() {
        // TODO Auto-generated method stub
        List<Promotion> promotionList = promotionRepository.findAll(Sort.by("id").descending());
        List<Promotion> list = new ArrayList<>();
        for(Promotion promotion: promotionList){
            if (promotion.getDateDeleted() == null) {
                list.add(promotion);
            }
        }
        return list;
    }

    @Override
    public Promotion findCode(String code){
        List<Promotion> promotions = findAll();
        for (Promotion promotion : promotions) {
            if (code.equals(promotion.getCode())) {
                System.out.println(promotion.getCode());
                if (promotion.getQuantity() > 0){
                    return promotion;
                }else if (promotion.getQuantity() == 0) {
                    enablePromotion(promotion.getId());
                }
            }
        }
        return null;
    }

    public static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }

    @Override
    public Promotion createPromotion(CreatePromotionRequest request) {
        // TODO Auto-generated method stub
        Promotion promotion = new Promotion();
        promotion.setName(request.getName());
        promotion.setDetail(request.getDetail());
        promotion.setQuantity(request.getQuantity());
        promotion.setPercent(request.getPercent());
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            code.append(ch);
        }
        promotion.setCode(code.toString());
        promotion.setEnabled(false);
        promotion.setDateCreated(dtf.format(now));
        promotionRepository.save(promotion);
        return promotion;
    }

    @Override
    public Promotion updatePromotion(long id, CreatePromotionRequest request) {
        // TODO Auto-generated method stub
        Promotion promotion = promotionRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Promotion With Id: " + id));
        promotion.setName(request.getName());
        promotion.setDetail(request.getDetail());
        promotion.setQuantity(request.getQuantity());
        promotion.setPercent(request.getPercent());
        promotionRepository.save(promotion);
        return promotion;
    }

    @Override
    public void enablePromotion(long id) {
        // TODO Auto-generated method stub
        Promotion promotion = promotionRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Promotion With Id: " + id));
        promotion.setEnabled(!promotion.isEnabled());
        promotionRepository.save(promotion);
    }

    @Override
    public void deletePromotion(long id) {
        // TODO Auto-generated method stub
        Promotion promotion = promotionRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Category With Id: " + id));
        promotion.setDateDeleted(dtf.format(now));
        promotion.setEnabled(false);
        promotionRepository.save(promotion);
    }

    @Override
    public List<Promotion> getListEnabled() {
        return promotionRepository.findALLByEnabled();
    }
}