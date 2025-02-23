package org.nexus.nexkartbackend.product;

import org.nexus.nexkartbackend.Repository.ProductRepository;
import org.nexus.nexkartbackend.entity.Product;
import org.nexus.nexkartbackend.exception.ProductNotFoundException;
import org.nexus.nexkartbackend.paging.PagingAndSortingHelper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ProductService {


    public static final int PRODUCTS_PER_PAGE = 4;


    @Autowired
    private ProductRepository productRepository;

    public List<Product> listAll() {
        return (List<Product>) productRepository.findAll();
    }

    public void updateProductEnabledStatus(Integer id,boolean enabled) {
        productRepository.updateEnabledStatus(id, enabled);
    }


    public void searchProducts(int pageNum, String keyword, Model model) {
        Pageable pageable = createPageable(PRODUCTS_PER_PAGE, pageNum);
        Page<Product> page = productRepository.searchProductsByName(keyword, pageable);
        updateModelAttributes(pageNum, page, keyword, model);
    }

    private Pageable createPageable(int pageSize, int pageNum) {
        return PageRequest.of(pageNum - 1, pageSize);
    }

    private void updateModelAttributes(int pageNum, Page<Product> page, String keyword, Model model) {
        model.addAttribute("listProducts", page.getContent());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("keyword", keyword);
    }

    public Product save(Product product) {

        if(product.getId() == null) {
            product.setCreatedTime(new Date());
        }

        if(product.getAlias() == null || product.getAlias().isEmpty()) {
            String defaultAlias = product.getName().replaceAll(" ","-");
            product.setAlias(defaultAlias);
        }
        else {
            product.setAlias(product.getAlias().replaceAll(" ","-"));
        }

        product.setUpdatedTime(new Date());

        return productRepository.save(product);

    }



//    public void listByPage(int pageNum, PagingAndSortingHelper helper, Integer categoryId) {
//        Pageable pageable = helper.createPageable(PRODUCTS_PER_PAGE, pageNum);
//        String keyword = helper.getKeyword();
//        Page<Product> page = null;
//
//        if (keyword != null && !keyword.isEmpty()) {
//            if (categoryId != null && categoryId > 0) {
//                String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
//                page = productRepository.searchInCategory(categoryId, categoryIdMatch, keyword, pageable);
//            } else {
//                page = productRepository.findAll(keyword, pageable);
//            }
//        } else {
//            if (categoryId != null && categoryId > 0) {
//                String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
//                page = productRepository.findAllInCategory(categoryId, categoryIdMatch, pageable);
//            } else {
//                page = productRepository.findAll(pageable);
//            }
//        }
//
//        helper.updateModelAttributes(pageNum, page);
//
//    }




    public String checkUnique(Integer id, String name) {
        boolean isCreatingNew = (id == null || id == 0);
        Product productByName = productRepository.findByName(name);

        if (isCreatingNew) {
            if (productByName != null) return "Duplicate";
        } else {
            if (productByName != null && productByName.getId() != id) {
                return "Duplicate";
            }
        }

        return "OK";
    }

//    public void searchProducts(int pageNum, PagingAndSortingHelper helper) {
//        Pageable pageable = helper.createPageable(PRODUCTS_PER_PAGE, pageNum);
//        String keyword = helper.getKeyword();
//        Page<Product> page = productRepository.searchProductsByName(keyword, pageable);
//        helper.updateModelAttributes(pageNum, page);
//    }

//
//    public Product save(Product product) {
//        if (product.getId() == null) {
//            product.setCreatedTime(new Date());
//        }
//
//        if (product.getAlias() == null || product.getAlias().isEmpty()) {
//            String defaultAlias = product.getName().replaceAll(" ", "-");
//            product.setAlias(defaultAlias);
//        } else {
//            product.setAlias(product.getAlias().replaceAll(" ", "-"));
//        }
//
//        product.setUpdatedTime(new Date());
//
//        Product updatedProduct = productRepository.save(product);
//        productRepository.updateReviewCountAndAverageRating(updatedProduct.getId());
//
//        return updatedProduct;
//    }

    public void saveProductPrice(Product productInForm) {
        Product productInDB = productRepository.findById(productInForm.getId()).get();
        productInDB.setCost(productInForm.getCost());
        productInDB.setPrice(productInForm.getPrice());
        productInDB.setDiscountPercent(productInForm.getDiscountPercent());

        productRepository.save(productInDB);
    }


    public void delete(Integer id) throws ProductNotFoundException {
        Long countById = productRepository.countById(id);

        if (countById == null || countById == 0) {
            throw new ProductNotFoundException("Could not find any product with ID " + id);
        }

        productRepository.deleteById(id);
    }

    public Product get(Integer id) throws ProductNotFoundException {
        try {
            return productRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new ProductNotFoundException("Could not find any product with ID " + id);
        }
    }

//    public Page<Product> listByPage(int pageNum, String keyword) {
//
//        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
//
//        if (keyword != null) {
//            return productRepository.findAll(keyword, pageable);
//        }
//
//        return productRepository.findAll(pageable);
//    }

    public Page<Product> listByPage(int pageNum,
                                    String keyword, Integer categoryId) {


        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);

        if (keyword != null && !keyword.isEmpty()) {
            if (categoryId != null && categoryId > 0) {
                String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
                return productRepository.searchInCategory(categoryId, categoryIdMatch, keyword, pageable);
            }

            return productRepository.findAll(keyword, pageable);
        }

        if (categoryId != null && categoryId > 0) {
            String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
            return productRepository.findAllInCategory(categoryId, categoryIdMatch, pageable);
        }

        return productRepository.findAll(pageable);
    }



}
