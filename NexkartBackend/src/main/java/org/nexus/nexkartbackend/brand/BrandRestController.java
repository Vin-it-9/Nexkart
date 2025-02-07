package org.nexus.nexkartbackend.brand;


import org.nexus.nexkartbackend.entity.Brand;
import org.nexus.nexkartbackend.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class BrandRestController
{

    @Autowired
    private BrandService brandService;

    @GetMapping("/brands/{id}/categories")
    public List<CategoryDTO> listCatgoriesByBrand(@PathVariable(name = "id") Integer brandId) throws BrandNotFoundRestException {

        List<CategoryDTO> listCategories = new ArrayList<>();

        try {
            Brand brand = brandService.get(brandId);
            Set<Category> categories = brand.getCategories();

            for (Category category : categories) {
                CategoryDTO categoryDTO = new CategoryDTO(category.getId() , category.getName());
                listCategories.add(categoryDTO);
            }

        return listCategories;

        } catch (BrandNotFoundException e) {
            throw new BrandNotFoundRestException();
        }

    }



}
