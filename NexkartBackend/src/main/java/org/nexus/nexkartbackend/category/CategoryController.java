package org.nexus.nexkartbackend.category;

import jakarta.servlet.ServletRequest;
import org.nexus.nexkartbackend.FileUploadUtil;
import org.nexus.nexkartbackend.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public void deleteCategory(@PathVariable("id") Integer id) {
        categoryService.deleteCategoryById(id);
    }

    @GetMapping("/categories/new")
    public String newCategory(Model model) {

        model.addAttribute("category", new Category());
        model.addAttribute("listCategories", categoryService.listCategories());
        model.addAttribute("pageTitle", "Create New Category");

        return "categories/category_form";
    }

    @PostMapping("/categories/save")
    public String saveCategory(Category category , @RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes redirectAttributes) {


        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        category.setImage(fileName);

        Category savedcategory = categoryService.save(category);
        String uploadDir = "./category-images/" + savedcategory.getId();
        try {
            FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        redirectAttributes.addFlashAttribute("message", "Category saved successfully");

        return "redirect:/categories";

    }







}
