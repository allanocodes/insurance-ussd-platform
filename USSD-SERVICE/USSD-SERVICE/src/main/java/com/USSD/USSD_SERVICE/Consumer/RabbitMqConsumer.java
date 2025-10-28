package com.USSD.USSD_SERVICE.Consumer;

import com.USSD.USSD_SERVICE.Entity.Payment;
import com.USSD.USSD_SERVICE.Entity.Policy;
import com.USSD.USSD_SERVICE.Entity.Quotation;
import com.USSD.USSD_SERVICE.Producer.NotificationProducer;
import com.USSD.USSD_SERVICE.config.RabbitMqConfiguration;
import com.USSD.USSD_SERVICE.dto.NotificationRequest;
import com.USSD.USSD_SERVICE.repository.PaymentRepository;
import com.USSD.USSD_SERVICE.repository.QuotationRepo;
import com.USSD.USSD_SERVICE.service.InsuranceService;
import com.USSD.USSD_SERVICE.service.NotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class RabbitMqConsumer {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    QuotationRepo quotationRepo;

    @Autowired
    InsuranceService insuranceService;

    @Autowired
    NotificationMessage notificationMessage;

    @Autowired
    NotificationProducer producer;

    private static final Logger logger= LoggerFactory.getLogger(RabbitMqConsumer.class);

    @RabbitListener(queues = RabbitMqConfiguration.paymentQueue)
    public void getPaymentMessage(Payment payment){
        logger.info("USSD service received Payment {}",payment);
        Optional<Payment> paymentOptional = paymentRepository.findByMerchantId(payment.getMerchantRequestId());

        Payment payment1 = paymentOptional.orElse(null);
        String quotationNumber = "";
        
        if(payment1 != null){
            payment1.setAmount(payment.getAmount());
            payment1.setResponseDesc(payment.getResponseDesc());
            payment1.setStatus(payment.getStatus());
            payment1.setTransactionDate(payment.getTransactionDate());
            payment1.setMpesaReceiptNumber(payment.getMpesaReceiptNumber());

            quotationNumber = payment1.getQuotationNumber();

            paymentRepository.save(payment1);

        }
        Optional<Quotation> quotationOptional = Optional.empty();

        if(!quotationNumber.isEmpty()) {
           quotationOptional = quotationRepo.findById(quotationNumber);
        }

        Quotation quotation= quotationOptional.orElseGet(()-> null);

        if(quotation != null){
            if(quotation.getPremium().compareTo(BigDecimal.valueOf(payment.getAmount())) > 0 ){
                // insufficient amount
                String message=  notificationMessage.insufficientPayment(quotation);
                NotificationRequest request = NotificationRequest.builder()
                        .id(quotationNumber)
                        .email(quotation.getPolicyHolder().getEmail())
                        .phoneNumber(quotation.getPolicyHolder().getPhoneNumber())
                        .message(message)
                        .build();
                producer.sendNotificationRequest(request);
            }
            else{
            Policy policy= insuranceService.awardPolicy(payment.getAmount(),quotation).getBody().getData();
            String message=  notificationMessage.sendPolicyMessage(policy);
            NotificationRequest request = NotificationRequest.builder()
                    .id(policy.getPolicyNumber())
                    .email(policy.getPolicyHolder().getEmail())
                    .phoneNumber(policy.getPolicyHolder().getPhoneNumber())
                    .message(message)
                    .build();
            producer.sendNotificationRequest(request);

            }
        }



    }
}
