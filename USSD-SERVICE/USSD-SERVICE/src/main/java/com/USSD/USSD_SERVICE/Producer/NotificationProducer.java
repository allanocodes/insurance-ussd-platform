package com.USSD.USSD_SERVICE.Producer;

import com.USSD.USSD_SERVICE.config.RabbitMqConfiguration;
import com.USSD.USSD_SERVICE.dto.NotificationRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendNotificationRequest(NotificationRequest request){
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.notificationExchange,
                RabbitMqConfiguration.notificationBindingKey,request);
    }

}
