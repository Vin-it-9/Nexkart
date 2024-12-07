package org.nexus.nexkartbackend;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.nexus.nexkartbackend.Repository.RoleRepository;
import org.nexus.nexkartbackend.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository repo;

    @Test
    public void testCreateFirstRole() {

        Role roleAdmin = new Role("Admin", "manage everything");
        Role savedRole = repo.save(roleAdmin);
        Assertions.assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateRestRoles() {

        Role roleAdmin = new Role("Admin", "manage everything");
        Role roleSalesperson = new Role("Salesperson",
                "manage product price, " + "customers, shipping, orders and sales report");
        Role roleEditor = new Role("Editor", "manage categories, brands, " + "products, articles and menus");

        Role roleShipper = new Role("Shipper", "view products, view orders " + "and update order status");

        Role roleAssistant = new Role("Assistant", "manage questions and reviews");

        repo.saveAll(List.of(roleAdmin ,roleSalesperson, roleEditor, roleShipper, roleAssistant));

    }

}
