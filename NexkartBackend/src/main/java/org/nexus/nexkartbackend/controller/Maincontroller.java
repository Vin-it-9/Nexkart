package org.nexus.nexkartbackend.controller;

import org.nexus.nexkartbackend.Repository.UserRepository;
import org.nexus.nexkartbackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class Maincontroller {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String viewHomePage() {
        return "index" ;
    }

    @GetMapping("/login")
    public String viewLoginPage() {

//        if (!userRepository.existsByEmail("admin@gmail.com")) {
//
//            User admin = new User();
//            admin.setFirstName("vinit");
//            admin.setLastName("shinde");
//            admin.setEmail("admin@gmail.com");
//            admin.setPassword(passwordEncoder.encode("admin"));
//
//            userRepository.save(admin);
//
//            System.out.println("Admin user created successfully.");
//        }

        return "login" ;
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

}
