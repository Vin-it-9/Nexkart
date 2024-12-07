package org.nexus.nexkartbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class Maincontroller {

    @GetMapping("")
    public String viewHomePage() {
        return "index" ;
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        return "login" ;
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

}
