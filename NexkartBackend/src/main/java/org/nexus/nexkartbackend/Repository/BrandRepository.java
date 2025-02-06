package org.nexus.nexkartbackend.Repository;

import org.nexus.nexkartbackend.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> , PagingAndSortingRepository<Brand, Integer> {

    public Long countById(Integer id);

    public Brand findByName(String name);



}
