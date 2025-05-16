package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    public Optional<User> findUserByEmail(String email);
    public Optional<User> findUserByUsername(String username);
    public Optional<User> findUserByRoles(Role role);
}
