/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mail.service;

import com.example.mail.model.Message;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 *
 * @author mike
 */
@Service
public class MailService {

    private static final Logger LOG = LoggerFactory.getLogger(MailService.class);

    @Autowired
    //private MailSender mailSender;
    private JavaMailSender mailSender;

    @Autowired
    private SimpleMailMessage templateMessage;

    @Autowired
    private TemplateEngine templateEngine;

    public void send(final Message message, final Locale locale) throws MessagingException, IOException {
        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", "(unknown)");
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("accounts", Arrays.asList("(none)"));
        String[] vars = expandArray(message.getVar());
        List<String> formatValues = new ArrayList<String>();
        for (String var : vars) {
            LOG.debug("var="+var);
            String[] values = var.split(";");
            String[] nameValuePair = values[0].split(":");
            String name = nameValuePair[0];
            values[0] = nameValuePair[1];
            for (String val : values) {
                formatValues.add(val);
            }
            if (values.length > 1) {
                LOG.debug("name="+name+",values="+values);
                ctx.setVariable(name, Arrays.asList(values));
            } else {
                LOG.debug("name="+name+",value="+values[0]);
                ctx.setVariable(name, values[0]);
            }
        }
        
        MultipartFile[] attachments = message.getAttachment();
        MultipartFile inline = message.getInline();
        boolean multipart = (message.getHtml() != null || (attachments != null || inline != null));
        // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
        String template = "email-simple.html";
        if (inline != null) {
            ctx.setVariable("imageResourceName",  inline.getOriginalFilename()); // so that we can reference it from HTML
            template = "email-inlineimage.html";
        }
        
        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        LOG.debug("multipart="+multipart);
        final MimeMessageHelper messageHelper
                    = new MimeMessageHelper(mimeMessage, multipart, "UTF-8");
        messageHelper.setFrom(message.getFrom());
        messageHelper.setTo(expandArray(message.getTo()));
        if (message.getCc() != null) messageHelper.setCc(expandArray(message.getCc()));
        if (message.getBcc() != null) messageHelper.setBcc(expandArray(message.getBcc()));
        messageHelper.setSubject(message.getSubject());

        // Create the HTML body using Thymeleaf
        String plainText = message.getText();
        String htmlText = message.getHtml();
        if (htmlText != null) {
            LOG.debug("template="+template);
            final String htmlContent = this.templateEngine.process(template, ctx);
            if (plainText != null) {
                //messageHelper.setText(plainText, htmlText);
                messageHelper.setText(plainText, htmlContent);
            } else {
                //messageHelper.setText(htmlText, true /* isHtml */);
                messageHelper.setText(htmlContent, true /* isHtml */);
            }
        } else {
            messageHelper.setText(String.format(locale, plainText, formatValues.get(0), formatValues.get(1)));
        }

        // Add the attachment
        if (attachments != null) {
            for (MultipartFile attachment : attachments) {
                final InputStreamSource attachmentSource
                        = new ByteArrayResource(attachment.getBytes());
                messageHelper.addAttachment(attachment.getOriginalFilename(),
                        attachmentSource, attachment.getContentType());                
            }
        }

        // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
        if (inline != null) {
            final InputStreamSource imageSource
                    = new ByteArrayResource(inline.getBytes());
            messageHelper.addInline(inline.getOriginalFilename(), imageSource,
                    inline.getContentType());
        }
        
        LOG.debug(message.toString());
        // Send email
        this.mailSender.send(mimeMessage);
    }
    
    private String[] expandArray(String[] addresses) {
        return expandArray(addresses, ",");
    }
    
    private String[] expandArray(String[] addresses, String sep) {
        if (sep == null) sep = ",";
        List<String> addressList = Arrays.asList(addresses);
        for (String address : addressList) {
            if (address.contains(sep)) {
                addressList.remove(address);
                addressList.addAll(Arrays.asList(address.split(sep)));
            }
        }
        return addressList.toArray(new String[addressList.size()]);    
    }

}
