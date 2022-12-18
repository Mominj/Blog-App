package com.example.bloggingapp.repository;

import com.example.bloggingapp.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface UsersRepo extends JpaRepository<Users, Integer> {

    @Query(value = "select count(id) from users where username = :username", nativeQuery = true)
    public int findByUserName(@Param("username") String userName);


    @Query(value = "select * from users where username = :username", nativeQuery = true)
    public Optional<Users> findByUser(@Param("username") String userName);

    @Query(value = "select * from users", nativeQuery = true)
    public Collection<Users> getAll();

    Users findUsersByUserName(String userName);


}
