/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.mail.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author mike
 */
@ConfigurationProperties(prefix="mail.smtp")
public class JavaMailProperties {
    private boolean auth = false;
    
    private boolean starttls_enable = false;

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public boolean isStarttls_enable() {
        return starttls_enable;
    }

    public void setStarttls_enable(boolean starttls_enable) {
        this.starttls_enable = starttls_enable;
    }

}
