package org.nexus.nexkartbackend.Repository;


import org.nexus.nexkartbackend.entity.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {

    public List<Country> findAllByOrderByNameAsc();


}