package org.example.drivenow_carrental_aad.repo;

import org.example.drivenow_carrental_aad.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

  List<Vehicle> findByStatus(Vehicle.Status status);
}
