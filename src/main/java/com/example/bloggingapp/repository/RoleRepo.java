package com.example.bloggingapp.repository;

import com.example.bloggingapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.Set;

@EnableJpaRepositories
@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {
    @Query(value = "select * from role where role_name = :role_name", nativeQuery = true)
    Role findByRoleName(@Param("role_name") String role_name);
}
