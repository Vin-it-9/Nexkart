package org.nexus.nexkartbackend.Repository;

import org.nexus.nexkartbackend.entity.Category;
import org.nexus.nexkartbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>,  PagingAndSortingRepository<User, Integer>  {

    @Query("SELECT u from User u WHERE u.email = :email")
    public User getUserByEmail(@Param("email") String email );

    public Long countById(Integer id);

    boolean existsByEmail(String email);



}
