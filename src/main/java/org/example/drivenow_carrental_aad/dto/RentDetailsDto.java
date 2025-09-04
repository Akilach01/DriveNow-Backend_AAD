package org.example.drivenow_carrental_aad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentDetailsDto {
    private Long rentId;
    private String date;
    private String pickupDate;
    private String returnDate;
    private String status;
    private String payment;

    private VehicleDto vehicle;
    private UserDto user;
    private DriverDto driver;
}
