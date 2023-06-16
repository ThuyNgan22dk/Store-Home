package com.example.demo.controllers;

import com.example.demo.entities.Warehouse;
import com.example.demo.services.WarehouseServise;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
@CrossOrigin(origins = "*",maxAge = 3600)
public class WarehouseController {
    @Autowired
    private WarehouseServise warehouseServise;

    @GetMapping("/")
    @Operation(summary="Lấy danh sách")
    public ResponseEntity<?> getList(){
        List<Warehouse> warehouses = warehouseServise.getList();
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/{type}")
    @Operation(summary="Lấy danh sách")
    public ResponseEntity<?> getListType(@PathVariable String type){
        List<Warehouse> warehouses = warehouseServise.getListType(type);
        return ResponseEntity.ok(warehouses);
    }
}
