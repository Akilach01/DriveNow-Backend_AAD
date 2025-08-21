package org.example.drivenow_carrental_aad.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class VehicleDto {
    private String vehicleId;
    private String model;
    private String year;
    private String category;
    private String licensePlate;
    private int seats;
    private double rentPrice;
    private String status;
}
