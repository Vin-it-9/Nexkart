package org.nexus.nexkartbackend.product;


import jakarta.servlet.ServletRequest;
import org.nexus.nexkartbackend.brand.BrandService;
import org.nexus.nexkartbackend.category.CategoryService;
import org.nexus.nexkartbackend.entity.Brand;
import org.nexus.nexkartbackend.entity.Product;
import org.nexus.nexkartbackend.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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

    @PostMapping("/products/save")
    public String saveProduct(Product product, Model model , RedirectAttributes redirectAttributes) {

        productService.save(product);
        redirectAttributes.addFlashAttribute("message", "The Product has been Saved Successfully");
        return "redirect:/products";

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
            redirectAttributes.addFlashAttribute("message", "The Product ID " + id + " has been Deleted");
        }
        catch (ProductNotFoundException e) {
                redirectAttributes.addFlashAttribute("message", e.getMessage());

        }

        return "redirect:/products";
    }




}
