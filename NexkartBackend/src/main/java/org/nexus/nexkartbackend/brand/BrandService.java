package org.nexus.nexkartbackend.brand;


import org.nexus.nexkartbackend.Repository.BrandRepository;
import org.nexus.nexkartbackend.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> listAll() {
        return (List<Brand>) brandRepository.findAll();
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



