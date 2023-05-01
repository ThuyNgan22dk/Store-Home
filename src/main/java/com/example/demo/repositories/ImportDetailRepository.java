package com.example.demo.repositories;

import com.example.demo.entities.ImportDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportDetailRepository extends JpaRepository<ImportDetail, Long> {
    @Query(value = "Select * from import_detail where import_goods_id = :ig_id",nativeQuery = true)
    List<ImportDetail> getListDetailById (long ig_id);
}
