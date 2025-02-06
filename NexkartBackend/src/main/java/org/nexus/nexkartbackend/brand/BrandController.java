package org.nexus.nexkartbackend.brand;


import org.nexus.nexkartbackend.FileUploadUtil;
import org.nexus.nexkartbackend.category.CategoryService;
import org.nexus.nexkartbackend.entity.Brand;
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
//@RequestMapping("/test")
public class BrandController {

        @Autowired
        BrandService brandService;

        @Autowired
        CategoryService categoryService;



    @GetMapping("/brands")
    public String listAll(Model model) {
        List<Brand> listBrands = brandService.listAll();
        model.addAttribute("listBrands", listBrands);

        return "brands/brands";

    }

    @GetMapping("/brands/new")
    public String newBrand(Model model) {

        List<Category> listCategories = categoryService.listCategoriesUsedInForm();

        model.addAttribute("listCategories", listCategories);
        model.addAttribute("brand", new Brand());
        model.addAttribute("pageTitle", "Create New Brand");

        return "brands/brand_form";
    }

    @PostMapping("/brands/save")
    public String saveBrand(Brand brand , @RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes redirectAttributes ) throws IOException {

        if(!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            brand.setLogo(fileName);

            Brand savedBrand = brandService.save(brand);
            String uploadDir = "./brands-logo/" + savedBrand.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
        }
        else {
            brandService.save(brand);
        }
        redirectAttributes.addFlashAttribute("message", "Brand created successfully");
        return "redirect:/brands";
    }

    @GetMapping("/brands/edit/{id}")
    public String editBrand(@PathVariable(name = "id") Integer id, Model model , RedirectAttributes redirectAttributes) {

        try {
            Brand brand = brandService.get(id);
            List<Category> listCategories = categoryService.listCategoriesUsedInForm();

            model.addAttribute("brand", brand);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("pageTitle", "Edit Brand (ID: " + id + ")");

            return "brands/brand_form";
        }
        catch (BrandNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/brands";
        }

    }
    @GetMapping("/brands/delete/{id}")
    public String deleteBrand(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes, Model model) {
            try {

                brandService.delete(id);
                String brandDir = "./brands-logo/" + id;
                FileUploadUtil.removeDir(brandDir);

                redirectAttributes.addFlashAttribute("message", "The brand " + id + " has been deleted successfully");
            } catch (BrandNotFoundException e) {
                redirectAttributes.addFlashAttribute("message", e.getMessage());
            }

            return "redirect:/brands";

    }




}


