package com.spring.ecommerce.lvtn.service.IMPL;

import com.spring.ecommerce.lvtn.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class MailServiceIMPL implements MailService {
    @Autowired
    private final SpringTemplateEngine templateEngine;

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String myEmail;

    @Override
    public void sendMail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set thông tin email
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);  // true để hỗ trợ HTML trong email
            helper.setFrom(myEmail);  // Địa chỉ email gửi

            // Gửi email
            mailSender.send(message);
            System.out.println("Email đã được gửi thành công!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi gửi email: " + e.getMessage());
        }
    }

    @Override
    public void sendRegistrationConfirmMail(String to, String confirmLink, String firstname, String lastname) {
        String subject = "Xác nhận đăng ký tài khoản";

        // Tạo context và thêm biến confirmLink, firstname, lastname
        Context context = new Context();
        context.setVariable("confirmLink", confirmLink);
        context.setVariable("firstname", firstname);
        context.setVariable("lastname", lastname);

        // Render template thành chuỗi
        String text = templateEngine.process("registration-confirm", context);

        // Gửi mail
        sendMail(to, subject, text);
    }
}