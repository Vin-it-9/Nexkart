package org.nexus.nexkartbackend.Repository;

import org.nexus.nexkartbackend.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> , PagingAndSortingRepository<Brand, Integer> {

    public Long countById(Integer id);

    public Brand findByName(String name);

    @Query("select b from brands b where b.name like %?1%")
    public Page<Brand> findAll(String keyword, Pageable pageable);

    @Query("select  new  brands(b.id ,b.name) from brands b order by b.name asc")
    public List<Brand> findAll();



}
