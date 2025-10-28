package com.notification.Notification_service.Service;

import com.notification.Notification_service.Exception.EmailNotSentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;


    public void sendEmail(String to,String text,String subject){

        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("allanwrites4u@gmail.com");
            simpleMailMessage.setTo(to);
            simpleMailMessage.setText(text);
            simpleMailMessage.setSubject(subject);

            javaMailSender.send(simpleMailMessage);

        }catch (MailException e){
            throw new EmailNotSentException(e.getMessage(),e.getCause());
        }

    }

}
