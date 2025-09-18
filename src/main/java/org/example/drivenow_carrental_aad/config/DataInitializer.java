package org.example.drivenow_carrental_aad.config;

import org.example.drivenow_carrental_aad.entity.Role;
import org.example.drivenow_carrental_aad.entity.User;
import org.example.drivenow_carrental_aad.entity.Vehicle;
import org.example.drivenow_carrental_aad.repo.RoleRepository;
import org.example.drivenow_carrental_aad.repo.UserRepository;
import org.example.drivenow_carrental_aad.repo.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Collections;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, VehicleRepository vehicleRepository){
        return args -> {
            Role userRole = new Role(null, "ROLE_USER");
            Role adminRole = new Role(null, "ROLE_ADMIN");
            roleRepository.save(userRole);
            roleRepository.save(adminRole);

            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setAddress("123 Admin St");
            admin.setContact("+1234567890");
            admin.setNicNo("123456789V");
            admin.setUserName("admin");
            admin.setEmail("admin@drivenow.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setStatus(User.Status.ACTIVE);
            admin.setRoles(Collections.singleton(adminRole));
            userRepository.save(admin);

            User user = new User();
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setAddress("456 User St");
            user.setContact("+0987654321");
            user.setNicNo("987654321V");
            user.setUserName("john");
            user.setEmail("john@drivenow.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setStatus(User.Status.ACTIVE);
            user.setRoles(Collections.singleton(userRole));
            userRepository.save(user);

            Vehicle vehicle = new Vehicle();
            vehicle.setModel("Toyota Corolla");
            vehicle.setYear(2020);
            vehicle.setCategory("Sedan");
            vehicle.setColour("White");
            vehicle.setImageUrl("http://example.com/corolla.jpg");
            vehicle.setFuelType("Petrol");
            vehicle.setLicensePlate("ABC123");
            vehicle.setSeats(5);
            vehicle.setRentPrice(new BigDecimal("50.00"));
            vehicle.setStatus(Vehicle.Status.AVAILABLE);
            vehicleRepository.save(vehicle);
         };
      }
    }

