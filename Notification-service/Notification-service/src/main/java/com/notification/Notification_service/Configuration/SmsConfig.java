package com.notification.Notification_service.Configuration;

import com.africastalking.AfricasTalking;
import com.africastalking.SmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {

    @Value("${africastalking.username}")
    private String username;

    @Value("${africastalking.apiKey}")
    private String apiKey;

    @Bean
    public SmsService smsService(){
        AfricasTalking.initialize(username,apiKey);
        return AfricasTalking.getService(AfricasTalking.SERVICE_SMS);
    }
}
