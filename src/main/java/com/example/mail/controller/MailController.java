package com.example.mail.controller;

import com.example.mail.model.Message;
import com.example.mail.model.MailResponse;
import com.example.mail.service.MailService;
import java.io.IOException;
import java.util.Locale;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mike Stuart
 */
@RestController
@RequestMapping("/messages")
public class MailController {

    private static final Logger LOG = LoggerFactory.getLogger(MailController.class);

    @Autowired
    private MailService mailService;

    @RequestMapping
    public MailResponse list() {
        MailResponse resp = new MailResponse();
        resp.setMessage("List");
        resp.setId(0);
        return resp;
    }

    @RequestMapping("{id}")
    public MailResponse view(@PathVariable("id") long id) {
        MailResponse resp = new MailResponse();
        resp.setMessage("View");
        resp.setId(id);
        return resp;
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(@ModelAttribute MimeMessage message) {
        return "messages/form";
    }

//    @RequestMapping(method = RequestMethod.POST)
//    public @ResponseBody MailResponse create(@Valid MimeMessage message, BindingResult result,
//            RedirectAttributes redirect) {
//        if (result.hasErrors()) {
//            return new MailResponse(1, "Failed. Sorry.");
//        }
//        return new MailResponse(1, "Queued. Thank you.");
//    }
    @RequestMapping(method = RequestMethod.POST)//, consumes = {"application/x-www-form-urlencoded"})
    public MailResponse send(Message message, Locale locale) {
//        if (message.getName() != null) {
//            if (message.getAttachment() != null) {
//                MultipartFile attach = message.getAttachment();
//                mailService.sendMimeMessageWithAttachment(message.getName(), message.getTo(),
//                        attach.getOriginalFilename(),
//                        attach.getBytes(), attach.getContentType(), locale);
//            } else if (message.getInline() != null) {
//                MultipartFile inline = message.getInline();
//                mailService.sendMimeMessageWithInLine(message.getName(), message.getTo(),
//                        inline.getOriginalFilename(),
//                        inline.getBytes(), inline.getContentType(), locale);
//            } else {
//                mailService.sendMimeMessage(message.getName(), message.getTo(), locale);
//            }
//        } else {
//            mailService.sendMessage(message.getFrom(), message.getTo(), message.getSubject(), message.getText());
//        }
        MailResponse resp = new MailResponse();
        try {
            mailService.send(message, locale);
            resp.setMessage("Sent");
            resp.setId(1);
        } catch (MessagingException mex) {
            resp.setMessage(mex.getLocalizedMessage());
        } catch (IOException ioex) {
            resp.setMessage(ioex.getLocalizedMessage());

        }
        return resp;
    }
}
