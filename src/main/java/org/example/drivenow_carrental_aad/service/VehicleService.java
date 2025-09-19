package org.example.drivenow_carrental_aad.service;

import org.example.drivenow_carrental_aad.dto.VehicleDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VehicleService {

    VehicleDto createVehicle(VehicleDto vehicleDto);
    VehicleDto updateVehicle(Long id, VehicleDto vehicleDto);
    List<VehicleDto> getAllVehicles();
    List<VehicleDto> getAvailableVehicles();
    void deleteVehicle(Long id);

    VehicleDto getVehicleById(Long id);
}
