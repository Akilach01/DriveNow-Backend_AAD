package org.example.drivenow_carrental_aad.repo;

import jakarta.validation.constraints.NotBlank;
import org.example.drivenow_carrental_aad.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

  List<Vehicle> findByStatus(Vehicle.Status status);

  Optional<Vehicle> findByLicensePlate(@NotBlank String licensePlate);
}
