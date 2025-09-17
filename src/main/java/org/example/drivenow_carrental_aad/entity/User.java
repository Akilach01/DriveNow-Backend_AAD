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

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @NotBlank
    @Column(unique = true)
    private String userName;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;
    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
           name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public enum Status{ACTIVE, BLOCKED}


}
