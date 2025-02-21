package org.nexus.nexkartfrontend.address;

import org.nexus.nexkartfrontend.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repo;

    public List<Address> listAddressBook(Customer customer) {
        return repo.findByCustomer(customer);
    }

}
