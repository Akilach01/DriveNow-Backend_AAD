package org.example.drivenow_carrental_aad.controller;

import org.example.drivenow_carrental_aad.dto.LoginRequestDto;
import org.example.drivenow_carrental_aad.dto.SignupRequestDto;
import org.example.drivenow_carrental_aad.dto.UserDto;
import org.example.drivenow_carrental_aad.entity.Role;
import org.example.drivenow_carrental_aad.entity.User;
import org.example.drivenow_carrental_aad.repo.RoleRepository;
import org.example.drivenow_carrental_aad.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public String registerUser(@RequestBody SignupRequestDto signupRequest) {
        if (userRepository.findByEmail(signupRequest.getEmail()) != null) {
            return "Error:Email Already Exists !";
        }
        User user = new User();
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setAddress(signupRequest.getAddress());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setContact(signupRequest.getContact());
        user.setNicNo(signupRequest.getNicNo());
        user.setUserName(signupRequest.getUserName());
        user.setStatus("ACTIVE");

        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);
        return "User Registered Successfully!";

        @PostMapping("/login")
        public String loginUser (@RequestBody LoginRequestDto loginRequest){
            User user = userRepository.findByEmail(loginRequest.getUsernameOrEmail());
            if (user == null) {
                user = userRepository.findByUserName(loginRequest.getUsernameOrEmail());
            }
            if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return "Error: Invalid username or password!";
            }
            // Later: return JWT
            return "Login successful for: " + user.getUserName();
        }


    }

