package com.example.demo.services;

import com.example.demo.entities.Warehouse;
import com.example.demo.model.request.ChangeWarehouseRequest;

import java.util.List;

public interface WarehouseServise {
    List<Warehouse> getList();

    void revenueStatistics();

    boolean addOrder(ChangeWarehouseRequest request);

    void addImport(ChangeWarehouseRequest request);
}
