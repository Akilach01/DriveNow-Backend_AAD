package org.example.drivenow_carrental_aad.repo;

import org.example.drivenow_carrental_aad.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, String> {
}
