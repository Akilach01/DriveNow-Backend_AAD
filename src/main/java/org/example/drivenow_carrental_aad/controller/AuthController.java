package org.example.drivenow_carrental_aad.controller;

import jakarta.validation.Valid;
import org.example.drivenow_carrental_aad.dto.LoginRequestDto;
import org.example.drivenow_carrental_aad.dto.LoginResponseDto;
import org.example.drivenow_carrental_aad.dto.SignupRequestDto;
import org.example.drivenow_carrental_aad.dto.UserDto;
import org.example.drivenow_carrental_aad.entity.Role;
import org.example.drivenow_carrental_aad.entity.User;
import org.example.drivenow_carrental_aad.repo.RoleRepository;
import org.example.drivenow_carrental_aad.repo.UserRepository;
import org.example.drivenow_carrental_aad.service.UserService;
import org.example.drivenow_carrental_aad.util.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService,AuthenticationManager authenticationManager ,JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser( @Valid @RequestBody SignupRequestDto signupRequest) {
        try {
            UserDto userDto = new UserDto();

            userDto.setFirstName(signupRequest.getFirstName());
            userDto.setLastName(signupRequest.getLastName());
            userDto.setAddress(signupRequest.getAddress());
            userDto.setContact(signupRequest.getContact());
            userDto.setNicNo(signupRequest.getNicNo());
            userDto.setUserName(signupRequest.getUserName());
            userDto.setEmail(signupRequest.getEmail());
            userDto.setPassword(signupRequest.getPassword());
            userDto.setStatus("ACTIVE");
            userDto.setRoles(Collections.singleton("ROLE_USER"));
            userService.createUser(userDto);
            return ResponseEntity.ok("User registered successfully: " + signupRequest.getUserName());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

        @PostMapping("/login")
        public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginRequestDto loginRequest) {
         authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(),loginRequest.getPassword())

         );
           User user = userService.getByUsername(loginRequest.getUsernameOrEmail());
           if (user == null){
               user = userService.getByEmail(loginRequest.getUsernameOrEmail());
           }
           if (user == null){
               throw new IllegalArgumentException("Invalid credentials");
           }
           String token = jwtTokenProvider.generateToken(user.getUserName());
            Set<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
            return ResponseEntity.ok(new LoginResponseDto(user.getUserName(),token, roles ));
        }


    }

