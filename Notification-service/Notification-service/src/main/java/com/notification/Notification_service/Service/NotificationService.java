package com.notification.Notification_service.Service;

import com.notification.Notification_service.Dto.NotificationRequest;
import com.notification.Notification_service.Entity.Payment;
import com.notification.Notification_service.Entity.Policy;
import com.notification.Notification_service.Entity.Quotation;
import com.notification.Notification_service.Exception.EmailNotSentException;
import com.notification.Notification_service.Repo.PaymentRepo;
import com.notification.Notification_service.Repo.PolicyRepo;
import com.notification.Notification_service.Repo.QuotationRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class NotificationService {

    private final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    EmailService emailService;

    @Autowired
    SmsSenderService smsSenderService;

    @Autowired
    QuotationRepo quotationRepo;

    @Autowired
    PaymentRepo paymentRepo;
    @Autowired
    PolicyRepo policyRepo;

  public void sendQuotationMessages(NotificationRequest request){

      Optional<Quotation> quotationOptional =  quotationRepo.findById(request.getId());

      Quotation quotation = quotationOptional.orElse(null);

      if(quotation != null) {
          smsSenderService.sendSms(request.getMessage(), request.getPhoneNumber());
          quotation.setSmsSent(true);
          quotationRepo.save(quotation);
      }

  }


  public void sendPaymentMessages(NotificationRequest request){
      Optional<Payment> paymentOptional = paymentRepo.findById(request.getId());

      Payment payment = paymentOptional.orElse(null);

      if(payment != null){
          smsSenderService.sendSms(request.getMessage(),request.getPhoneNumber());
          payment.setSms_sent(true);
          paymentRepo.save(payment);
      }

  }


  public void sendPolicyMessages(NotificationRequest request){
      Optional<Policy> policyOptional = policyRepo.findById(request.getId());
      logger.info("email: "+ request.getId());
      Policy policy = policyOptional.orElse(null);
      if(policy != null){
          emailService.sendEmail(request.getEmail(),request.getMessage(),"POLICY NOTIFICATION");
          logger.info("email: "+ request.getEmail());
          policy.setEmailSent(true);
          policyRepo.save(policy);
      }
  }
}
