package org.example.drivenow_carrental_aad.entity;

import ch.qos.logback.core.status.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.drivenow_carrental_aad.dto.DriverDto;
import org.example.drivenow_carrental_aad.dto.UserDto;
import org.example.drivenow_carrental_aad.dto.VehicleDto;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class RentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentId;

    @NotNull
    private LocalDate date;

    @NotNull
    @FutureOrPresent
    private LocalDate pickupDate;

    @NotNull
    @Future
    private LocalDate returnDate;

@Enumerated(EnumType.STRING)
    private Status status;

@Positive
    private BigDecimal payment;

    //vehicle
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    //user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //driver
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    public enum Status{PENDING, APPROVED, CANCELLED}

}
