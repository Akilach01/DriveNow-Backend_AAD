package org.example.drivenow_carrental_aad.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
public class Vehicle {
    @Id
    private String vehicleId;
    private String model;
    private String year;
    private String category;
    private String colour;
    private String sideView;
    private String fuelType;
    private String licensePlate;
    private int seats;
    private double rentPrice;
    private String status;
}
