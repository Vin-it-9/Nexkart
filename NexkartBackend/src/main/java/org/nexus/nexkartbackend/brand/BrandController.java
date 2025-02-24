package org.nexus.nexkartbackend.brand;


import org.nexus.nexkartbackend.FileUploadUtil;
import org.nexus.nexkartbackend.aws.AmazonS3Util;
import org.nexus.nexkartbackend.category.CategoryService;
import org.nexus.nexkartbackend.entity.Brand;
import org.nexus.nexkartbackend.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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


//    @GetMapping("/brands")
//    public String listAll(Model model) {
//        List<Brand> listBrands = brandService.listAll();
//        model.addAttribute("listBrands", listBrands);
//
//        return "brands/brands";
//
//    }

    @GetMapping("/brands")
    public String listAll(Model model) {
        return listByPage(1,model,null);
    }



    @GetMapping("brands/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model , @Param("keyword") String keyword) {

        Page<Brand> page = brandService.listByPage(pageNum, keyword);
        List<Brand> listBrands = page.getContent();

        long startCount = (pageNum - 1) * BrandService.BRANDS_PER_PAGE + 1;

        long endCount = startCount + BrandService.BRANDS_PER_PAGE - 1;

        if(endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("keyword" , keyword);
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

            String uploadDir = "brand-logos/" + savedBrand.getId();

            AmazonS3Util.removeFolder(uploadDir);
            AmazonS3Util.uploadFile(uploadDir, fileName, multipartFile.getInputStream());

//            String uploadDir = "./brands-logo/" + savedBrand.getId();
//
//            FileUploadUtil.cleanDir(uploadDir);
//            FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);

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
                String brandDir = "brand-logos/" + id;
                AmazonS3Util.removeFolder(brandDir);
//                String brandDir = "./brands-logo/" + id;
//                FileUploadUtil.removeDir(brandDir);

                redirectAttributes.addFlashAttribute("message", "The brand " + id + " has been deleted successfully");
            } catch (BrandNotFoundException e) {
                redirectAttributes.addFlashAttribute("message", e.getMessage());
            }

            return "redirect:/brands";

    }




}


