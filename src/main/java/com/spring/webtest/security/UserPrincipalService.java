package com.spring.webtest.security;

import com.spring.webtest.database.entities.User;
import com.spring.webtest.database.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPrincipalService implements UserDetailsService {

    private final UserRepository repository;

    public UserPrincipalService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserPrincipal loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Could not find User with email '%s'", email))
        );
        return new UserPrincipal(user, List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
