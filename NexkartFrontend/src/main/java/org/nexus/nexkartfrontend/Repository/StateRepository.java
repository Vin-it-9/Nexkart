package org.nexus.nexkartfrontend.Repository;


import org.nexus.nexkartfrontend.entity.Country;
import org.nexus.nexkartfrontend.entity.State;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends CrudRepository<State, Integer> {

    public List<State> findByCountryOrderByNameAsc(Country country);


}