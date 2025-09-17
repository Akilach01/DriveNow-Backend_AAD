package org.example.drivenow_carrental_aad.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class VehicleDto {

    private Long vehicleId;

    @NotBlank
    private String model;

@Min(1900) @Max(2025)
    private int year;

    @NotBlank
    private String category;

    @NotBlank
    private String colour;

    private String imageUrl;

    @NotBlank
    private String fuelType;

    @NotBlank
    private String licensePlate;

    @Positive
    private int seats;

    @Positive
    private BigDecimal rentPrice;

    private String status;
}
