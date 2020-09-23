package com.codecool.peermentoringbackend.security;

import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.UserModel;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

@Component
@RestController
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    /**
     * Loads the user from the DB and converts it to Spring Security's internal User object.
     * Spring will call this code to retrieve a user upon login from the DB.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));



        if (user == null) {
            throw new UsernameNotFoundException("Username: " + username + " not found");
        }

        return new User(user.getUsername(), user.getPassword(),
                user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}