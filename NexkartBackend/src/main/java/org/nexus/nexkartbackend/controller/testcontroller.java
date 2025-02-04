package org.nexus.nexkartbackend.controller;

import org.nexus.nexkartbackend.Repository.CategoryRepository;
import org.nexus.nexkartbackend.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class testcontroller {

    @Autowired
    CategoryRepository CategoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;



}
