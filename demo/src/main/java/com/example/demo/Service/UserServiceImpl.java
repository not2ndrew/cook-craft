package com.example.demo.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final UserRequestMapper userRequestMapper;

    /* UserRequest */
    public boolean userExist(UserRequest userRequest) {
        Optional<UserRequest> userByEmail = userRepository.findUserByEmail(userRequest.getEmail())
            .map(userRequestMapper);

        if (userByEmail.isPresent()) {
            return true;
        }

        return false;
    }

    @Override
    public void save(UserRequest userRequest) {
        Optional<UserRequest> userByEmail = userRepository.findUserByEmail(userRequest.getEmail())
            .map(userRequestMapper);

        if (userByEmail.isPresent()) {
            throw new RuntimeException("Email: " + userRequest.getEmail() + " already exists.");
        }

        User user = new User(
            new HashSet<Role>(), 
            userRequest.getUsername(), 
            userRequest.getFName(), 
            userRequest.getLName(), 
            userRequest.getEmail(), 
            passwordEncoder().encode(userRequest.getPassword()), 
            userRequest.getDob(), 
            new ArrayList<Recipe>()
        );
        
        Role role = roleRepository.findByName(ROLE_USER)
            .orElseGet(() -> 
                roleRepository.save(new Role(ROLE_USER))
            );

        roleRepository.save(role);
        user.getRoles().add(role);
        
        userRepository.save(user);
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userRepository.findUserByEmail(username)
            .map(userDtoMapper)
            .orElseThrow(() ->
                new RuntimeException("Email: " + username + " does not exists")
            );
        
        return new org.springframework.security.core.userdetails.User(
            userDto.getEmail(), 
            userDto.getPassword(), 
            mapRolesToAuthorities(userDto.getRole())
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
            .collect(Collectors.toList());
    }
}
