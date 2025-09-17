package org.example.drivenow_carrental_aad.service.impl;

import org.example.drivenow_carrental_aad.dto.UserDto;
import org.example.drivenow_carrental_aad.entity.User;
import org.example.drivenow_carrental_aad.repo.UserRepository;
import org.example.drivenow_carrental_aad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    private UserDto mapToDto(User user) {
        return new UserDto(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getAddress(),
                user.getContact(),
                user.getNicNo(),
                user.getUserName(),
                user.getEmail(),
//                user.getPassword(),
                null, //don't expose password
                user.getStatus()
        );
    }
    private User mapToEntity(UserDto dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setAddress(dto.getAddress());
        user.setContact(dto.getContact());
        user.setNicNo(dto.getNicNo());
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        user.setStatus(dto.getStatus());
        return user;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = mapToEntity(userDto);
        user.setStatus("ACTIVE");
        User saved = userRepository.save(user);
        return mapToDto(saved);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        return userRepository.findById(id).map(user -> {
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setAddress(userDto.getAddress());
            user.setContact(userDto.getContact());
            user.setNicNo(userDto.getNicNo());
            user.setUserName(userDto.getUserName());
            user.setEmail(userDto.getEmail());
            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }

            user.setStatus(userDto.getStatus());
            return mapToDto(userRepository.save(user));
        }).orElse(null);

    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id).map(this::mapToDto).orElse(null);
    }

    @Override
    public List<UserDto> getAllUsers() {
       return userRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(String.valueOf(id));
    }

    @Override
    public UserDto getByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user != null ? mapToDto(user):null ;
    }

    @Override
    public UserDto getByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? mapToDto(user) : null;
    }
    @Override
    public UserDto updateOwnProfile(UserDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setAddress(dto.getAddress());
        user.setContact(dto.getContact());
        user.setNicNo(dto.getNicNo());
        user.setUserName(dto.getUserName());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        User updated = userRepository.save(user);
        return mapToDto(updated);
    }
    @Override
    public UserDto updateUserStatus(Long id, String status) {
        User user = userRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(status);
        User updated = userRepository.save(user);
        return mapToDto(updated);
    }
}
