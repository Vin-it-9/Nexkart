package org.nexus.nexkartfrontend.address;

import org.nexus.nexkartfrontend.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    public List<Address> findByCustomer(Customer customer);

    @Query("SELECT a FROM Address a WHERE a.id = ?1 AND a.customer.id = ?2")
    public Address findByIdAndCustomer(Integer addressId, Integer customerId);

    @Query("DELETE FROM Address a WHERE a.id = ?1 AND a.customer.id = ?2")
    @Modifying
    public void deleteByIdAndCustomer(Integer addressId, Integer customerId);

}