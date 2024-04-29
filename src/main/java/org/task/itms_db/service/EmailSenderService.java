package org.task.itms_db.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.task.itms_db.jwt.JwtService;

@Service
public class EmailSenderService {

    private final JavaMailSender sender;
    private final JwtService service;
    private static final String BASE_URL = "http://localhost:8080/admin/mail/";

    public EmailSenderService(JavaMailSender sender, JwtService service) {
        this.sender = sender;
        this.service = service;
    }

    public void sendMessage(String to, String subject, String text, Long employeeId, Long feedbackId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text + " "  + generateLink(employeeId, feedbackId));
        sender.send(message);
    }

    public String generateLink(Long employeeId, Long feedbackId) {
        return  BASE_URL + service.generateTokenWithUsernameAndPassword(employeeId, feedbackId);
    }
}
