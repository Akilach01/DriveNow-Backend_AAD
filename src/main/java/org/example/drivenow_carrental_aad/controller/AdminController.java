package org.example.drivenow_carrental_aad.controller;

import jakarta.validation.Valid;
import org.example.drivenow_carrental_aad.dto.RentDetailsDto;
import org.example.drivenow_carrental_aad.dto.UserDto;
import org.example.drivenow_carrental_aad.service.AdminService;
import org.example.drivenow_carrental_aad.service.DriverService;
import org.example.drivenow_carrental_aad.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

private final AdminService adminService;
private final UserService userService;
private final DriverService driverService;

public AdminController(AdminService adminService, UserService userService, DriverService driverService){
    this.adminService = adminService;
    this.userService = userService;
    this.driverService = driverService;

}
@PutMapping("/bookings/{id}/approve")
public ResponseEntity<RentDetailsDto> approveBooking(@PathVariable Long id) {
    return ResponseEntity.ok(adminService.approveBooking(id));
}

@PutMapping("/bookings/{id}/reject")
public ResponseEntity<RentDetailsDto> rejectBooking(@PathVariable Long id) {
    return ResponseEntity.ok(adminService.rejectBooking(id));
}

@PutMapping("/bookings/{bookingId}/driver/{driverId}")
public ResponseEntity<RentDetailsDto> assignDriver(@PathVariable Long bookingId,@PathVariable Long driverId){
    return ResponseEntity.ok(adminService.assignDriver(bookingId,driverId));

}

@PostMapping("/users")
public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
    return ResponseEntity.ok(userService.createUser(userDto));
}

@PutMapping("/users/{id}")
public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto){
    return ResponseEntity.ok(userService.updateUser(id, userDto));
}

@GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

@DeleteMapping("/users/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable Long id){
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
}

@PutMapping("/users/{id}/status")
public ResponseEntity<UserDto> updateUserStatus(@PathVariable Long id, @RequestBody String status){
    return ResponseEntity.ok(userService.updateUserStatus(id, status));
}

@PostMapping("/drivers")


}
