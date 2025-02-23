package org.nexus.nexkartbackend.order;

import org.nexus.nexkartbackend.paging.PagingAndSortingHelper;
import org.nexus.nexkartbackend.paging.PagingAndSortingParam;
import org.nexus.nexkartbackend.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ProductSearchController {

    @Autowired
    private ProductService productService;

    @GetMapping("/orders/search_product")
    public String showSearchProductPage() {
        return "orders/search_product";
    }

    @PostMapping("/orders/search_product")
    public String searchProducts(@RequestParam("keyword") String keyword) {
        return "redirect:/orders/search_product/page/1?keyword=" + keyword;
    }

    @GetMapping("/orders/search_product/page/{pageNum}")
    public String searchProductsByPage(@PathVariable(name = "pageNum") int pageNum,
                                       @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
                                       Model model) {
        productService.searchProducts(pageNum, keyword, model);
        return "orders/search_product";
    }

}
