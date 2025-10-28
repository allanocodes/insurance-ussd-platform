package com.notification.Notification_service.Configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static String notificationQueue = "NotificationQueue";
    public static String notificationExchange = "NotificationExchange";
    public static String notificationBindingKey = "NotificationBindingKey";


    @Bean
    public Queue queue(){
       return new Queue(notificationQueue,true);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(notificationExchange);
    }

    @Bean
    public Binding binding(){
      return   BindingBuilder.bind(queue())
                .to(exchange())
                .with(notificationBindingKey);
    }

    @Bean
    MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
