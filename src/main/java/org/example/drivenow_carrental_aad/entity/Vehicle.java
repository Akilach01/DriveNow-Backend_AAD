package org.example.drivenow_carrental_aad.entity;

import ch.qos.logback.core.status.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    @NotBlank
    private String model;
   @Min(1900)@Max(2025)
    private int year;
    @NotBlank
    private String category;
    @NotBlank
    private String colour;
    private String imageUrl;
    @NotBlank
    private String fuelType;
    @NotBlank
    @Column(unique = true)
    private String licensePlate;
    @Positive
    private int seats;
    @Positive
    private BigDecimal rentPrice;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status{AVAILABLE, UNAVAILABLE}
}
