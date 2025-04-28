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

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipe> userRecipes;

    public User(Set<Role> roles, String username, String fName, String lName, String email, String password, LocalDate dob, List<Recipe> userRecipes) {
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
            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
            .collect(Collectors.toSet());
    }
}
