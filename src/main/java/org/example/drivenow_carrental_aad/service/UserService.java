package org.example.drivenow_carrental_aad.service;

import org.example.drivenow_carrental_aad.dto.UserDto;
import org.example.drivenow_carrental_aad.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    static UserDto updateOwnProfile(UserDto dto) {
    }

    UserDto createUser(UserDto userDto);
    UserDto updateUser(Long id,UserDto userDto);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    void deleteUser(Long id);

    User getByEmail(String email);
    User getByUsername(String username);

}
