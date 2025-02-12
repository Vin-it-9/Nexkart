package org.nexus.nexkartfrontend.common.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> , PagingAndSortingRepository<Category, Integer> {


    @Query("select c from Category  c where  c.enabled = true order by c.name asc ")
    public List<Category> findAllEnabled();

    @Query("SELECT c FROM Category c WHERE c.enabled = true AND c.alias = ?1")
    public Category findByAliasEnabled(String alias);




}
