package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM image WHERE uploaded_by = :userId order by id desc")
    public List<Image> getListImageOfUser(long userId);
}

