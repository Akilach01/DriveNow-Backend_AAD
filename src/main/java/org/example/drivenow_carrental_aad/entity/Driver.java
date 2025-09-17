package org.example.drivenow_carrental_aad.entity;

import ch.qos.logback.core.status.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status{AVAILABLE, BUSY}
}
