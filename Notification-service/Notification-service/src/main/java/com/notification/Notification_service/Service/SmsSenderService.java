package com.notification.Notification_service.Service;

import com.africastalking.SmsService;
import com.africastalking.sms.Recipient;
import com.notification.Notification_service.Exception.EmailNotSentException;
import com.notification.Notification_service.Exception.SmsNotSendException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsSenderService {
    @Autowired
    SmsService smsService;

    private final Logger logger = LoggerFactory.getLogger(SmsSenderService.class);

    public void sendSms(String message,String phoneNumber){

        try {
            List<Recipient> response = smsService.send(message, new String[]{phoneNumber}, true);
            response.forEach(r->logger.info("Sent to " + r.number + " Status: " + r.status));
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new SmsNotSendException("sms not sent",e.getCause());
        }
    }
}
