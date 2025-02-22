package org.nexus.nexkartfrontend.shipping;

import org.nexus.nexkartfrontend.entity.Country;
import org.nexus.nexkartfrontend.entity.ShippingRate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRateRepository extends CrudRepository<ShippingRate, Integer> {

    public ShippingRate findByCountryAndState(Country country, String state);


}
