package org.example.drivenow_carrental_aad.repo;

import org.example.drivenow_carrental_aad.entity.RentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnRepository extends JpaRepository<RentDetails, String> {
}
