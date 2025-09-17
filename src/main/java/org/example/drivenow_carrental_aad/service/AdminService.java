package org.example.drivenow_carrental_aad.service;

import org.example.drivenow_carrental_aad.dto.RentDetailsDto;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

RentDetailsDto approveBooking(Long id);
RentDetailsDto rejectBooking(Long id);
RentDetailsDto assignDriver(Long bookingId, Long driverId);


}
