package org.example.drivenow_carrental_aad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminDto {
    private String adminId;
    private String username;
    private String password;
    private String email;
    private String name;
    private String contact;
}
