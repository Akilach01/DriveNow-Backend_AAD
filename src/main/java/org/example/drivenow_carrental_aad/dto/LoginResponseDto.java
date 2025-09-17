package org.example.drivenow_carrental_aad.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    @NotBlank
    private String username;
    @NotBlank
    private String token;

    private Set<String> roles;
}
