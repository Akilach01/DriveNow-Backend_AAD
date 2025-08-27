package org.example.drivenow_carrental_aad.repo;

import org.example.drivenow_carrental_aad.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
}
