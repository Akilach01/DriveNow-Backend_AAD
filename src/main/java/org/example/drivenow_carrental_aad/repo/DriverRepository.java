package org.example.drivenow_carrental_aad.repo;

import org.example.drivenow_carrental_aad.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver,String> {
}
