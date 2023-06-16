package com.example.demo.services.Impl;

import com.example.demo.entities.Product;
import com.example.demo.entities.Warehouse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.request.ChangeWarehouseRequest;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.WarehouseRepository;
import com.example.demo.services.ImportService;
import com.example.demo.services.OrderService;
import com.example.demo.services.WarehouseServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class WarehouseServiceImpl implements WarehouseServise {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public List<Warehouse> getList() {
        return warehouseRepository.findAll(Sort.by("id").descending());
    }

    @Override
    public List<Warehouse> getListType(String type){
        return warehouseRepository.getListType(type);
    }

    @Override
    public boolean addOrder(ChangeWarehouseRequest request) {
        Warehouse warehouse = new Warehouse();
        Product product = productRepository.findByProductname(request.getProductname()).orElseThrow(() -> new NotFoundException("Not Found Category With Id: " + request.getProductname()));
        if (product.getQuantity() >= request.getQuantity()) {
            product.setQuantity(product.getQuantity() - request.getQuantity());
            if (product.getQuantity() <= 0) {
                product.setInventoryStatus("Hết hàng");
            } else if (product.getQuantity() < 10) {
                product.setInventoryStatus("Còn ít");
            } else {
                product.setInventoryStatus("Sẵn có");
            }
            warehouse.setProduct(product);
            warehouse.setTypeWarehouse("order");
            warehouse.setExpiry(request.getExpiry());
            warehouse.setQuantity(request.getQuantity());
            warehouseRepository.save(warehouse);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addImport(ChangeWarehouseRequest request) {
        Warehouse warehouse = new Warehouse();
        Product product = productRepository.findByProductname(request.getProductname()).orElseThrow(() -> new NotFoundException("Not Found Product With Id: " + request.getProductname()));
        product.setQuantity(product.getQuantity() + request.getQuantity());
        warehouse.setProduct(product);
        warehouse.setExpiry(request.getExpiry());
        warehouse.setQuantity(request.getQuantity());
        warehouse.setTypeWarehouse("import");
        warehouseRepository.save(warehouse);
    }
}
