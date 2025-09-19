package org.example.drivenow_carrental_aad.controller;

import jakarta.validation.Valid;
import org.example.drivenow_carrental_aad.dto.RentDetailsDto;
import org.example.drivenow_carrental_aad.service.RentDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class RentDetailsController {

    private final RentDetailService service;

    public RentDetailsController(RentDetailService service) {
        this.service = service;
    }
    @PreAuthorize("hasRole('USER')")
@PostMapping
    public ResponseEntity <RentDetailsDto> createBooking(@Valid @RequestBody RentDetailsDto rentDetails) {
        return ResponseEntity.ok(service.createBooking(rentDetails));
}

@PreAuthorize("hasRole('ADMIN')")
@GetMapping
    public ResponseEntity <List<RentDetailsDto>> getAllBookings() {
        return ResponseEntity.ok (service.getAllBookings());
}

@PreAuthorize("hasAnyRole('USER','ADMIN')")
@GetMapping("/{id}")
    public ResponseEntity <RentDetailsDto> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok (service.getBookingById(id));
}

@PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/cancel")
    public ResponseEntity <RentDetailsDto> cancelBooking(@PathVariable Long id){
        return ResponseEntity.ok (service.cancelBooking(id));
}

@GetMapping("/user")
@PreAuthorize("hasRole('USER')")
public ResponseEntity <List<RentDetailsDto>> getUserBookings() {
    return  ResponseEntity.ok (service.getUserBookings());
}


}
