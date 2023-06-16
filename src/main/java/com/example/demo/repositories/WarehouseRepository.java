package com.example.demo.repositories;

import com.example.demo.entities.Product;
import com.example.demo.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    @Query(value = "Select * from Warehouse where type_warehouse = :type order by id desc",nativeQuery = true)
    List<Warehouse> getListType(String type);
}
