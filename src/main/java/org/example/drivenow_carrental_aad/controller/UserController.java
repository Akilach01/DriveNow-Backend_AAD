package org.example.drivenow_carrental_aad.controller;

import jakarta.validation.Valid;
import org.example.drivenow_carrental_aad.dto.UserDto;
import org.example.drivenow_carrental_aad.entity.User;
import org.example.drivenow_carrental_aad.service.NotificationService;
import org.example.drivenow_carrental_aad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final NotificationService notificationService;


    public UserController(UserService userService, NotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }

    //admin can also create users
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto dto) {
        UserDto created = userService.createUser(dto);
        try {
            notificationService.sendEmailNotification(created.getUserId(),
                    "Welcome to Drive Now",
                    "Your account has been created successfully.");
        } catch (Exception e) {
            // Log error but don't fail creation
        }
        return ResponseEntity.ok(created);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity <List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity <UserDto> updateUser(@PathVariable Long id,@Valid @RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/me")
    public ResponseEntity <UserDto> updateOwnProfile(@Valid @RequestBody UserDto dto){
        return ResponseEntity.ok(userService.updateOwnProfile(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/block")
    public  ResponseEntity <UserDto> blockUser(@PathVariable Long id){
        UserDto updated = userService.updateUserStatus(id, User.Status.BLOCKED.name());
        try {
            notificationService.sendEmailNotification(id,
                    "Account Blocked",
                    "Your account has been blocked by the admin.");
        } catch (Exception e) {
            // Log error but don't fail update
        }
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/unblock")
    public ResponseEntity <UserDto> unblockUser(@PathVariable Long id) {
        UserDto updated = userService.updateUserStatus(id, User.Status.ACTIVE.name());
        try {
            notificationService.sendEmailNotification(id,
                    "Account Unblocked",
                    "Your account has been unblocked by the admin.");
        } catch (Exception e) {

        }
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity <Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

