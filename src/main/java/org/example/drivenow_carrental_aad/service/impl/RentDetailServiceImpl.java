package org.example.drivenow_carrental_aad.service.impl;

import org.example.drivenow_carrental_aad.dto.RentDetailsDto;
import org.example.drivenow_carrental_aad.entity.RentDetails;
import org.example.drivenow_carrental_aad.repo.RentRepository;
import org.example.drivenow_carrental_aad.service.RentDetailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentDetailServiceImpl implements RentDetailService {

    private final RentRepository repository;
    public RentDetailServiceImpl(RentRepository repository) {
        this.repository = repository;
    }

    @Override
    public RentDetailsDto createBooking(RentDetailsDto dto){
        RentDetails rent = new RentDetails();
        rent.setDate(dto.getDate());
        rent.setPickupDate(dto.getPickupDate());
        rent.setReturnDate(dto.getReturnDate());
        rent.setPayment(dto.getPayment());
        rent.setStatus("pending");

        RentDetails saved = repository.save(rent);

        dto.setRentId(saved.getRentId());
        dto.setStatus(saved.getStatus());
        return dto;
    }

    @Override
    public List<RentDetailsDto> getAllBookings(){
        return repository.findAll().stream().map(rent -> {
            RentDetailsDto dto = new RentDetailsDto();
            dto.setRentId(rent.getRentId());
            dto.setDate(rent.getDate());
            dto.setPickupDate(rent.getPickupDate());
            dto.setReturnDate(rent.getReturnDate());
            dto.setPayment(rent.getPayment());
            dto.setStatus(rent.getStatus());
            return dto;
        }).collect(Collectors.toList());
    }


    @Override
    public RentDetailsDto getBookingById(Long id) {
        return repository.findById(id).map(rent ->{
            RentDetailsDto dto = new RentDetailsDto();
            dto.setRentId(rent.getRentId());
            dto.setDate(rent.getDate());
            dto.setPickupDate(rent.getPickupDate());
            dto.setReturnDate(rent.getReturnDate());
            dto.setPayment(rent.getPayment());
            dto.setStatus(rent.getStatus());
            return dto;
                }).orElse(null);
    }
}
