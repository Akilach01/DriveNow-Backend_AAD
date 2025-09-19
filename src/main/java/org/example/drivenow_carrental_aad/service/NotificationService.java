package org.example.drivenow_carrental_aad.service;

import org.example.drivenow_carrental_aad.entity.Notification;
import org.example.drivenow_carrental_aad.entity.User;
import org.example.drivenow_carrental_aad.repo.NotificationRepository;
import org.example.drivenow_carrental_aad.repo.UserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
    public void sendEmailNotification(Long userId, String message, String subject) throws MessagingException {
       User user = userRepository.findById(userId)
               .orElseThrow(() ->new IllegalArgumentException("User not found"));
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setType(Notification.Type.EMAIL);
        notificationRepository.save(notification);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(message, true);
        mailSender.send(mimeMessage);

    }
}
