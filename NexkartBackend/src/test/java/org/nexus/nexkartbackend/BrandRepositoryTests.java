package org.nexus.nexkartbackend;


import org.nexus.nexkartbackend.Repository.BrandRepository;
import org.nexus.nexkartbackend.entity.Brand;
import org.nexus.nexkartbackend.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandRepositoryTests {


    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void testCreateBrand() {

        Category laptop = new Category(1);

        Brand acer = new Brand("Acer");
        acer.getCategories().add(laptop);
        Brand savedBrand = brandRepository.save(acer);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0);

    }

}
