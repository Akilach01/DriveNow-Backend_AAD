package org.example.drivenow_carrental_aad.service.impl;

import org.example.drivenow_carrental_aad.dto.DriverDto;
import org.example.drivenow_carrental_aad.entity.Driver;
import org.example.drivenow_carrental_aad.repo.DriverRepository;
import org.example.drivenow_carrental_aad.service.DriverService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    public DriverServiceImpl(DriverRepository driverRepository){
        this.driverRepository = driverRepository;
    }

    private DriverDto mapToDto(Driver driver){
        DriverDto dto = new DriverDto();
        dto.setDriverId(driver.getDriverId());
        dto.setName(driver.getName());
        dto.setAddress(driver.getAddress());
        dto.setNic(driver.getNic());
        dto.setContact(driver.getContact());
        dto.setStatus(driver.getStatus().name());
        dto.setEmail(driver.getEmail());
        return dto;
    }

    private Driver mapToEntity(DriverDto dto){
        Driver driver = new Driver();
        driver.setDriverId(dto.getDriverId());
        driver.setName(dto.getName());
        driver.setAddress(dto.getAddress());
        driver.setNic(dto.getNic());
        driver.setContact(dto.getContact());
        driver.setEmail(dto.getEmail());
        if (dto.getStatus() != null) {
            driver.setStatus(Driver.Status.valueOf(dto.getStatus()));
        }
        return driver;
    }

    @Override
    public DriverDto createDriver(DriverDto dto) {
        Driver driver = mapToEntity(dto);
        driver.setStatus(Driver.Status.AVAILABLE);
        Driver saved = driverRepository.save(driver);
        return mapToDto(saved);
    }

    @Override
    public DriverDto updateDriver(Long id, DriverDto dto) {
        Driver driver = driverRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Driver not found"));
        driver.setName(dto.getName());
        driver.setAddress(dto.getAddress());
        driver.setNic(dto.getNic());
        driver.setContact(dto.getContact());
        driver.setEmail(dto.getEmail());
        driver.setStatus(Driver.Status.valueOf(dto.getStatus()));
        return mapToDto(driverRepository.save(driver));

    }

    @Override
    public DriverDto getDriverById(Long id) {
        return driverRepository.findById(id).map(this::mapToDto)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found"));

    }

    @Override
    public List<DriverDto> getAllDrivers() {
        return driverRepository.findAll().stream().map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DriverDto> getAvailableDrivers() {
      return driverRepository.findByStatus(Driver.Status.AVAILABLE).stream()
              .map(this::mapToDto)
              .collect(Collectors.toList());

    }

    @Override
    public void deleteDriver(Long id) {
     driverRepository.deleteById(id);
    }
}
