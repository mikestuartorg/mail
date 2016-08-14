/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mail.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {
    @Value("${email.host}")
    private String host;

    @Value("${email.port}")
    private int port;

    @Value("${email.template.from}")
    private String from;

    @Value("${email.template.subject}")
    private String subject;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
//        javaMailSender.setUsername(userName);
//        javaMailSender.setPassword(password);
//        javaMailSender.setDefaultEncoding(defaultEncoding);
//        Properties properties = new Properties();
//        properties.put("mail.smtp.auth", false);
//        properties.put("mail.smtp.starttls.enable", false);
//        javaMailSender.setJavaMailProperties(properties);
        //javaMailSender.setProtocol(null);
        return javaMailSender;
    }

    @Bean
    public SimpleMailMessage simpleMailMessage() {
       SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
       simpleMailMessage.setFrom(from);
       simpleMailMessage.setSubject(subject);
       return simpleMailMessage;
    }
}
