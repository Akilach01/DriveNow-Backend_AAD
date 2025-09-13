package org.example.drivenow_carrental_aad.util;

import org.example.drivenow_carrental_aad.entity.User;
import org.example.drivenow_carrental_aad.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail)throws UsernameNotFoundException {
       User user = userRepository.findByEmail(usernameOrEmail);

       if (user == null) {
           user = userRepository.findByEmail(usernameOrEmail);
       }
       if (user == null) {
           throw new UsernameNotFoundException("User not found with username: " + usernameOrEmail);

       }
       return new org.springframework.security.core.userdetails.User(
               user.getUserName(),
               user.getPassword(),
               Collections.emptyList()
       );
    }
}
