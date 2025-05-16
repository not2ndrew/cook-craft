package com.example.demo.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.UserDto;
import com.example.demo.Entity.Recipe;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import static com.example.demo.Enum.RoleName.*;

import com.example.demo.Mapper.UserDtoMapper;
import com.example.demo.Mapper.UserRequestMapper;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Request.UserRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final UserRequestMapper userRequestMapper;
    private final PasswordEncoder passwordEncoder;

    /* UserRequest */
    public boolean userExist(UserRequest userRequest) {
        Optional<UserRequest> userByEmail = userRepository.findUserByEmail(userRequest.getEmail())
            .map(userRequestMapper);

        if (userByEmail.isPresent()) {
            return true;
        }

        return false;
    }

    public List<UserDto> getAllUsersAndAdmins() {
        return userRepository.findAll()
            .stream()
            .map(userDtoMapper)
            .toList();
    }

    public List<UserDto> getAllUsersByRole(Role role) {
        return userRepository.findUserByRoles(role)
            .stream()
            .map(userDtoMapper)
            .toList();
    }

    public void deleteUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email)
            .orElseThrow(() -> new RuntimeException("Email: " + email + "was not found."));
        
        userRepository.delete(user);
    }

    /* OVERRIDE METHODS */

    @Override
    public void save(UserRequest userRequest) {
        Optional<UserRequest> userByEmail = userRepository.findUserByEmail(userRequest.getEmail())
            .map(userRequestMapper);

        if (userByEmail.isPresent()) {
            throw new RuntimeException("Email: " + userRequest.getEmail() + " already exists.");
        }

        Set<Role> userRoles = new HashSet<>();


        if (userRequest.getRole() != null && !userRequest.getRole().isEmpty()) {
            for (Role role : userRequest.getRole()) {
                Role databaseRole = roleRepository.findByName(role.getName())
                    .orElseGet(() -> roleRepository.save(new Role(role.getName())));
                
                userRoles.add(databaseRole);
            }
        } else {
            Role defaultRole = roleRepository.findByName(ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(ROLE_USER)));
            
            userRoles.add(defaultRole);
        }


        User user = new User(
            userRoles, 
            userRequest.getUsername(), 
            userRequest.getFName(), 
            userRequest.getLName(), 
            userRequest.getEmail(), 
            passwordEncoder.encode(userRequest.getPassword()), 
            userRequest.getDob(), 
            new ArrayList<Recipe>()
        );
        
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRequest userRequest= userRepository.findUserByEmail(username)
            .map(userRequestMapper)
            .orElseThrow(() ->
                new RuntimeException("Email: " + username + " does not exists")
            );
        
        return new org.springframework.security.core.userdetails.User(
            userRequest.getEmail(), 
            userRequest.getPassword(), 
            mapRolesToAuthorities(userRequest.getRole())
        );
    }

    @Override
    public boolean userExistByEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }


    /* HELPER METHODS */


    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
            .collect(Collectors.toList());
    }

    public User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw new RuntimeException("No authenticated user found");
        }

        String email = auth.getName(); // Because email is used as the username
        return userRepository.findUserByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}
