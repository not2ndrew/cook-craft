package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    // @Query is a little different compared to MySQL. Make sure to note the difference.
    /* 
    This SQL statement is identical to query jpa
    SELECT * FROM USER WHERE EMAIL = ?

    u is an "alias" used for the entity User
    ?1 is a placeholder for the first method parameter. If the parameter of findUserByEmail is "andrewho",
    then mySQL equivalent statement is:
    SELECT * FROM USER WHERE EMAIL = "andrewho"
     */

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public Optional<User> findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    public Optional<User> findUserByUsername(String username);
}
