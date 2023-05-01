package com.example.demo.repositories;
import com.example.demo.entities.ImportGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportGoodsRepository extends JpaRepository<ImportGoods, Long> {
}
