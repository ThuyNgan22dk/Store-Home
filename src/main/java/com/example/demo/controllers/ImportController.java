package com.example.demo.controllers;

import com.example.demo.entities.ImportDetail;
import com.example.demo.entities.ImportGoods;
import com.example.demo.model.request.CreateImportDetailRequest;
import com.example.demo.model.request.CreateImportGoodRequest;
import com.example.demo.model.response.MessageResponse;
import com.example.demo.services.ImportService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/import")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ImportController {
    @Autowired
    private ImportService importService;

    @GetMapping("/")
    @Operation(summary="Lấy ra danh sách mua hàng")
    public ResponseEntity<List<ImportGoods>> getList(){
        List<ImportGoods> list = importService.getList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/importDetail")
    public ResponseEntity<List<ImportDetail>> getListDetailNull(){
        return null;
    }

    @GetMapping("/importDetail/{ig_id}")
    @Operation(summary="Lấy ra danh sách chi tiết các sản phẩm trong đơn mua hàng")
    public ResponseEntity<List<ImportDetail>> getListDetail(@PathVariable Long ig_id){
        List<ImportDetail> list = importService.getListDetail(ig_id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/totalImport")
    public ResponseEntity<?> getTotalImport(){
        long totalImport = importService.totalAllImport();
        return ResponseEntity.ok(totalImport);
    }

    @GetMapping("/totalDay/{day}")
    public ResponseEntity<?> getTotalDayImport(@PathVariable String day){
        long totalDayImport = importService.getImportForChart(day);
        return ResponseEntity.ok(totalDayImport);
    }

    @PostMapping("/importDetail/create")
    @Operation(summary="Tạo mới sản phẩm trong danh sách đơn mua hàng")
    public ResponseEntity<?> createProductImport(@RequestBody CreateImportDetailRequest request){
        importService.createProductImport(request);
        return ResponseEntity.ok(new MessageResponse("Add Product Successfully!"));
    }

    @PutMapping("importDetail/update/{id}")
    @Operation(summary="Tìm sản phẩm trong đơn hàng bằng id và cập nhật sản phẩm đó")
    public ResponseEntity<ImportDetail> updateProductImport(@PathVariable Long id, @RequestBody CreateImportDetailRequest request){
        ImportDetail importDetail = importService.updateImportDetail(id, request);
        return ResponseEntity.ok(importDetail);
    }

    @PostMapping("/create")
    @Operation(summary="Mua sản phẩm")
    public ResponseEntity<?> placeImportGood(@RequestBody CreateImportGoodRequest request){
        importService.placeImport(request);
        return ResponseEntity.ok(new MessageResponse("Import Placed Successfully!"));
    }

    @PutMapping("/update/{id}")
    @Operation(summary="Tìm đơn mua hàng và cập nhật đơn mua hàng đó")
    public ResponseEntity<ImportGoods> updateImportGood(@PathVariable Long id, @RequestBody CreateImportGoodRequest request){
        ImportGoods importGoods = importService.updateImport(id, request);
        return ResponseEntity.ok(importGoods);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary="Xóa sản phẩm bằng id")
    public ResponseEntity<?> deleteImportGood(@PathVariable Long id){
        importService.deleteImport(id);
        return ResponseEntity.ok(new MessageResponse("Xóa đơn mua hàng thành công"));
    }

    @DeleteMapping("importDetail/delete/{id}")
    @Operation(summary="Xóa sản phẩm bằng id")
    public ResponseEntity<?> deleteImportDetail(@PathVariable Long id){
        importService.deteleProductImport(id);
        return ResponseEntity.ok(new MessageResponse("Xóa sản phảm thành công"));
    }
}
