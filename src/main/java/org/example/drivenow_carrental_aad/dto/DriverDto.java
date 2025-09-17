package org.example.drivenow_carrental_aad.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {
    private Long driverId;

    @NotBlank
    private String name;

    private String address;

    @NotBlank
    @Pattern(regexp = "\\d{9}[vV]|\\d{12}")
    private String nic;

   @NotBlank
   @Pattern(regexp = "\\+?\\d{10,15}")
    private String contact;

    private String status;
     @NotBlank
     @Email
    private String email;


}
