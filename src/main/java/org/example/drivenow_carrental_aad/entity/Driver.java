package org.example.drivenow_carrental_aad.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Driver {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String driverId;
    private String name;
    private String address;
    private String nic;
    private String contact;
    private String status;
    private String email;
    private String username;
    private String password;
}
