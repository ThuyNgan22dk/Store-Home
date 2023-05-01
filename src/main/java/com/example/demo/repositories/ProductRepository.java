package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query(value = "Select * from Product where productname = :name",nativeQuery = true)
    Optional<Product> findByProductname(String name);

    @Query(value = "Select * from Product order by id desc limit :number",nativeQuery = true)
    List<Product> getListNewest(int number);

    @Query(value = "Select * from Product order by price limit 8 ",nativeQuery = true)
    List<Product> getListByPrice();

    @Query(value ="Select * from Product where category_id = :id order by rand() limit 4",nativeQuery = true)
    List<Product> findRelatedProduct(long id);

    @Query(value ="Select * from Product where category_id = :id",nativeQuery = true)
    List<Product> getListProductByCategory(long id);

    @Query(value = "Select * from Product where category_id = :id and price between :min and :max",nativeQuery = true)
    List<Product> getListProductByPriceRange(long id,int min,int max);

    @Query(value= "Select p from Product p where p.productname like %:keyword% order by id desc")
    List<Product> searchProduct(String keyword);

    @Query(value = "Select p from Product p where c.enabled = true",nativeQuery = true)
    List<Product> findALLByEnabled();

}
