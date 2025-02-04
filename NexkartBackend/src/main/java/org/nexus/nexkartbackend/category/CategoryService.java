package org.nexus.nexkartbackend.category;

import org.nexus.nexkartbackend.Repository.CategoryRepository;
import org.nexus.nexkartbackend.entity.Category;
import org.nexus.nexkartbackend.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    public Category save(Category category) {
        return repo.save(category);
    }

    public List<Category> getAllCategories() {
        return (List<Category>) repo.findAll();
    }

    public void deleteCategoryById(Integer id) {
        repo.deleteById(id);
    }

    public List<Category> listCategories() {
        return (List<Category>) repo.findAll();
    }



    public List<Category> listCategoriesUsedInForm() {

        List<Category> categoriesUsedInForm = new ArrayList<>();

        Iterable<Category> categoriesInDB = repo.findAll();

        for (Category category : categoriesInDB) {

            if (category.getParent() == null) {
                categoriesUsedInForm.add(new Category(category.getName()));
            }

            Set<Category> children = category.getChildren();

            for (Category subcategory : children) {

                String name = "--" + subcategory.getName();
                categoriesUsedInForm.add(new Category(name));

                listChildren(categoriesUsedInForm,subcategory,1);

            }
        }
        return categoriesUsedInForm;
    }

    private void listChildren(List<Category> categoriesUsedInForm ,Category parent, int sublevel) {

        int newSublevel = sublevel + 1;

        Set<Category> children = parent.getChildren();

        for (Category subcategory : children) {
            String name = "";

            for (int i = 0; i < newSublevel ; i++) {
                name +="--";
            }
           categoriesUsedInForm.add(new Category(name));
            listChildren(categoriesUsedInForm,subcategory,newSublevel);
        }
    }


    public Category get(Integer id) throws CategoryNotFoundException {
        try {
            return repo.findById(id).get();
        }
        catch (Exception e) {
            throw new CategoryNotFoundException("Could not find category with id: " + id);
        }
    }

    public void delete(Integer id) throws CategoryNotFoundException {
        Long countById = repo.countById(id);
        if(countById== null || countById == 0) {
            throw new CategoryNotFoundException("Could not find category with id: " + id);
        }
        repo.deleteById(id);
    }
















}


