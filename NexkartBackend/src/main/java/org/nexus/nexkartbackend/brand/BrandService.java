package org.nexus.nexkartbackend.brand;


import jakarta.transaction.Transactional;
import org.nexus.nexkartbackend.Repository.BrandRepository;
import org.nexus.nexkartbackend.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class BrandService {

    public static final int BRANDS_PER_PAGE = 10;

    @Autowired
    private BrandRepository brandRepository;


    public List<Brand> listAll() {
        return (List<Brand>) brandRepository.findAll();
    }

    public Page<Brand> listByPage(int pageNum , String keyword) {

        Pageable pageable = PageRequest.of(pageNum - 1, BRANDS_PER_PAGE);

        if(keyword != null) {

            return brandRepository.findAll(keyword, pageable);

        }

        return brandRepository.findAll(pageable);

    }


    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }


    public Brand get(Integer id) throws BrandNotFoundException {
        try {
            return brandRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new BrandNotFoundException("Could not find any brand with id " + id);
        }
    }

    public void delete(Integer id) throws BrandNotFoundException {

        Long countById = brandRepository.countById(id);

        if (countById == null || countById == 0) {
            throw new BrandNotFoundException("Could not find any brand with id " + id);
        }
        brandRepository.deleteById(id);
    }


}



