package org.nexus.nexkartbackend.Repository;

import org.nexus.nexkartbackend.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    boolean existsByName(String name);

    Optional<Role> findByName(String name);



}