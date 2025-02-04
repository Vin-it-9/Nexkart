package org.nexus.nexkartbackend;


import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.nexus.nexkartbackend.Repository.UserRepository;
import org.nexus.nexkartbackend.entity.Role;
import org.nexus.nexkartbackend.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.List;


@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {


    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void testCreateNewUserWithOneRole() {

        Role roleAdmin = entityManager.find(Role.class, 1);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User userNamHM = new User("admin@gmail.com",encoder.encode("admin"), "admin", "shinde");
        userNamHM.addRole(roleAdmin);
        User savedUser = repo.save(userNamHM);
        assertThat(savedUser.getId()).isGreaterThan(0);

    }


    @Test
    public void testCreateNewUserWithTwoRoles() {
        User userRavi = new User("ravi@gmail.com", "ravi2020", "Ravi", "Kumar");

        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(4);

        userRavi.addRole(roleEditor);
        userRavi.addRole(roleAssistant);

        User savedUser = repo.save(userRavi);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> listusers = repo.findAll();
        listusers.forEach(user -> System.out.println(user));

    }


    @Test
    public void testGetUserById() {
        User userNam = repo.findById(1).get();
        System.out.println(userNam);
        assertThat(userNam).isNotNull();

    }


    @Test
    public void testUpdateUserDetails() {
        User userNam = repo.findById(1).get();
        userNam.setEnabled(true);
        userNam.setEmail("vinit@gmail.com");

        repo.save(userNam);

    }

    @Test
    public void testUpdateUserRole() {

        User userRavi = repo.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSaleperson = new Role(2);

        userRavi.getRoles().remove( roleEditor);
        userRavi.addRole(roleSaleperson);

        repo.save(userRavi);
    }

    @Test
    public void testDeleteUser() {
        Integer userID = 2 ;
        repo.deleteById(userID);
    }

    @Test
    public void testGetUserByEmail() {
        String email = "vinit@gmail.com" ;
        User user =	 repo.getUserByEmail(email);
        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById() {
        Integer id = 1;
        Long countById  =	repo.countById(id);

        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testListFirstPage() {
        int pageNumber = 1;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
       Page<User>  page = repo.findAll(pageable);

        List<User> listUsers = page.getContent();

        listUsers.forEach(user -> System.out.println(user));

        assertThat(listUsers.size()).isEqualTo(pageSize);

    }


    @Test
    public void testSearchUser() {
        String keyword = "chri";

        int pageNumber = 0;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<User>  page = repo.findAll(keyword,pageable);

        List<User> listUsers = page.getContent();

        listUsers.forEach(user -> System.out.println(user));

        assertThat(listUsers.size()).isGreaterThan(0);

    }



}


