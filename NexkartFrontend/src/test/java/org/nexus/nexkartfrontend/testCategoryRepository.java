package org.nexus.nexkartfrontend;


import org.junit.jupiter.api.Test;
import org.nexus.nexkartfrontend.common.category.Category;
import org.nexus.nexkartfrontend.common.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class testCategoryRepository {


    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testFindAll() {
        List<Category> categories = categoryRepository.findAllEnabled();
        categories.forEach(category -> {
            System.out.println(category.getName() + " (" + category.isEnabled() + ")");

        });
    }

    @Test
    public void testFindCategoryByAlias() {
        String alias = "smartwatches";
        Category category = categoryRepository.findByAliasEnabled(alias);
        assertThat(category).isNotNull();
    }


}
