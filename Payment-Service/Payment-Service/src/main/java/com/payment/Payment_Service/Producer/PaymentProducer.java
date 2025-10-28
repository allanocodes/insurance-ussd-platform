package com.payment.Payment_Service.Producer;

import com.payment.Payment_Service.Config.RabbitmqConf;
import com.payment.Payment_Service.Entity.Payment;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendMessage(Payment payment){
        rabbitTemplate.convertAndSend(RabbitmqConf.paymentExchange,
                RabbitmqConf.paymentExchangeKey,payment);
    }
}
