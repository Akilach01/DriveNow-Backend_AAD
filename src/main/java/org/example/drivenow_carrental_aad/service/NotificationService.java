package org.example.drivenow_carrental_aad.service;

import org.example.drivenow_carrental_aad.entity.Notification;
import org.example.drivenow_carrental_aad.entity.User;
import org.example.drivenow_carrental_aad.repo.NotificationRepository;
import org.example.drivenow_carrental_aad.repo.UserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository, JavaMailSender mailSender) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    @Async
    public void sendEmailNotification(Long userId, String subject, String message) throws MessagingException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Save notification to database
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setType(Notification.Type.EMAIL);
        notificationRepository.save(notification);

        // Send email
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Log the error but don't throw it to prevent failing the main operation
            System.err.println("Failed to send email to " + user.getEmail() + ": " + e.getMessage());
            throw e; // Re-throw if you want to handle it at higher level
        }
    }
}
