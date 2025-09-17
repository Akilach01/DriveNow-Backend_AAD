package org.example.drivenow_carrental_aad.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequestDto {

    @NotBlank
    private String usernameOrEmail;
    @NotBlank
    private String password;

}
