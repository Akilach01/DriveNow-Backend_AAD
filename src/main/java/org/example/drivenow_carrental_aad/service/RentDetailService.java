package org.example.drivenow_carrental_aad.service;

import org.example.drivenow_carrental_aad.dto.RentDetailsDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RentDetailService {

    RentDetailsDto createBooking(RentDetailsDto rentDetailsDto);
    List<RentDetailsDto> getAllBookings();
 RentDetailsDto getBookingById(Long id);

}
