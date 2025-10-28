package com.USSD.USSD_SERVICE.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMqConfiguration {

    public static String notificationQueue = "NotificationQueue";
    public static String notificationExchange = "NotificationExchange";
    public static String notificationBindingKey = "NotificationBindingKey";

    public static final String paymentQueue = "paymentQueue";
    public static String paymentExchange = "paymentExchange";
    public static String paymentExchangeKey = "paymentExchangeKey";


    @Bean
    public Queue queue2(){
        return new Queue(paymentQueue,true);
    }
    @Bean
    public TopicExchange exchange2(){
        return new TopicExchange(paymentExchange);
    }
    @Bean
    public Binding binding2(){
        return BindingBuilder.bind(queue2())
                .to(exchange2())
                .with(paymentExchangeKey);
    }


    @Bean
    public Queue queue(){
        return  new Queue(notificationQueue,true);
    }

    @Bean
    public TopicExchange exchange(){
        return  new TopicExchange(notificationExchange);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(notificationBindingKey);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
