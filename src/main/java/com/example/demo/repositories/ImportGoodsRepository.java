package com.example.demo.repositories;
import com.example.demo.entities.ImportGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportGoodsRepository extends JpaRepository<ImportGoods, Long> {
    @Query(value ="Select * from import_goods where date_time = :dateTime",nativeQuery = true)
    List<ImportGoods> getImportDay(String dateTime);
}
