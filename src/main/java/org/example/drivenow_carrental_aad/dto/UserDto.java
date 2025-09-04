package org.example.drivenow_carrental_aad.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {

    private Long userId;
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
