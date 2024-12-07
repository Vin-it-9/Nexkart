package org.nexus.nexkartbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class maincontroller {

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
