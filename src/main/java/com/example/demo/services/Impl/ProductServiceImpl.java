package com.example.demo.services.Impl;

import com.example.demo.entities.*;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.request.CreateProductRequest;
import com.example.demo.repositories.*;
import com.example.demo.services.ProductService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CartRepository cartRepository;

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
    public List<Product> getListSuggestProduct(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Not Found Product With username: " + username));
        List<Cart> carts = cartRepository.getCartByUser(user.getId());
        List<Product> productList = new ArrayList<>();
        List<String> categoryName = new ArrayList<>();
        boolean check = false;
        int count = 1;
        List<Integer> listCountCate = new ArrayList<>();
        if (carts.size() == 0){
            productList = getListRan();
        } else {
            if (carts.size() == 1) {
                Cart cart = carts.get(0);
                categoryName.add(cart.getProduct().getCategory().getName());
            }
            if (carts.size() > 1){
                listCountCate.add(1);
                categoryName.add(carts.get(1).getProduct().getCategory().getName());
                for (Cart cart: carts) {
                    check = false;
                    for (int j = 0; j < count; j++) {
                        if (categoryName.get(j).equals(cart.getProduct().getCategory().getName())) {
                            check = true;
                            listCountCate.set(j,listCountCate.get(j) + 1);
                            break;
                        }
                    }
                    if (!check) {
                        listCountCate.add(1);
                        categoryName.add(cart.getProduct().getCategory().getName());
                        count++;
                    }
                }
                String temp;    Integer temp1;
                for (int i = 0 ; i < listCountCate.size() - 1; i++) {
                    for (int j = i + 1; j < listCountCate.size(); j++) {
                        if (listCountCate.get(i) < listCountCate.get(j)) {
                            temp1 = listCountCate.get(j);
                            listCountCate.set(j,listCountCate.get(i));
                            listCountCate.set(i, temp1);
                            temp = categoryName.get(j);
                            categoryName.set(j,categoryName.get(i));
                            categoryName.set(i, temp);
                        }
                    }
                }
            }

            List<Category> categoryList = categoryRepository.findAll();
            for (Category category: categoryList){
                check = false;
                for (int j = 0; j < count; j++) {
                    if (categoryName.get(j).equals(category.getName())) {
                        check = true;
                        break;
                    }
                }
                if (!check) {
                    listCountCate.add(1);
                    categoryName.add(category.getName());
                    count++;
                }
            }

            for (int i = 0; i < listCountCate.size(); i++){
                Category category = categoryRepository.findCategoryByName(categoryName.get(i));
                List<Product> products = productRepository.getProductByCategory(category.getId());
                productList.addAll(products);
            }
        }
        return productList;
    }

    @Override
    public List<Product> findProductForUser(){
        return productRepository.findProduct();
    }

    @Override
    public List<Product> getListRan(){
        return productRepository.getListRan();
    }

    @Override
    public Product getProduct(long id) {
        // TODO Auto-generated method stub
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Product With Id: " + id));
    }

    @Override
    public List<Product> getListSortUpAsc(){
        return productRepository.getSortUpAsc();
    }

    @Override
    public List<Product> getListSortDesc(){
        return productRepository.getSortDesc();
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
            product.setInventoryStatus("Hết hàng");
        } else if (product.getQuantity() < 10) {
            product.setInventoryStatus("Còn ít");
        } else {
            product.setInventoryStatus("Sẵn có");
        }
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
