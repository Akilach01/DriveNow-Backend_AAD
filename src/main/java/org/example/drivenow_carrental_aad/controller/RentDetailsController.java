package org.example.drivenow_carrental_aad.controller;

import org.example.drivenow_carrental_aad.dto.RentDetailsDto;
import org.example.drivenow_carrental_aad.service.RentDetailService;
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
    public RentDetailsDto createBooking(@RequestBody RentDetailsDto rentDetails) {
        return service.createBooking(rentDetails);
}

    @PreAuthorize("hasRole('ADMIN')")
@GetMapping
    public List<RentDetailsDto> getAllBookings() {
        return service.getAllBookings();
}
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
@GetMapping("/{id}")
    public RentDetailsDto getBooking(@PathVariable Long id) {
        return service.getBookingById(id);
}
}
