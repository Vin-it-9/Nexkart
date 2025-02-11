package org.nexus.nexkartfrontend;


import org.nexus.nexkartfrontend.common.category.Category;
import org.nexus.nexkartfrontend.common.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String viewHomePage(Model model) {

        List<Category> listCategories = categoryService.listNoChildrenCategories();
        model.addAttribute("listCategories", listCategories);

        return "index";
    }

    @GetMapping("/categories")
    public String viewCategories(Model model) {
        List<Category> listCategories = categoryService.listNoChildrenCategories();
        model.addAttribute("listCategories", listCategories);
        return "categories";
    }



    @GetMapping("/login")
    public String login() {
        return "login";
    }


}
