package org.nexus.nexkartbackend.product;


import org.nexus.nexkartbackend.FileUploadUtil;
import org.nexus.nexkartbackend.brand.BrandService;
import org.nexus.nexkartbackend.category.CategoryService;
import org.nexus.nexkartbackend.entity.Brand;
import org.nexus.nexkartbackend.entity.Product;
import org.nexus.nexkartbackend.exception.ProductNotFoundException;
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
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;
    
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/products")
    public String getProduct(Model model) {

        List<Product> listProducts = productService.listAll();

        model.addAttribute("listProducts", listProducts);

        return "products/products";

    }

    @GetMapping("/products/new")
    public String newProduct(Model model) {
        List<Brand> listBrands = brandService.listAll();

        Product product = new Product();
        product.setEnabled(true);
        product.setInStock(true);

        model.addAttribute("product", product);
        model.addAttribute("listBrands", listBrands);
        model.addAttribute("pageTitle", "Create New Product");


        return "products/product_form";
    }

//    @PostMapping("/products/save")
//    public String saveProduct(Product product, Model model , RedirectAttributes redirectAttributes) {
//
//        productService.save(product);
//        redirectAttributes.addFlashAttribute("message", "The Product has been Saved Successfully");
//        return "redirect:/products";
//
//    }

    @PostMapping("/products/save")
    public String saveProduct(Product product, Model model , RedirectAttributes redirectAttributes
            , @RequestParam("fileImage")MultipartFile mainImagemultipartFile
            , @RequestParam("extraImage") MultipartFile [] extraImageMultiparts) throws IOException {


            setMainImageName(mainImagemultipartFile ,product);
            setExtraImageNames(extraImageMultiparts,product);

            Product savedProduct = productService.save(product);

            saveUploadedImages(mainImagemultipartFile,extraImageMultiparts,savedProduct);



        redirectAttributes.addFlashAttribute("message", "The Product has been Saved Successfully");
        return "redirect:/products";

    }

    private void saveUploadedImages(MultipartFile mainImagemultipartFile, MultipartFile[] extraImageMultiparts, Product savedProduct) throws IOException {

        if(!mainImagemultipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(mainImagemultipartFile.getOriginalFilename());

            String uploadDir = "./product-images/" + savedProduct.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir,fileName,mainImagemultipartFile);

        }
        if(extraImageMultiparts.length > 0) {

            String uploadDir = "./product-images/" + savedProduct.getId() + "/extras";

            for (MultipartFile multipartFile : extraImageMultiparts) {
                if (!multipartFile.isEmpty()) {
                    continue;
                }
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);

            }

        }

    }

    private void setExtraImageNames(MultipartFile[] extraImageMultiparts, Product product) {
        if(extraImageMultiparts.length > 0) {
            for (MultipartFile multipartFile : extraImageMultiparts) {
                if(!multipartFile.isEmpty()) {
                    String fileName =  StringUtils.cleanPath(multipartFile.getOriginalFilename());
                    product.addExtraImage(fileName);
                }
            }
        }


    }

    public void setMainImageName(MultipartFile mainImagemultipartFile ,Product product) {

        if(!mainImagemultipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(mainImagemultipartFile.getOriginalFilename());
            product.setMainImage(fileName);
        }
    }

    @GetMapping("/products/{id}/enabled/{status}")
    public String updateProductEnabledStatus(
            @PathVariable("id") Integer id,
            @PathVariable("status") boolean enabled,
            RedirectAttributes redirectAttributes) {

        productService.updateProductEnabledStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The Product ID " + id + " has been " + status + ".";

        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable( name = "id") Integer id, RedirectAttributes redirectAttributes, Model model) {

        try {
            productService.delete(id);
            String productExtraImageDir = "./product-images/" + id + "/extras";
            String productImageDir = "./product-images/" + id + "/extras";
            FileUploadUtil.removeDir(productExtraImageDir);
            FileUploadUtil.removeDir(productImageDir);

            redirectAttributes.addFlashAttribute("message", "The Product ID " + id + " has been Deleted");
        }
        catch (ProductNotFoundException e) {
                redirectAttributes.addFlashAttribute("message", e.getMessage());

        }

        return "redirect:/products";
    }




}
