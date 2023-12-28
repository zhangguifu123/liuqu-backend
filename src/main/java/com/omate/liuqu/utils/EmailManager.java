package com.omate.liuqu.utils;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;

public class EmailManager {
    private static final String BASE_URL = "http://localhost:8081";

    private static JavaMailSenderImpl sender;

    public static void init() {
        sender = new JavaMailSenderImpl();
        sender.setHost("sandbox.smtp.mailtrap.io");
        sender.setPort(25);
        sender.setUsername("3e3e49f740d374");
        sender.setPassword("93e7ba99d341af");

        Properties props = sender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
    }

    public static void sendEmail(String to, String subject, String content) throws Exception {
        content = content.replace("%BASE_URL%", BASE_URL);

        MimeMessage message = sender.createMimeMessage();
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(subject);
        message.setContent(content, "text/html; charset=utf-8");
        sender.send(message);
    }
}
