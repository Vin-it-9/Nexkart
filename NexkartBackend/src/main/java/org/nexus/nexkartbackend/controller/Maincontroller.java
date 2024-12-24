package org.nexus.nexkartbackend.controller;

import org.nexus.nexkartbackend.Repository.RoleRepository;
import org.nexus.nexkartbackend.Repository.UserRepository;
import org.nexus.nexkartbackend.entity.Role;
import org.nexus.nexkartbackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class Maincontroller {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("")
    public String viewHomePage() {
        return "index" ;
    }


    @GetMapping("/login")
    public String viewLoginPage() {

        if (!userRepository.existsByEmail("admin@gmail.com")) {
            testCreateRestRoles();
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            User adminUser = new User("admin@gmail.com", encoder.encode("admin"), "admin", "shinde");
            Role roleAdmin = roleRepository.findByName("Admin")
                    .orElseThrow(() -> new RuntimeException("Admin role not found!"));
            adminUser.addRole(roleAdmin);
            adminUser.setEnabled(true);
            userRepository.save(adminUser);
        }

        return "login";
    }

    public void testCreateRestRoles() {

        List<Role> roles = List.of(
                new Role("Admin", "manage everything"),
                new Role("Salesperson", "manage product price, customers, shipping, orders and sales report"),
                new Role("Editor", "manage categories, brands, products, articles and menus"),
                new Role("Shipper", "view products, view orders and update order status"),
                new Role("Assistant", "manage questions and reviews")
        );

        roles.forEach(role -> {
            if (!roleRepository.existsByName(role.getName())) {
                roleRepository.save(role);
            }
        });
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

}
