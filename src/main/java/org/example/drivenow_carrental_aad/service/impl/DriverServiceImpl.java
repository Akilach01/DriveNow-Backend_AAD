package org.example.drivenow_carrental_aad.service.impl;

import org.example.drivenow_carrental_aad.dto.DriverDto;
import org.example.drivenow_carrental_aad.service.DriverService;

import java.util.List;

public class DriverServiceImpl implements DriverService {
    @Override
    public DriverDto createDriver(DriverDto driverDto) {
        return null;
    }

    @Override
    public DriverDto updateDriver(Long id, DriverDto driverDto) {
        return null;
    }

    @Override
    public DriverDto getDriverById(Long id) {
        return null;
    }

    @Override
    public List<DriverDto> getAllDrivers() {
        return List.of();
    }

    @Override
    public List<DriverDto> getAvailableDrivers() {
        return List.of();
    }

    @Override
    public void deleteDriver(Long id) {

    }
}
