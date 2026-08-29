package com.mcmanuel.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine engine;


    @Async
    public void sendEmail(String email,String otp) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,MULTIPART_MODE_MIXED,"UTF-8");

        helper.setTo(email);
        helper.setFrom("mcmanuel755@gmail.com");
        helper.setSubject("Verification");

        Map<String, Object> properties = new HashMap<>();

        properties.put("OTP",otp);
        Context context = new Context();
        context.setVariables(properties);

        String template =engine.process("index.html",context);

        helper.setText(template,true);

        mailSender.send(message);
    }
}
