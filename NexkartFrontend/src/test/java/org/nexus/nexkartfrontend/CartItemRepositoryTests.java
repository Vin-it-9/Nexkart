package org.nexus.nexkartfrontend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.nexus.nexkartfrontend.common.product.Product;
import org.nexus.nexkartfrontend.customer.Customer;
import org.nexus.nexkartfrontend.shoppingcart.CartItem;
import org.nexus.nexkartfrontend.shoppingcart.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;



@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CartItemRepositoryTests {

    @Autowired
    private CartItemRepository repo;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSaveItem() {

        Integer customerId = 1;
        Integer productId = 6;

        Customer customer = entityManager.find(Customer.class, customerId);
        Product product = entityManager.find(Product.class, productId);

        CartItem newItem = new CartItem();
        newItem.setCustomer(customer);
        newItem.setProduct(product);
        newItem.setQuantity(1);

        CartItem savedItem = repo.save(newItem);

        assertThat(savedItem.getId()).isGreaterThan(0);

    }

    @Test
    public void testSave2Items() {
        Integer customerId = 2;
        Integer productId = 8;

        Customer customer = entityManager.find(Customer.class, customerId);
        Product product = entityManager.find(Product.class, productId);

        CartItem item1 = new CartItem();
        item1.setCustomer(customer);
        item1.setProduct(product);
        item1.setQuantity(2);

        CartItem item2 = new CartItem();
        item2.setCustomer(new Customer(customerId));
        item2.setProduct(new Product(8));
        item2.setQuantity(3);

        Iterable<CartItem> iterable = repo.saveAll(List.of(item1, item2));

        assertThat(iterable).size().isGreaterThan(0);
    }

    @Test
    public void testFindByCustomer() {

        Integer customerId = 2;
        List<CartItem> listItems = repo.findByCustomer(new Customer(customerId));

        listItems.forEach(System.out::println);

        assertThat(listItems.size()).isEqualTo(2);
    }

    @Test
    public void testFindByCustomerAndProduct() {
        Integer customerId = 2;
        Integer productId = 8;

        CartItem item = repo.findByCustomerAndProduct(new Customer(customerId), new Product(productId));

        assertThat(item).isNotNull();

        System.out.println(item);
    }

    @Test
    public void testUpdateQuantity() {
        Integer customerId = 1;
        Integer productId = 6;
        Integer quantity = 4;

        repo.updateQuantity(quantity, customerId, productId);

        CartItem item = repo.findByCustomerAndProduct(new Customer(customerId), new Product(productId));

        assertThat(item.getQuantity()).isEqualTo(4);
    }

    @Test
    public void testDeleteByCustomerAndProduct() {

        Integer customerId = 2;
        Integer productId = 8;

        repo.deleteByCustomerAndProduct(customerId, productId);

        CartItem item = repo.findByCustomerAndProduct(new Customer(customerId), new Product(productId));

        assertThat(item).isNull();
    }
}