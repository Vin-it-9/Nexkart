package org.nexus.nexkartbackend.Repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.nexus.nexkartbackend.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer>, PagingAndSortingRepository<Category, Integer> {

    public Long countById(Integer id);


}