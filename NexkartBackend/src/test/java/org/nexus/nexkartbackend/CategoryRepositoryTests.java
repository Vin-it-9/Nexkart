package org.nexus.nexkartbackend;



import org.junit.jupiter.api.Test;
import org.nexus.nexkartbackend.Repository.CategoryRepository;
import org.nexus.nexkartbackend.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository repo;

    @Test
    public void testCreateRootCategory() {
        Category rootCategory = new Category("Computers");
        Category savedCategory = repo.save(rootCategory);
        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateRootCategory2() {
        Category rootCategory = new Category("Electronics");
        Category savedCategory = repo.save(rootCategory);
        assertThat(savedCategory.getId()).isGreaterThan(0);

    }

    @Test
    public void testCreateRootCategory3() {
        Category rootCategory = new Category("Automobile");
        Category savedCategory = repo.save(rootCategory);
        assertThat(savedCategory.getId()).isGreaterThan(0);

    }

    @Test
    public void testCreateSubCategory3() {
        Category parent = new Category(1);
        Category laptops = new Category("Dekstops", parent);
        repo.save(laptops);
    }

    @Test
    public void testCreateSubCategory4() {
        Category parent = new Category(1);
        Category laptops = new Category("Computer Components", parent);
        repo.save(laptops);
    }

    @Test
    public void testCreateSubCategory1() {
        Category parent = new Category(1);
        Category laptops = new Category("Laptop", parent);
        repo.save(laptops);
    }


    @Test
    public void testCreateSubCategory() {
        Category parent = new Category(2);
        Category Mouse = new Category("Cameras", parent);
        Category components = new Category("Smartphones", parent);

        repo.saveAll(List.of(Mouse, components));

    }

    @Test
    public void testCreateSubCategory2() {
        Category parent = new Category(5);
        Category Mouse = new Category("Memory", parent);
        Category savedMouse = repo.save(Mouse);
        assertThat(savedMouse.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetCategory() {

        Category category = repo.findById(2).get();
        System.out.println(category.getName());

        Set<Category> children = category.getChildren();

        for (Category subcategory : children) {
            System.out.println(subcategory.getName());
        }

        assertThat(children.size()).isGreaterThan(0);

    }

    @Test
    public void testPringHierarCategories() {

        Iterable<Category> categories = repo.findAll();
        for (Category category : categories) {
            if (category.getParent() == null) {
                System.out.println(category.getName());
            }
            Set<Category> children = category.getChildren();

            for (Category subcategory : children) {
                System.out.println("--"+subcategory.getName());
                printChildren(subcategory , 1);
            }

        }

    }

    private void printChildren(Category parent , int sublevel) {

        int newSublevel = sublevel + 1;

        Set<Category> children = parent.getChildren();

            for (Category subcategory : children) {
                for (int i = 0; i < newSublevel ; i++) {
                    System.out.print("--");
                }
                System.out.println(subcategory.getName());

                printChildren(subcategory , newSublevel);
            }
    }


}
