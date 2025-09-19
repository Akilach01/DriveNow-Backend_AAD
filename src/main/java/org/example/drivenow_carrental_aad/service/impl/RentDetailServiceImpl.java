package org.example.drivenow_carrental_aad.service.impl;

import org.example.drivenow_carrental_aad.dto.DriverDto;
import org.example.drivenow_carrental_aad.dto.RentDetailsDto;
import org.example.drivenow_carrental_aad.dto.UserDto;
import org.example.drivenow_carrental_aad.dto.VehicleDto;
import org.example.drivenow_carrental_aad.entity.Driver;
import org.example.drivenow_carrental_aad.entity.RentDetails;
import org.example.drivenow_carrental_aad.entity.User;
import org.example.drivenow_carrental_aad.entity.Vehicle;
import org.example.drivenow_carrental_aad.repo.DriverRepository;
import org.example.drivenow_carrental_aad.repo.RentRepository;
import org.example.drivenow_carrental_aad.repo.UserRepository;
import org.example.drivenow_carrental_aad.repo.VehicleRepository;
import org.example.drivenow_carrental_aad.service.NotificationService;
import org.example.drivenow_carrental_aad.service.RentDetailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentDetailServiceImpl implements RentDetailService {

    private final RentRepository rentRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final NotificationService notificationService;
    

    public RentDetailServiceImpl(RentRepository repository,UserRepository userRepository,VehicleRepository vehicleRepository,DriverRepository driverRepository, NotificationService notificationService) {
        this.rentRepository = rentRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
        this.notificationService = notificationService;
    }

    private RentDetailsDto mapToDto(RentDetails rent) {
        RentDetailsDto dto = new RentDetailsDto();
        dto.setRentId(rent.getRentId());
        dto.setDate(rent.getDate());
        dto.setPickupDate(rent.getPickupDate());
        dto.setReturnDate(rent.getReturnDate());
        dto.setPayment(rent.getPayment());
        dto.setStatus(rent.getStatus().name());
        if (rent.getUser() != null) {
            UserDto userDto = new UserDto();
            userDto.setUserId(rent.getUser().getUserId());
            dto.setUser(userDto);
        }
        if (rent.getVehicle() != null) {
            VehicleDto vehicleDto = new VehicleDto();
            vehicleDto.setVehicleId(rent.getVehicle().getVehicleId());
            dto.setVehicle(vehicleDto);
        }
        if (rent.getDriver() != null) {
            DriverDto driverDto = new DriverDto();
            driverDto.setDriverId(rent.getDriver().getDriverId());
            dto.setDriver(driverDto);
        }
        return dto;
    }

    private RentDetails mapToEntity(RentDetailsDto dto) {
        RentDetails rent = new RentDetails();
        rent.setDate(dto.getDate());
        rent.setPickupDate(dto.getPickupDate());
        rent.setReturnDate(dto.getReturnDate());
        rent.setPayment(dto.getPayment());
        rent.setStatus(RentDetails.Status.PENDING);
        return rent;
    }

    @Override
    public RentDetailsDto createBooking(RentDetailsDto dto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicle().getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        if (!vehicle.getStatus().equals(Vehicle.Status.AVAILABLE)) {
            throw new IllegalArgumentException("Vehicle not available");
        }
        if (rentRepository.existsByVehicleAndStatusNotAndDateOverlap(
                vehicle, RentDetails.Status.CANCELLED, dto.getPickupDate(), dto.getReturnDate())) {
            throw new IllegalArgumentException("Vehicle booked for selected dates");
        }
        RentDetails rent = mapToEntity(dto);
        rent.setUser(user);
        rent.setVehicle(vehicle);
        vehicle.setStatus(Vehicle.Status.UNAVAILABLE);
        vehicleRepository.save(vehicle);
        RentDetails saved = rentRepository.save(rent);
        try {
            notificationService.sendEmailNotification(user.getUserId(),
                    "Booking Confirmation",
                    "Your booking for vehicle ID " + vehicle.getVehicleId() + " is pending approval.");
        } catch (Exception e) {

        }
        return mapToDto(saved);
    }

    @Override
    public List<RentDetailsDto> getAllBookings(){
        return rentRepository.findAll().stream()
                .map(this::mapToDto)
        .collect(Collectors.toList());
    }


    @Override
    public RentDetailsDto getBookingById(Long id) {
        return rentRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found !"));

    }

    @Override
    public RentDetailsDto cancelBooking(Long id){
        RentDetails rent = rentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("booking not found"));
        if (!rent.getStatus().equals(RentDetails.Status.PENDING)) {
            throw new IllegalArgumentException("Only pending bookings can be cancelled");
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!rent.getUser().getUserName().equals(username)) {
            throw new IllegalArgumentException("Not authorized to cancel this booking");
        }
        rent.setStatus(RentDetails.Status.CANCELLED);
        Vehicle vehicle = rent.getVehicle();
        vehicle.setStatus(Vehicle.Status.AVAILABLE);
        vehicleRepository.save(vehicle);
        RentDetails updated = rentRepository.save(rent);
        try {
            notificationService.sendEmailNotification(rent.getUser().getUserId(),
                    "Booking Cancelled",
                    "Your booking ID " + id + " has been cancelled.");
        } catch (Exception e) {
        }
        return mapToDto(updated);
    }

    @Override
    public RentDetailsDto approveBooking(Long id) {
     RentDetails rent = rentRepository.findById(id)
             .orElseThrow(() ->new IllegalArgumentException("Booking not found"));
        if (!rent.getStatus().equals(RentDetails.Status.PENDING)) {
            throw new IllegalArgumentException("Only pending bookings can be approved");
        }
        rent.setStatus(RentDetails.Status.APPROVED);
        RentDetails updated = rentRepository.save(rent);
        try {
            notificationService.sendEmailNotification(rent.getUser().getUserId(),
                    "Booking Approved",
                    "Your booking ID " + id + " has been approved.");
        } catch (Exception e) {
        }
        return mapToDto(updated);
    }

    @Override
    public RentDetailsDto rejectBooking(Long id) {
        RentDetails rent = rentRepository.findById(id)
                .orElseThrow(() ->new IllegalArgumentException("Booking not found"));
        if (!rent.getStatus().equals(RentDetails.Status.PENDING)) {
            throw new IllegalArgumentException("Only pending bookings can be rejected");
        }
        rent.setStatus(RentDetails.Status.CANCELLED);
        Vehicle vehicle = rent.getVehicle();
        vehicle.setStatus(Vehicle.Status.AVAILABLE);
        vehicleRepository.save(vehicle);
        RentDetails updated = rentRepository.save(rent);
        try {
            notificationService.sendEmailNotification(rent.getUser().getUserId(),
                    "Booking Rejected",
                    "Your booking ID " + id + " has been rejected.");
        } catch (Exception e) {

        }
        return mapToDto(updated);
    }

    @Override
    public RentDetailsDto assignDriver(Long bookingId, Long driverId) {
        RentDetails rent = rentRepository.findById(bookingId)
                .orElseThrow(() ->new IllegalArgumentException("Booking not found"));
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found"));
        if (!driver.getStatus().equals(Driver.Status.AVAILABLE)) {
            throw new IllegalArgumentException("Driver not available");
        }
        rent.setDriver(driver);
        driver.setStatus(Driver.Status.BUSY);
        driverRepository.save(driver);
        RentDetails updated = rentRepository.save(rent);
        try {
            notificationService.sendEmailNotification(rent.getUser().getUserId(),
                    "Driver Assigned",
                    "Driver ID " + driverId + " has been assigned to your booking ID " + bookingId + ".");
        } catch (Exception e) {
        }
        return mapToDto(updated);
    }

    @Override
    public List<RentDetailsDto> getUserBookings() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return rentRepository.findByUser(user).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
