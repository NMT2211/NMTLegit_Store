package com.jpa.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetPasswordEmail(String toEmail, String resetUrl) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject("Đặt lại mật khẩu của bạn");
        helper.setText("<p>Chào bạn,</p>"
                     + "<p>Bạn đã yêu cầu đặt lại mật khẩu. Nhấn vào liên kết dưới đây:</p>"
                     + "<p><a href=\"" + resetUrl + "\">Đặt lại mật khẩu</a></p>"
                     + "<p>Liên kết này có hiệu lực trong 15 phút.</p>", true);

        mailSender.send(message);
    }
}
