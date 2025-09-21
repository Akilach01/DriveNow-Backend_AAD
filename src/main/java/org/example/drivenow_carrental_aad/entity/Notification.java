package org.example.drivenow_carrental_aad.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Notification {

  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  @NotBlank
  private String message;

  @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

  @Enumerated(EnumType.STRING)
    private NotificationType type;

  private LocalDateTime sentAt =LocalDateTime.now();

  @Column(name = "read", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
  private boolean read;

  public enum NotificationType{EMAIL,IN_APP}
}
