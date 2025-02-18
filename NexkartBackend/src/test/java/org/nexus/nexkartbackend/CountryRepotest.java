package org.nexus.nexkartbackend;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.nexus.nexkartbackend.Repository.CountryRepository;
import org.nexus.nexkartbackend.entity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CountryRepotest {

    @Autowired private CountryRepository repo;

    @Test
    public void testCreateMultipleCountries() {
        List<Country> countries = List.of(
                new Country("India", "IN"),
                new Country("United States", "US"),
                new Country("United Kingdom", "UK"),
                new Country("Canada", "CA"),
                new Country("Australia", "AU")
        );

        List<Country> savedCountries = (List<Country>) repo.saveAll(countries);

        assertThat(savedCountries).isNotNull();
        savedCountries.forEach(country -> assertThat(country.getId()).isGreaterThan(0));
    }


    @Test
    public void testListCountries() {
        List<Country> listCountries = repo.findAllByOrderByNameAsc();
        listCountries.forEach(System.out::println);

        assertThat(listCountries.size()).isGreaterThan(0);
    }
    @Test
    public void testUpdateCountry() {
        Integer id = 1;
        String name = "Republic of India";

        Country country = repo.findById(id).get();
        country.setName(name);

        Country updatedCountry = repo.save(country);

        assertThat(updatedCountry.getName()).isEqualTo(name);
    }

    @Test
    public void testGetCountry() {
        Integer id = 2;
        Country country = repo.findById(id).get();
        assertThat(country).isNotNull();
    }

    @Test
    public void testDeleteCountry() {
        Integer id = 2;
        repo.deleteById(id);

        Optional<Country> findById = repo.findById(id);
        assertThat(findById.isEmpty());
    }


}
