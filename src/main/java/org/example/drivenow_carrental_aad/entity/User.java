package org.example.drivenow_carrental_aad.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
public class User {
    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String address;
    private int contact;
    private String nicNo;
    private String userName;
    private String email;
    private String password;
    private String status;
}
