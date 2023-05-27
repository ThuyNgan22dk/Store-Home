package com.example.demo.controllers;

import com.example.demo.entities.Promotion;
import com.example.demo.model.request.CreatePromotionRequest;
import com.example.demo.model.response.MessageResponse;
import com.example.demo.services.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/promotion")
@CrossOrigin(origins = "*",maxAge = 3600)
public class PromotionController {
    @Autowired
    private PromotionService promotionService;

    @GetMapping("/")
    @Operation(summary="Lấy danh sách mã giảm giá")
    public ResponseEntity<?> getListPromotion(){
        List<Promotion> promotions = promotionService.findAll();
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/enabled")
    @Operation(summary="Lấy ra danh sách mã giảm giá đã kích hoạt")
    public ResponseEntity<List<Promotion>> getListEnabled(){
        List<Promotion> promotions = promotionService.getListEnabled();
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/findCode/{code}")
    @Operation(summary="Lấy ra danh sách mã giảm giá đã kích hoạt")
    public ResponseEntity<Promotion> getPromotionByCode(@PathVariable String code){
        Promotion promotion = promotionService.findCode(code);
        return ResponseEntity.ok(promotion);
    }

    @PostMapping("/create")
    @Operation(summary="Tạo mới mã giảm giá")
    public ResponseEntity<?> createPromotion(@Valid @RequestBody CreatePromotionRequest request){
        Promotion promotion = promotionService.createPromotion(request);
        return ResponseEntity.ok(promotion);
    }

    @PutMapping("/update/{id}")
    @Operation(summary="Tìm mã giảm giá bằng id và cập nhật mã giảm giá đó")
    public ResponseEntity<?> updatePromotion(@PathVariable long id, @Valid @RequestBody CreatePromotionRequest request){
        Promotion promotion = promotionService.updatePromotion(id, request);
        return ResponseEntity.ok(promotion);
    }

    @PutMapping("/enable/{id}")
    @Operation(summary="Kích hoạt mã giảm giá bằng id")
    public ResponseEntity<?> enabled(@PathVariable long id){
        promotionService.enablePromotion(id);
        return ResponseEntity.ok(new MessageResponse("Cập nhật thành công"));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary="Xóa mã giảm giá bằng id")
    public ResponseEntity<?> delete(@PathVariable long id){
        promotionService.deletePromotion(id);
        return ResponseEntity.ok(new MessageResponse("Xóa thành công"));
    }
}
