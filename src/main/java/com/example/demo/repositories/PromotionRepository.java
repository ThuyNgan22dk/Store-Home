package com.example.demo.repositories;
import java.util.List;

import com.example.demo.entities.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion,Long> {
//    @Query(value = "Select * from Promotion where code = :code",nativeQuery = true)
//    Promotion findByCode(String code);

    @Query("Select c from Promotion c where c.enabled = true")
    List<Promotion> findALLByEnabled();
}
