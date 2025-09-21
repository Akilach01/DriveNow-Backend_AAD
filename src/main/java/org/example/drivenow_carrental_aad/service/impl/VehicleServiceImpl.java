package org.example.drivenow_carrental_aad.service.impl;

import org.example.drivenow_carrental_aad.dto.VehicleDto;
import org.example.drivenow_carrental_aad.entity.Vehicle;
import org.example.drivenow_carrental_aad.repo.VehicleRepository;
import org.example.drivenow_carrental_aad.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public VehicleDto createVehicle(VehicleDto vehicleDto) {
        // Check if vehicle already exists by license plate
        if (vehicleRepository.findByLicensePlate(vehicleDto.getLicensePlate()).isPresent()) {
            throw new IllegalArgumentException("Vehicle with license plate " + vehicleDto.getLicensePlate() + " already exists");
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setModel(vehicleDto.getModel());
        vehicle.setYear(vehicleDto.getYear());
        vehicle.setCategory(vehicleDto.getCategory());
        vehicle.setColour(vehicleDto.getColour());
        vehicle.setImageUrl(vehicleDto.getImageUrl());
        vehicle.setFuelType(vehicleDto.getFuelType());
        vehicle.setLicensePlate(vehicleDto.getLicensePlate());
        vehicle.setSeats(vehicleDto.getSeats());
        vehicle.setRentPrice(vehicleDto.getRentPrice());  // ✅ Fixed: Direct BigDecimal assignment
        vehicle.setStatus(Vehicle.Status.AVAILABLE);  // ✅ Fixed: String → Enum

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return convertToDto(savedVehicle);
    }

    @Override
    public VehicleDto updateVehicle(Long id, VehicleDto vehicleDto) {
        Vehicle existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id: " + id));

        // Check if license plate is being changed to one that already exists
        if (!existingVehicle.getLicensePlate().equals(vehicleDto.getLicensePlate()) &&
                vehicleRepository.findByLicensePlate(vehicleDto.getLicensePlate()).isPresent()) {
            throw new IllegalArgumentException("License plate " + vehicleDto.getLicensePlate() + " is already in use");
        }

        // Update all fields
        existingVehicle.setModel(vehicleDto.getModel());
        existingVehicle.setYear(vehicleDto.getYear());
        existingVehicle.setCategory(vehicleDto.getCategory());
        existingVehicle.setColour(vehicleDto.getColour());
        existingVehicle.setImageUrl(vehicleDto.getImageUrl());
        existingVehicle.setFuelType(vehicleDto.getFuelType());
        existingVehicle.setLicensePlate(vehicleDto.getLicensePlate());
        existingVehicle.setSeats(vehicleDto.getSeats());
        existingVehicle.setRentPrice(vehicleDto.getRentPrice());  // ✅ Fixed: Direct BigDecimal assignment

        // ✅ Fixed: Handle status properly (String from DTO → Enum in Entity)
        if (vehicleDto.getStatus() != null) {
            try {
                existingVehicle.setStatus(Vehicle.Status.valueOf(vehicleDto.getStatus()));
            } catch (IllegalArgumentException e) {
                existingVehicle.setStatus(Vehicle.Status.AVAILABLE);  // Default fallback
            }
        }

        Vehicle updatedVehicle = vehicleRepository.save(existingVehicle);
        return convertToDto(updatedVehicle);
    }

    @Override
    public List<VehicleDto> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleDto> getAvailableVehicles() {
        return vehicleRepository.findByStatus(Vehicle.Status.AVAILABLE)  // ✅ Fixed: Enum parameter
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteVehicle(Long id) {
        // Check if vehicle exists
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id: " + id));

        vehicleRepository.delete(vehicle);
    }

    @Override
    public VehicleDto getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id: " + id));
        return convertToDto(vehicle);
    }

    /**
     * Convert Vehicle entity to VehicleDto
     */
    private VehicleDto convertToDto(Vehicle vehicle) {
        VehicleDto dto = new VehicleDto();
        dto.setVehicleId(vehicle.getVehicleId());
        dto.setModel(vehicle.getModel());
        dto.setYear(vehicle.getYear());
        dto.setCategory(vehicle.getCategory());
        dto.setColour(vehicle.getColour());
        dto.setImageUrl(vehicle.getImageUrl());
        dto.setFuelType(vehicle.getFuelType());
        dto.setLicensePlate(vehicle.getLicensePlate());
        dto.setSeats(vehicle.getSeats());
        dto.setRentPrice(vehicle.getRentPrice());  // ✅ Fixed: BigDecimal stays BigDecimal
        dto.setStatus(vehicle.getStatus().name());  // ✅ Fixed: Enum → String for DTO
        return dto;
    }
}