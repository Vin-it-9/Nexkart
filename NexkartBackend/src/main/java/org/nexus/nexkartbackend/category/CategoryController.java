package org.nexus.nexkartbackend.category;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nexus.nexkartbackend.FileUploadUtil;
import org.nexus.nexkartbackend.entity.Category;
import org.nexus.nexkartbackend.exception.CategoryNotFoundException;
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


    @GetMapping("/categories/new")
    public String newCategory(Model model) {

        model.addAttribute("category", new Category());
        model.addAttribute("listCategories", categoryService.listCategories());
        model.addAttribute("pageTitle", "Create New Category");

        return "categories/category_form";
    }

//    @PostMapping("/categories/save")
//    public String saveCategory(Category category , @RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes redirectAttributes ) {
//
//
//        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        category.setImage(fileName);
//
//        Category savedcategory = categoryService.save(category);
//        String uploadDir = "./category-images/" + savedcategory.getId();
//        try {
//            FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        redirectAttributes.addFlashAttribute("message", "Category saved successfully");
//
//        return "redirect:/categories";
//
//    }

    @PostMapping("/categories/save")
    public String saveCategory(Category category,
                               @RequestParam("fileImage") MultipartFile multipartFile,
                               RedirectAttributes ra) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            category.setImage(fileName);

            Category savedCategory = categoryService.save(category);
            String uploadDir = "./category-images/" + savedCategory.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            categoryService.save(category);
        }

        ra.addFlashAttribute("message", "The category has been saved successfully.");
        return "redirect:/categories";
    }


    @GetMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable(name = "id") Integer id, Model model,
                               RedirectAttributes ra) {
        try {

            Category category = categoryService.get(id);
            List<Category> listCategories = categoryService.listCategoriesUsedInForm();

            model.addAttribute("category", category);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("pageTitle", "Edit Category (ID: " + id + ")");

            return "categories/category_form";
        } catch (CategoryNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
            return "redirect:/categories";
        }
    }



    @GetMapping("/categories/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<Category> listCategories = categoryService.listCategoriesUsedInForm();
        CategoryCsvExporter exporter = new CategoryCsvExporter();
        exporter.export(listCategories, response);
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable(name = "id") Integer id,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        try {
            categoryService.delete(id);
            String categoryDir = "category-images/" + id;
            FileUploadUtil.removeDir(categoryDir);

            redirectAttributes.addFlashAttribute("message",
                    "The category ID " + id + " has been deleted successfully");
        } catch (CategoryNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/categories";
    }






}
