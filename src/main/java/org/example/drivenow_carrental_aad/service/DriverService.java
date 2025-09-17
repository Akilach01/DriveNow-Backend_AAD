package org.example.drivenow_carrental_aad.service;

import org.example.drivenow_carrental_aad.dto.DriverDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DriverService {

    DriverDto createDriver(DriverDto driverDto);
    DriverDto updateDriver(Long id, DriverDto driverDto);
    DriverDto getDriverById(Long id);
    List<DriverDto> getAllDrivers();
    List<DriverDto> getAvailableDrivers();
    void deleteDriver(Long id);
}
