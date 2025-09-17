package org.example.drivenow_carrental_aad.repo;

import org.example.drivenow_carrental_aad.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

  Role findByName(String name);
}
