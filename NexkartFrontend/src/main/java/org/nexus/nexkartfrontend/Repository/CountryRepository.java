package org.nexus.nexkartfrontend.Repository;


import org.nexus.nexkartfrontend.entity.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, Integer> {

    public List<Country> findAllByOrderByNameAsc();


}