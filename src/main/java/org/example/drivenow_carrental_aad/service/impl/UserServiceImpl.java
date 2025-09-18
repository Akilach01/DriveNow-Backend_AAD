package org.example.drivenow_carrental_aad.service.impl;

import org.example.drivenow_carrental_aad.dto.UserDto;
import org.example.drivenow_carrental_aad.entity.Role;
import org.example.drivenow_carrental_aad.entity.User;
import org.example.drivenow_carrental_aad.repo.RoleRepository;
import org.example.drivenow_carrental_aad.repo.UserRepository;
import org.example.drivenow_carrental_aad.service.NotificationService;
import org.example.drivenow_carrental_aad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final NotificationService notificationService;

    public UserServiceImpl(UserRepository userRepository,RoleRepository roleRepository, PasswordEncoder passwordEncoder,NotificationService notificationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.notificationService = notificationService;
    }
    private UserDto mapToDto(User user) {
      UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setAddress(user.getAddress());
        dto.setContact(user.getContact());
        dto.setNicNo(user.getNicNo());
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setStatus(user.getStatus().name());
        dto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
        return dto;
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
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getStatus() != null) {
            user.setStatus(User.Status.valueOf(dto.getStatus()));
        }
        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            Set<Role> roles = dto.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName))
                    .filter(role -> role != null)
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }
        return user;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.findByUserName(userDto.getUserName()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = mapToEntity(userDto);
        if (user.getStatus() == null) {
            user.setStatus(User.Status.ACTIVE);
        }
        User saved = userRepository.save(user);
        try {
            notificationService.sendEmailNotification(saved.getUserId(),
                    "Welcome to Drive Now",
                    "Your account has been created successfully.");
        } catch (Exception e) {
            // Log error but don't fail creation
        }

        return mapToDto(saved);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        User updated = mapToEntity(userDto);
        updated.setUserId(id);
        if (userDto.getPassword() == null || userDto.getPassword().isBlank()) {
            updated.setPassword(existing.getPassword());
        }
        User saved = userRepository.save(updated);
        try {
            notificationService.sendEmailNotification(saved.getUserId(),
                    "Profile Updated",
                    "Your profile has been updated by the admin.");
        } catch (Exception e) {

            }
        return mapToDto(saved);
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id).map(this::mapToDto).orElseThrow(() ->new IllegalArgumentException("User not found"));
    }

    @Override
    public List<UserDto> getAllUsers() {
       return userRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)){
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User getByEmail(String email) {
       return userRepository.findByEmail(email);

    }

    @Override
    public User getByUsername(String username) {
       return userRepository.findByUsername(username);

    }
    @Override
    public UserDto updateOwnProfile(UserDto userDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User existing = userRepository.findByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        User updated = mapToEntity(userDto);
        updated.setUserId(existing.getUserId());
        updated.setPassword(existing.getPassword());
        updated.setStatus(existing.getStatus());
        updated.setRoles(existing.getRoles());
        User saved = userRepository.save(updated);
        try {
            notificationService.sendEmailNotification(saved.getUserId(),
                    "Profile Updated",
                    "You have successfully updated your profile.");
        } catch (Exception e) {

        }return mapToDto(saved);

    }

    @Override
    public UserDto updateUserStatus(Long id, String status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
  try{
      user.setStatus(User.Status.valueOf(status));
  } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid status:" + status);
  }
  User saved = userRepository.save(user);
  try{
    String subject = status.equals(User.Status.BLOCKED.name()) ? "Account is Blocked" : "Account Unblocked";
      String message = status.equals(User.Status.BLOCKED.name())
              ? "Your account has been blocked by admin." : "account has been unblocked.";
      notificationService.sendEmailNotification(saved.getUserId(), subject,message );

  } catch (Exception e) {
  }
        return mapToDto(saved);
    }
}
