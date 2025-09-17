package org.example.drivenow_carrental_aad.service.impl;

import org.example.drivenow_carrental_aad.dto.RentDetailsDto;
import org.example.drivenow_carrental_aad.entity.Driver;
import org.example.drivenow_carrental_aad.entity.RentDetails;
import org.example.drivenow_carrental_aad.entity.User;
import org.example.drivenow_carrental_aad.entity.Vehicle;
import org.example.drivenow_carrental_aad.repo.DriverRepository;
import org.example.drivenow_carrental_aad.repo.RentRepository;
import org.example.drivenow_carrental_aad.repo.UserRepository;
import org.example.drivenow_carrental_aad.repo.VehicleRepository;
import org.example.drivenow_carrental_aad.service.RentDetailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentDetailServiceImpl implements RentDetailService {

    private final RentRepository rentRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    

    public RentDetailServiceImpl(RentRepository repository,UserRepository userRepository,VehicleRepository vehicleRepository,DriverRepository driverRepository) {
        this.rentRepository = rentRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public RentDetailsDto createBooking(RentDetailsDto dto){
        RentDetails rent = new RentDetails();
        rent.setDate(dto.getDate());
        rent.setPickupDate(dto.getPickupDate());
        rent.setReturnDate(dto.getReturnDate());
        rent.setPayment(dto.getPayment());
        rent.setStatus("pending");

        if (dto.getUser() != null && dto.getUser().getUserId() != null) {
            User user = UserRepository.findById(dto.getUser().getUserId())
                    .orElseThrow(()->new RuntimeException("user not found !"));
            rent.setUser(user);
        }
        if (dto.getVehicle() != null && dto.getVehicle().getVehicleId() != null) {
            Vehicle vehicle = VehicleRepository.findById(dto.getVehicle().getVehicleId())
                    .orElseThrow(()->new RuntimeException("vehicle not found !"));
            rent.setVehicle(vehicle);
        }
        if (dto.getDriver() != null && dto.getDriver().getDriverId() != null) {
            Driver driver = DriverRepository.findById(dto.getDriver().getDriverId())
                    .orElseThrow(()->new RuntimeException("driver not found !"));
            rent.setDriver(driver);
        }

        RentDetails saved = rentRepository.save(rent);

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

    @Override
    public RentDetailsDto cancelBooking(Long id){
        RentDetails rent = rentRepository.findById(id).orElseThrow())->new RuntimeException("booking not found"));
    rent.setStatus("canceled");
    RentDetails updated = rentRepository.save(rent);

    RentDetailsDto dto = new RentDetailsDto();

        dto.setRentId(updated.getRentId());
        dto.setDate(updated.getDate());
        dto.setPickupDate(updated.getPickupDate());
        dto.setReturnDate(updated.getReturnDate());
        dto.setPayment(updated.getPayment());
        dto.setStatus(updated.getStatus());
        return dto;
    }

    @Override
    public RentDetailsDto approveBooking(Long id) {
        return null;
    }

    @Override
    public RentDetailsDto rejectBooking(Long id) {
        return null;
    }

    @Override
    public RentDetailsDto assignDriver(Long bookingId, Long driverId) {
        return null;
    }

    @Override
    public List<RentDetailsDto> getUserBookings() {
        return List.of();
    }
}
