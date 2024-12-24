package org.nexus.nexkartbackend;


import org.junit.jupiter.api.Test;
import org.nexus.nexkartbackend.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Rollback(false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepository {

    private CategoryRepository repo;
//
//    @Test
//    public void testCreateCategory() {
//        Category cat = new Category("Computers");
//        Category savedCategory = repo.save();
//        assertThat(savedCategory.getId()).isGraterThan(0);
//    }


}
