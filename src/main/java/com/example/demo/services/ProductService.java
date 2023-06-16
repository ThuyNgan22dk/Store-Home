package com.example.demo.services;
import java.util.List;

import com.example.demo.entities.Product;
import com.example.demo.model.request.CreateProductRequest;

public interface ProductService {
    List<Product> getList();

    List<Product> getListNewst(int number);

    List<Product> getListByPrice();

    List<Product> findRelatedProduct(long id);
    List<Product> findProductForUser();

    List<Product> getListProductByCategory(long id);

    List<Product> getListByPriceRange(long id,int min, int max);

    List<Product> searchProduct(String keyword);
    List<Product> getListSortUpAsc();
    List<Product> getListSortDesc();
    List<Product> getListSuggestProduct(String username);

    Product getProduct(long id);
    List<Product> getListRan();

    Product createProduct(CreateProductRequest request);

    void enableProduct(long id);

    List<Product> getListEnabled();

    Product updateProduct(long id, CreateProductRequest request);

    void deleteProduct(long id);

}
