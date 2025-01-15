package com.example.demo.Entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.Enum.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private Collection<Role> roles;

    private String username;

    @Column(name = "first_name")
    private String fName;

    @Column(name = "last_name")
    private String lName;
    private String email;
    private String password;
    
    private LocalDate dob;

    /* 
    The comment should be it's own class rather than a String. 
    Each comment should contain:

    Date written,
    The actual comment itself (can be empty and max limit is 100 words),
    Like and Dislikes numbers.

    */
    // private List<String> userComments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Recipe> userRecipes;

    public User(Collection<Role> roles, String username, String fName, String lName, String email, String password, LocalDate dob, List<Recipe> userRecipes) {
        this.roles = roles;
        this.username = username;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.userRecipes = userRecipes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> authorities = new HashSet<Role>();

        for (Role role : roles) {
            authorities.add(role);
        }

        return authorities.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
            .collect(Collectors.toList());
    }
}
