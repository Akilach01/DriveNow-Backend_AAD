package org.example.drivenow_carrental_aad.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {

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
    @Pattern(regexp = "\\\\d{9}[vV]|\\\\d{12}")
    private String nicNo;

    @NotBlank
    private String userName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;
}
