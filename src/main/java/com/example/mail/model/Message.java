/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.mail.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author mike
 */
@Data
public class Message {
    // if make any of these final, lombok would create a constructor with as arg
    private String from;
    private String[] to;
    private String[] cc;
    private String[] bcc;
    private String subject;
    private String text;
    private String html;
    private MultipartFile[] attachment;
    private MultipartFile inline;
    
    //private String replyTo;
    //private Date sentDate;
    
    // make generic params for text/html substitution
    private String[] var;  
}
