package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("Select c from Category c where c.enable = true")
    List<Category> findALLByEnabled();

    @Query(value = "Select * from Category where name = :name and enable = true", nativeQuery = true)
    Category findCategoryByName(String name);
}
