package org.example.drivenow_carrental_aad.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentDetailsDto {

    private Long rentId;
    @NotNull
    private LocalDate date;

    @NotNull
    @FutureOrPresent
    private LocalDate pickupDate;

    @NotNull
    @Future
    private LocalDate returnDate;

    private String status;

    @Positive
    private BigDecimal payment;

    @NotNull
    private VehicleDto vehicle;

    private UserDto user;
    private DriverDto driver;
}
