package org.nexus.nexkartbackend.Repository;


import org.nexus.nexkartbackend.entity.Country;
import org.nexus.nexkartbackend.entity.State;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends CrudRepository<State, Integer> {

    public List<State> findByCountryOrderByNameAsc(Country country);


}