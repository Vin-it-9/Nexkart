package org.nexus.nexkartfrontend;

import org.junit.jupiter.api.Test;
import org.nexus.nexkartfrontend.address.*;
import org.nexus.nexkartfrontend.customer.Customer;
import org.nexus.nexkartfrontend.entity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class AddressRepositoryTests {

    @Autowired private
    AddressRepository repo;

    @Test
    public void testAddNew() {
        Integer customerId = 1;
        Integer countryId = 1;
        Address newAddress = new Address();
        newAddress.setCustomer(new Customer(customerId));
        newAddress.setCountry(new Country(countryId));
        newAddress.setFirstName("Charles");
        newAddress.setLastName("Brugger");
        newAddress.setPhoneNumber("646-232-3902");
        newAddress.setAddressLine1("204 Morningview Lane");
        newAddress.setCity("New York");
        newAddress.setState("New York");
        newAddress.setPostalCode("10013");

        Address savedAddress = repo.save(newAddress);

        assertThat(savedAddress).isNotNull();
        assertThat(savedAddress.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindByCustomer() {
        Integer customerId = 1;
        List<Address> listAddresses = repo.findByCustomer(new Customer(customerId));
        assertThat(listAddresses.size()).isGreaterThan(0);

        listAddresses.forEach(System.out::println);
    }

    @Test
    public void testFindByIdAndCustomer() {
        Integer addressId = 2;
        Integer customerId = 1;

        Address address = repo.findByIdAndCustomer(addressId, customerId);

        assertThat(address).isNotNull();
        System.out.println(address);
    }

    @Test
    public void testUpdate() {
        Integer addressId = 2;
        String phoneNumber = "646-232-3932";

        Address address = repo.findById(addressId).get();
        address.setPhoneNumber(phoneNumber);

        Address updatedAddress = repo.save(address);
        assertThat(updatedAddress.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    public void testDeleteByIdAndCustomer() {
        Integer addressId = 1;
        Integer customerId = 1;

        repo.deleteByIdAndCustomer(addressId, customerId);

        Address address = repo.findByIdAndCustomer(addressId, customerId);
        assertThat(address).isNull();
    }

    @Test
    public void testSetDefault() {
        Integer addressId = 1;
        repo.setDefaultAddress(addressId);

        Address address = repo.findById(addressId).get();
        assertThat(address.isDefaultForShipping()).isTrue();
    }


}