package com.USSD.USSD_SERVICE.service;

import com.USSD.USSD_SERVICE.Entity.Payment;
import com.USSD.USSD_SERVICE.Entity.Quotation;
import com.USSD.USSD_SERVICE.Producer.NotificationProducer;
import com.USSD.USSD_SERVICE.dto.*;
import com.USSD.USSD_SERVICE.helper.ParameterSetter;
import com.USSD.USSD_SERVICE.repository.PaymentRepository;
import com.USSD.USSD_SERVICE.repository.QuotationRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.collections.CaseInsensitiveKeyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
public class SessionStateService {

    @Autowired
    SessionService sessionService;

    @Autowired
    QuotationRepo quotationRepo;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    NotificationMessage message;

    @Autowired
    ParameterSetter parameterSetter;

    @Autowired
    InsuranceService insuranceService;

    @Autowired
    NotificationProducer producer;

    @Autowired
    PaymentService paymentService;

    private static final Logger logger =LoggerFactory.getLogger(SessionStateService.class);


    public String produceReturnMessage(String trimmedText,UssdSession session) {



        Map<String, String> attribute = session.getAttribute();
        Quotation quotation2;

        if (session.getState().equals("NEW")) {
            session.setState("MAIN_MENU");
            sessionService.save(session);
            return "CON Insurance Main Menu\n1. Get Quote\n2. Make Payment \n3. Check Status";
        }

        switch (session.getState()) {
            case "MAIN_MENU":
                if (trimmedText.equals("1")) {
                    session.setState("QUOTE_POLICY_TYPE");
                    sessionService.save(session);
                    return "CON Policy Type\n1. Third Party\n2. Comprehensive";
                } else if (trimmedText.equals("2")) {
                    session.setState("PAYMENT_POLICY_TYPE");
                    sessionService.save(session);
                    return "CON Policy Type\n1. Third Party\n2. Comprehensive";
                } else if (trimmedText.equals("3")) {
                    session.setState("STATUS");
                    sessionService.save(session);
                    return "CON Enter Policy Number";
                }
            case "QUOTE_POLICY_TYPE":
                if (trimmedText.equals("1")) {
                    session.setState("THIRD_PARTY_VEHICLE_REG");
                    sessionService.save(session);
                    return "CON Enter Vehicle Registation Number";
                } else if (trimmedText.equals("2")) {
                    session.setState("COMPREHENSIVE_VEHICLE_REG");
                    sessionService.save(session);
                    return "CON Enter Vehicle Registation Number";
                }
            case "THIRD_PARTY_VEHICLE_REG":
                if (session.getAttribute().get("Vehicle_Registration") == null) {
                    session.getAttribute().put("Vehicle_Registration", trimmedText);
                      sessionService.save(session);
                    return "CON Enter/select vehicle type (private, PSV, commercial,";
                } else if (session.getAttribute().get("Vehicle_Type") == null) {
                    session.getAttribute().put("Vehicle_Type", trimmedText);
                      sessionService.save(session);
                    return "CON Enter/select vehicle use (personal, commercial, etc.).";
                } else if (attribute.get("vehicleUse") == null) {
                    attribute.put("vehicleUse", trimmedText);
                      sessionService.save(session);
                    return "CON Enter your email:";


                }
                else if (attribute.get("email") == null) {
                    attribute.put("email", trimmedText);
                    sessionService.save(session);
                    return "CON Enter the vehicles capacity:";}


                else if (attribute.get("capacity") == null) {
                    attribute.put("capacity", trimmedText);
                    // saving check also the other statement if they save
                      sessionService.save(session);
                    return "CON Enter Policy Holder Name:";
                } else if (attribute.get("holderName") == null) {
                    attribute.put("holderName", trimmedText);
                    //saving
                      sessionService.save(session);
                    return "CON Enter ID/Passport Number:";
                } else if (attribute.get("holderId") == null) {
                    attribute.put("holderId", trimmedText);
                      sessionService.save(session);
                    return String.format("CON Confirm Details: \nREG: %s\nType: %s\nUse: %s\nCap: %s\nName: %s\nID: %s\n1. Confirm\n2. Cancel",
                            attribute.get("Vehicle_Registration"),
                            attribute.get("Vehicle_Type"),
                            attribute.get("vehicleUse"),
                            attribute.get("capacity"),
                            attribute.get("holderName"),
                            attribute.get("holderId"));

                } else {
                    // Handle confirmation step
                    if ("1".equals(trimmedText)) {
                        ThirdPartyQuotationRequest request = parameterSetter.setThirdPartyParameter(session);
                        Quotation quotation= insuranceService.getThirdPartyQuotation(request).getBody().getData();
                        String userMessage = message.sendQuotation(quotation);

                        quotationRepo.save(quotation);
                        NotificationRequest request1 = NotificationRequest.builder()
                                .id(quotation.getQuotationNumber())
                                .email(quotation.getPolicyHolder().getEmail())
                                .phoneNumber(quotation.getPolicyHolder().getPhoneNumber())
                                .message(userMessage)
                                .build();
                        producer.sendNotificationRequest(request1);


                         logger.info("third party: {}",request);
                        // you send to api
                        sessionService.end(session.getSessionId());
                        return "END Your Third Party Insurance quote request has been submitted. You will receive an SMS.";
                    } else {
                        return "END Cancelled.";
                    }

                }

            case "COMPREHENSIVE_VEHICLE_REG":
                if (attribute.get("Vehicle_Registration") == null) {
                    attribute.put("Vehicle_Registration", trimmedText);
                      sessionService.save(session);
                    return "CON Enter Vehicle Make (e.g., Toyota, Nissan):";
                } else if (attribute.get("Vehicle_Make") == null) {
                    attribute.put("Vehicle_Make", trimmedText);
                      sessionService.save(session);
                    return "CON Enter Vehicle Model (e.g., Corolla, X-Trail):";
                } else if (attribute.get("Vehicle_Model") == null) {
                    attribute.put("Vehicle_Model", trimmedText);
                      sessionService.save(session);
                    return "CON Enter Year of Manufacture (e.g., 2018):";
                } else if (attribute.get("Year_Of_Manufacture") == null) {
                    attribute.put("Year_Of_Manufacture", trimmedText);
                      sessionService.save(session);
                    return "CON Enter/select vehicle use (personal, commercial, etc.):";
                } else if (attribute.get("vehicleUse") == null) {
                    attribute.put("vehicleUse", trimmedText);
                      sessionService.save(session);
                    return "CON Enter Sum Insured (KES):";
                } else if (attribute.get("Sum_Insured") == null) {
                    attribute.put("Sum_Insured", trimmedText);
                      sessionService.save(session);
                    return "CON Enter Policy Holder Name:";
                } else if (attribute.get("holderName") == null) {
                    attribute.put("holderName", trimmedText);
                      sessionService.save(session);
                    return "CON enter email:";
                }
                else if (attribute.get("email") == null) {
                    attribute.put("email", trimmedText);
                    sessionService.save(session);
                    return "CON Enter ID/Passport Number:";}


                else if (attribute.get("holderId") == null) {
                    attribute.put("holderId", trimmedText);
                      sessionService.save(session);

                    // Confirmation screen
                    return String.format(
                            "CON Confirm Details:\nReg: %s\nMake: %s\nModel: %s\nYear: %s\nUse: %s\nSum Insured: %s\nName: %s\nID: %s\n1. Confirm\n2. Cancel",
                            attribute.get("Vehicle_Registration"),
                            attribute.get("Vehicle_Make"),
                            attribute.get("Vehicle_Model"),
                            attribute.get("Year_Of_Manufacture"),
                            attribute.get("vehicleUse"),
                            attribute.get("Sum_Insured"),
                            attribute.get("holderName"),
                            attribute.get("holderId")
                    );
                } else {
                    // Handle confirmation step
                    if ("1".equals(trimmedText)) {
                        ComprehensiveQuotationRequest request = parameterSetter.setComprehensiveParameters(session);
                        logger.info("Comprehensive Quotation {}",request);

                     Quotation quotation= insuranceService.getComprehensiveQuotation(request).getBody().getData();
                        quotationRepo.save(quotation);
                     String userMessage = message.sendQuotation(quotation);
                        NotificationRequest request1 = NotificationRequest.builder()
                                .id(quotation.getQuotationNumber())
                                .email(quotation.getPolicyHolder().getEmail())
                                .phoneNumber(quotation.getPolicyHolder().getPhoneNumber())
                                .message(userMessage)
                                .build();
                        producer.sendNotificationRequest(request1);

                        sessionService.end(session.getSessionId());
                        return "END Your Comprehensive Insurance quote request has been submitted. You will receive an SMS.";
                    } else {
                        return "END Cancelled.";
                    }
                }

            case "PAYMENT_POLICY_TYPE":
                if(trimmedText.equals("1")){
                    session.setState("PAYMENT_TYPE_THIRD_PARTY");
                    attribute.put("policyType","third_party");
                    sessionService.save(session);
                    return "CON Payment Type \n1. Full Payment";
                }
                else if(trimmedText.equals("2")){
                    session.setState("PAYMENT_TYPE_COMPREHENSIVE");
                    attribute.put("policyType","comprehensive");
                    sessionService.save(session);
                    return "CON Payment Type \n1. Full Payment";
                }

            case  "PAYMENT_TYPE_THIRD_PARTY":
                if(trimmedText.equals("1")){
                session.setState("CONFIRM_PAYMENT");
                sessionService.save(session);
                return "CON Enter Amount (KSH): ";}

            case "PAYMENT_QUOTATION":
                session.setState("PAYMENT_CONFIRMATION_MENU");
                attribute.put("QuotationNumber",trimmedText);
                sessionService.save(session);
                return String.format("CON Payment Amount Ksh: %s \n1. Confirm\n2. Cancel",trimmedText);

            case "CONFIRM_PAYMENT":
                attribute.put("Payment_amount",trimmedText);
                session.setState("PAYMENT_QUOTATION");
                sessionService.save(session);
                return "CON Enter Quotation number: ";



            case "PAYMENT_CONFIRMATION_MENU":
                if ("1".equals(trimmedText)) {

                    Optional<Quotation> quotationOptional=quotationRepo.findById(session.getAttribute().get("QuotationNumber"));

                    Quotation quotation = quotationOptional.orElseGet(()->null);

                    if(quotation == null){
                        return "END Quotation does not exist. Please get quotation first.";
                    }

                    StkPushRequest request = StkPushRequest.builder()
                            .amount(session.getAttribute().get("Payment_amount"))
                            .phoneNumber(session.getPhoneNumber())
                            .AccountReference("REF:"+ quotation.getQuotationNumber())
                            .transactionDescription("full payment on "+ quotation.getPolicyType())
                            .build();

                   PaymentResponse paymentResponse= paymentService.sendStkPushRequest(request).getBody().getData();
                   paymentResponse.setQuotationNumber(quotation.getQuotationNumber());

                   Payment payment = Payment.builder()
                           .phone(paymentResponse.getPhone())
                           .quotationNumber(paymentResponse.getQuotationNumber())
                           .amount(paymentResponse.getAmount())
                           .checkoutRequestId(paymentResponse.getCheckoutRequestId())
                           .merchantRequestId(paymentResponse.getMerchantRequestId())
                           .responseCode(paymentResponse.getResponseCode())
                           .responseDesc(paymentResponse.getResponseDesc())
                           .status(paymentResponse.getStatus())
                           .build();

                   paymentRepository.save(payment);


                    sessionService.end(session.getSessionId());

                    return "END Your payment request has been submitted You will receive an mpesa menu";
                } else {
                    return "END Cancelled.";
                }

            case "PAYMENT_TYPE_COMPREHENSIVE" :
                if(trimmedText.equals("1")){
                    session.setState("CONFIRM_PAYMENT");
                    sessionService.save(session);
                    return "CON Enter Amount (KSH): ";
                }

            case "STATUS":
                attribute.put("policy_number",trimmedText);
                session.setState("STATUS_PROCESSED");
                return "END Request is being processed";

        }

              sessionService.end(session.getSessionId());
                return "END something wend wrong";
        }

    }
