package org.example.drivenow_carrental_aad.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.drivenow_carrental_aad.dto.DriverDto;
import org.example.drivenow_carrental_aad.dto.UserDto;
import org.example.drivenow_carrental_aad.dto.VehicleDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class RentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String rentId;
    private String date;
    private String pickupDate;
    private String returnDate;
    private String status;
    private String payment;

}
