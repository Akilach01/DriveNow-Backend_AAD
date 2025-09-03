package org.example.drivenow_carrental_aad.service.impl;

import org.example.drivenow_carrental_aad.dto.UserDto;
import org.example.drivenow_carrental_aad.repo.UserRepository;
import org.example.drivenow_carrental_aad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ArrayList<UserDto> getAllUsers(){

    }

    @Override
    public UserDto getUsers(String username , String password){

    }

}
