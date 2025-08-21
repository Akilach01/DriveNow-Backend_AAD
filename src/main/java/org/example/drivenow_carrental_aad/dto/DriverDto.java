package org.example.drivenow_carrental_aad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {
    private String driverId;
    private String name;
    private String address;
    private String nic;
    private String contact;
    private String status;

}
