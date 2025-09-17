package org.example.drivenow_carrental_aad.repo;

import org.example.drivenow_carrental_aad.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver,Long> {
    List<Driver> findByStatus(Driver.Status status);
}
