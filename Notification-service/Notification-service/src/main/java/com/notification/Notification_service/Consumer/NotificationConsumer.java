package com.notification.Notification_service.Consumer;

import com.notification.Notification_service.Configuration.RabbitConfig;
import com.notification.Notification_service.Dto.NotificationRequest;
import com.notification.Notification_service.Service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    NotificationService service;

    private final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

    public static final String notificationQueue = "NotificationQueue";

    @RabbitListener(queues = notificationQueue)
    public void getMessage(NotificationRequest request){
        service.sendQuotationMessages(request);
        logger.info("Notification service received message {}",request);
    }
}
