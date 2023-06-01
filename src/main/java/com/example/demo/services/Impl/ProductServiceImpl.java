package com.example.demo.services.Impl;

import com.example.demo.entities.Category;
import com.example.demo.entities.Image;
import com.example.demo.entities.Product;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.request.CreateProductRequest;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ImageRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
    public LocalDateTime now = LocalDateTime.now();

    @Override
    public List<Product> getList() {
        // TODO Auto-generated method stub
        List<Product> productList = productRepository.findAll();
        List<Product> list = new ArrayList<>();
        for(Product product: productList){
            if (product.getDateDeleted() == null) {
                list.add(product);
            }
        }
        return list;
    }

    @Override
    public Product getProduct(long id) {
        // TODO Auto-generated method stub
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Product With Id: " + id));
    }

    @Override
    public Product createProduct(CreateProductRequest request) {
        // TODO Auto-generated method stub
        Product product = new Product();
        product.setDateCreated(dtf.format(now));
        product.setEnabled(true);
        return getProduct(request, product);
    }

    private Product getProduct(CreateProductRequest request, Product product) {
        product.setProductname(request.getProductname());
        product.setDescription(request.getDescription());
        product.setOrigin(request.getOrigin());
        product.setUnit(request.getUnit());
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(()-> new NotFoundException("Not Found Category With Id: " + request.getCategoryId()));
        product.setCategory(category);
        product.setPrice(request.getPrice());
        if (product.getQuantity() <= 0) {
            product.setInventoryStatus("OUTOFSTOCK");
        } else if (product.getQuantity() < 10) {
            product.setInventoryStatus("LOWSTOCK");
        } else {
            product.setInventoryStatus("INSTOCK");
        }
        product.setRate(0);
        Set<Image> images = new HashSet<>();
        for(long imageId: request.getImageIds()){
            Image image = imageRepository.findById(imageId).orElseThrow(() -> new NotFoundException("Not Found Image With Id: " + imageId));
            images.add(image);
        }
        product.setImages(images);
        productRepository.save(product);
        return product;
    }

    @Override
    public Product updateProduct(long id, CreateProductRequest request) {
        // TODO Auto-generated method stub
        Product product= productRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Product With Id: " + id));
        return getProduct(request, product);
    }

    @Override
    public void enableProduct(long id) {
        // TODO Auto-generated method stub
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Promotion With Id: " + id));
        product.setEnabled(!product.isEnabled());
        productRepository.save(product);
    }

    @Override
    public List<Product> getListEnabled() {
        // TODO Auto-generated method stub
        return productRepository.findALLByEnabled();
    }

    @Override
    public void deleteProduct(long id) {
        // TODO Auto-generated method stub
        Product product= productRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Product With Id: " + id));
        product.setDateDeleted(dtf.format(now));
        product.setEnabled(false);
        product.getImages().remove(this);
        productRepository.save(product);
    }

    @Override
    public List<Product> getListNewst(int number) {
        // TODO Auto-generated method stub
        return productRepository.getListNewest(number);
    }

    @Override
    public List<Product> getListByPrice() {
        // TODO Auto-generated method stub
        return productRepository.getListByPrice();
    }

    @Override
    public List<Product> findRelatedProduct(long id){
        return productRepository.findRelatedProduct(id);
    }

    @Override
    public List<Product> findProductForUser(){
        return productRepository.findProduct();
    }

    @Override
    public List<Product> getListProductByCategory(long id){
        return productRepository.getListProductByCategory(id);
    }

    @Override
    public List<Product> getListByPriceRange(long id,int min, int max){
        if (id == 0) {
            return productRepository.getListProductByPriceRange(min, max);
        } else {
            return productRepository.getListProductByPriceRangeCategoryId(id, min, max);
        }
    }

    @Override
    public List<Product> searchProduct(String keyword) {
        return productRepository.searchProduct(keyword);
    }
}
