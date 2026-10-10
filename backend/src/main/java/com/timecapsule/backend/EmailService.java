package com.timecapsule.backend;

import java.io.File;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendCapsuleEmail(
            String recipientEmail,
            String title,
            String message,
            String photoPath) throws Exception {

        MimeMessage mail = mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(mail, true);

        helper.setTo(recipientEmail);
        helper.setSubject("Your TimeCapsule: " + title);
        helper.setText(
                "Your TimeCapsule is ready!\n\n" +
                "Title: " + title + "\n\n" +
                message
        );

        if (photoPath != null && !photoPath.isBlank()) {
            File photoFile = new File(photoPath);

            if (photoFile.exists() && photoFile.isFile()) {
                helper.addAttachment(
                        photoFile.getName(),
                        new FileSystemResource(photoFile)
                );
            }
        }

        mailSender.send(mail);
    }
}