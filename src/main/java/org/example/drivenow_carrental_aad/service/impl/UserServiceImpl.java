package org.example.drivenow_carrental_aad.service.impl;

import org.example.drivenow_carrental_aad.dto.UserDto;
import org.example.drivenow_carrental_aad.entity.User;
import org.example.drivenow_carrental_aad.repo.UserRepository;
import org.example.drivenow_carrental_aad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
                user.getPassword(),
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
        user.setPassword(dto.getPassword());
        user.setStatus(dto.getStatus());
        return user;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User saved = userRepository.save(mapToEntity(userDto));
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
            user.setPassword(userDto.getPassword());
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
        userRepository.deleteById(id);
    }

    @Override
    public User getByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user != null ? mapToDto(user):null ;
    }

    @Override
    public User getByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? mapToDto(user) : null;
    }
}
