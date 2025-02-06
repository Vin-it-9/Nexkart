package org.nexus.nexkartbackend.Repository;

import org.nexus.nexkartbackend.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BrandRepository extends JpaRepository<Brand, Integer> , PagingAndSortingRepository<Brand, Integer> {

    public Long countById(Integer id);

}
