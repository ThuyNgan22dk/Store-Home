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
    @Query(value = "Select * from Product where productname = :name and enabled = true",nativeQuery = true)
    Optional<Product> findByProductname(String name);

    @Query(value = "Select * from Product where enabled = true order by id desc limit :number",nativeQuery = true)
    List<Product> getListNewest(int number);

    @Query(value = "Select * from Product where enabled = true order by rand()", nativeQuery = true)
    List<Product> getListRan();

    @Query(value = "Select * from Product where enabled = true order by price limit 8 ",nativeQuery = true)
    List<Product> getListByPrice();

    @Query(value ="Select * from Product where category_id = :categoryId and enabled = true order by rand() limit 4",nativeQuery = true)
    List<Product> findRelatedProduct(long categoryId);

//    @Query(value ="Select * from Product where category_id = :categoryId and enabled = true order by rand() limit :number",nativeQuery = true)
//    List<Product> getListProductByCategory(long categoryId, int number);
    @Query(value ="Select * from Product where category_id = :categoryId and enabled = true order by rand()",nativeQuery = true)
    List<Product> getProductByCategory(Long categoryId);

    @Query(value ="Select * from Product where enabled = true order by rand() limit 8",nativeQuery = true)
    List<Product> findProduct();

    @Query(value ="Select * from Product where category_id = :id and enabled = true",nativeQuery = true)
    List<Product> getListProductByCategory(long id);

    @Query(value = "Select * from Product where enabled = true and price between :min and :max",nativeQuery = true)
    List<Product> getListProductByPriceRange(int min,int max);

    @Query(value = "Select * from Product where category_id = :id and enabled = true and price between :min and :max",nativeQuery = true)
    List<Product> getListProductByPriceRangeCategoryId(long id,int min,int max);

    @Query(value= "Select * from Product p where p.enabled = true and p.productname like %:keyword% order by id desc", nativeQuery = true)
    List<Product> searchProduct(String keyword);

    @Query(value = "Select * from Product where enabled = true order by price asc", nativeQuery = true)
    List<Product> getSortUpAsc();

    @Query(value = "Select * from Product where enabled = true order by price desc", nativeQuery = true)
    List<Product> getSortDesc();

    @Query(value = "Select * from Product p where p.enabled = true",nativeQuery = true)
    List<Product> findALLByEnabled();
}
