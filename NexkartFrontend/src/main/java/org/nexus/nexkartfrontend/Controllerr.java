package org.nexus.nexkartfrontend;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Controllerr {

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
