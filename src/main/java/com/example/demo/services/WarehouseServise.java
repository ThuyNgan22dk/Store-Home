package com.example.demo.services;

import com.example.demo.entities.Warehouse;
import com.example.demo.model.request.ChangeWarehouseRequest;

import java.util.List;

public interface WarehouseServise {
    List<Warehouse> getList();

    List<Warehouse> getListType(String type);

    boolean addOrder(ChangeWarehouseRequest request);
//    void deleteWasehouse(long id);

    void addImport(ChangeWarehouseRequest request);
}
