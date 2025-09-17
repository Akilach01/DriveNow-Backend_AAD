package org.example.drivenow_carrental_aad.repo;

import org.example.drivenow_carrental_aad.entity.RentDetails;
import org.example.drivenow_carrental_aad.entity.User;
import org.example.drivenow_carrental_aad.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RentRepository extends JpaRepository<RentDetails, Long> {

    List<RentDetails> findByUser(User user);

    @Query("SELECT r FROM RentDetails r WHERE r.vehicle = :vehicle AND r.status != :status " +
            "AND (r.pickupDate <= :endDate AND r.returnDate >= :startDate)")
    boolean existsByVehicleAndStatusNotAndDateOverlap(Vehicle vehicle, RentDetails.Status status,
                                                      LocalDate startDate, LocalDate endDate);

}
