package org.example.drivenow_carrental_aad.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {

    private Long userId;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String address;

    @NotBlank
    @Pattern(regexp = "\\+?\\d{10,15}")
    private String contact;

    @NotBlank
    @Pattern(regexp = "\\d{9}[vV]|\\d{12}")
    private String nicNo;

    private String userName;

    @Email
    @NotBlank
    private String email;

    @Size(min = 6)
    private String password;

    private String status;

    private Set<String> roles =new HashSet<>();
}
