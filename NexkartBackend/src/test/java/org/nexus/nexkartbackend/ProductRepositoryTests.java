package org.nexus.nexkartbackend;


import org.junit.jupiter.api.Test;
import org.nexus.nexkartbackend.Repository.CategoryRepository;
import org.nexus.nexkartbackend.Repository.ProductRepository;
import org.nexus.nexkartbackend.entity.Brand;
import org.nexus.nexkartbackend.entity.Category;
import org.nexus.nexkartbackend.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testCreateProduct() {

        Brand brand = entityManager.find(Brand.class, 1);

        Category category = entityManager.find(Category.class, 1);

        Product product = new Product();
        product.setName("Samsung S25 Ultra");
        product.setAlias("samsung product");
        product.setShortDescription("Samsung S25 Ultra the better than iphone");
        product.setFullDescription("better than iphone similar to the power of iphone 16 pro max ");
        product.setBrand(brand);
        product.setCategory(category);
        product.setPrice(456);
        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());

        Product savedProduct = productRepository.save(product);


        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isGreaterThan(0);


    }

    @Test
    public void testCreateAppleLaptop() {

        Brand brand = entityManager.find(Brand.class, 2); // Assuming Apple has ID 2
        Category category = entityManager.find(Category.class, 1); // Assuming Laptops have ID 2

        Product product = new Product();
        product.setName("MacBook Pro 16 M3");
        product.setAlias("apple laptop");
        product.setShortDescription("MacBook Pro M3 is a powerful and efficient laptop.");
        product.setFullDescription("The new MacBook Pro 16 M3 offers superior performance and stunning display.");
        product.setBrand(brand);
        product.setCategory(category);
        product.setPrice(2499);
        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());

        Product savedProduct = productRepository.save(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateAsusLaptop() {

        Brand brand = entityManager.find(Brand.class, 2); // Assuming Asus has ID 3
        Category category = entityManager.find(Category.class, 1); // Assuming Laptops have ID 2

        Product product = new Product();
        product.setName("Asus ROG Zephyrus G14");
        product.setAlias("asus laptop");
        product.setShortDescription("Asus ROG Zephyrus G14 is a powerful gaming laptop.");
        product.setFullDescription("The Asus ROG Zephyrus G14 comes with a high-refresh-rate display and powerful GPU.");
        product.setBrand(brand);
        product.setCategory(category);
        product.setPrice(1899);
        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());

        Product savedProduct = productRepository.save(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllProducts() {

        Iterable<Product> iterable = productRepository.findAll();

        iterable.forEach(System.out::println);
    }

    @Test
    public void testFindProductById() {

        Integer id = 2;
        Product product = productRepository.findById(id).get();
        System.out.println(product);
        assertThat(product).isNotNull();

    }
    
    @Test
    public void testUpdateProduct() {
        Integer id = 5;
        Product product = productRepository.findById(id).get();
        product.setEnabled(true);
        productRepository.save(product);

        Product updatedProduct = productRepository.save(product);

        assertThat(updatedProduct.getPrice()).isEqualTo(499);

    }

    @Test
    public void testSavedProductWithImages() {
        Integer id = 1;
       Product  product = productRepository.findById(id).get();

       product.setMainImage("main_image.jpg");
       product.addExtraImage("extra_image.jpg");
       product.addExtraImage("extra_image2.jpg");
       product.addExtraImage("extra_image3.jpg");

          Product saveProduct =  productRepository.save(product);
         assertThat(saveProduct.getImages().size()).isEqualTo(3);

    }

    @Test
    public void testSaveProductWithDetails() {
        Integer id = 1;
        Product  product = productRepository.findById(id).get();

        product.addDetail("Device Memory" , "128GB");
        product.addDetail("CPU Model" , "Intel i5");

        Product savedProduct = productRepository.save(product);
        assertThat(savedProduct.getDetails()).isNotNull();

    }




}

