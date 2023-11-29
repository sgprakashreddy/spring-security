package com.javatechie.springsecurity.service;

import com.javatechie.springsecurity.dto.Product;
import com.javatechie.springsecurity.entity.UserInfo;
import com.javatechie.springsecurity.repository.UserInfoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductService {

    List<Product> productList=null;

    @Autowired
    private UserInfoRepository userInfoRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void loadProductsFromDB(){
        productList= IntStream.rangeClosed(1,10)
                .mapToObj(value -> Product.builder()
                        .productId(value)
                        .name("Product"+value)
                        .qty(new Random().nextInt(10))
                        .price(new Random().nextInt(5000)).build()).collect(Collectors.toList());
    }

    public List<Product> getAllProducts(){
        return productList;
    }

    public Product getProductById(int id){
        return productList.stream()
                .filter(product -> product.getProductId()==id)
                .findAny()
                .orElseThrow(() -> new RuntimeException("Product " +id+ " not found in existing products"));
    }

    public String addUser(UserInfo userInfo){
        System.out.println("************************");
        System.out.println("userInfo = " + userInfo);
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRespository.save(userInfo);
        System.out.println(" Product Service userInfo = " + userInfo);
        return "User Added to System successfully";
    }

}
