package org.example.drivenow_carrental_aad.service.impl;

import org.example.drivenow_carrental_aad.dto.RentDetailsDto;
import org.example.drivenow_carrental_aad.service.AdminService;
import org.example.drivenow_carrental_aad.service.RentDetailService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final RentDetailService rentDetailService;

    public AdminServiceImpl(RentDetailService rentDetailService){
        this.rentDetailService = rentDetailService;
    }

    @Override
    public RentDetailsDto approveBooking(Long id) {
        return rentDetailService.approveBooking(id);
    }

    @Override
    public RentDetailsDto rejectBooking(Long id) {
        return rentDetailService.rejectBooking(id);
    }

    @Override
    public RentDetailsDto assignDriver(Long bookingId, Long driverId) {
        return rentDetailService.assignDriver(bookingId,driverId);
    }
}
