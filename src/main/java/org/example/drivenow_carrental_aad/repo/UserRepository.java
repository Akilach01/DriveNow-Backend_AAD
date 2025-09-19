package org.example.drivenow_carrental_aad.repo;

import org.example.drivenow_carrental_aad.dto.UserDto;
import org.example.drivenow_carrental_aad.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
//    User findByUserName(String username);

    Optional<User> findByUserName(String username);
}
