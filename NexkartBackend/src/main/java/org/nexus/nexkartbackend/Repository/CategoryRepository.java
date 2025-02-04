package org.nexus.nexkartbackend.Repository;


import org.nexus.nexkartbackend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.nexus.nexkartbackend.entity.Category;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer>, PagingAndSortingRepository<Category, Integer> {

    public Long countById(Integer id);


    @Query("select c from Category c where c.parent.id is null")
    public Page<Category> findRootCategories(Pageable pageable);


    @Query("SELECT c FROM Category c WHERE c.name LIKE CONCAT('%', ?1, '%') OR c.alias LIKE CONCAT('%', ?1, '%')")
    public Page<Category> search(String keyword, Pageable pageable);





}