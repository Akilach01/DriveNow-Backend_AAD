package org.example.drivenow_carrental_aad.repo;

import org.example.drivenow_carrental_aad.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
