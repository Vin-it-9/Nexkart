package org.nexus.nexkartbackend.controller;

import org.nexus.nexkartbackend.Repository.CategoryRepository;
import org.nexus.nexkartbackend.Repository.ProductRepository;
import org.nexus.nexkartbackend.entity.Product;
import org.nexus.nexkartbackend.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class testcontroller {

    @Autowired
    CategoryRepository CategoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;




}
