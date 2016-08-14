package com.example.mail;

import com.example.mail.config.MailConfiguration;
import com.example.mail.config.ThymeleafConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MailConfiguration.class, ThymeleafConfiguration.class})
@EnableAutoConfiguration(exclude = {ThymeleafAutoConfiguration.class})
@ComponentScan
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
