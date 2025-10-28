package com.payment.Payment_Service.Service;


import com.payment.Payment_Service.Dto.PaymentResponse;
import com.payment.Payment_Service.Dto.StkCallbackResponse;
import com.payment.Payment_Service.Dto.StkPushRequest;
import com.payment.Payment_Service.Dto.StkPushResponse;
import com.payment.Payment_Service.Entity.Payment;
import com.payment.Payment_Service.Producer.PaymentProducer;
import com.payment.Payment_Service.Repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PaymentService {

    @Value("${mpesa.consumerKey}")
    private String consumerKey;
    @Value("${mpesa.consumerSecret}")
    private  String consumerSecret;
    @Value("${mpesa.authUrl}")
    private String authUrl;

    @Value("${mpesa.shortCode}")
    private String shortCode;
    @Value("${mpesa.passCode}")
    private String passKey;
    @Value("${mpesa.stkpushurl}")
    private String stkPushUrl;

    @Value("${mpesa.callbackurl}")
    private String callbackUrl;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PaymentRepository repository;

    @Autowired
    PaymentProducer producer;


    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    public String getAuthenticationToken(){

        String auth = consumerKey + ":" + consumerSecret;
        String encodedAuthValue = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Basic "+ encodedAuthValue);

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

      ResponseEntity<Map> responseEntity=  restTemplate.exchange(authUrl, HttpMethod.GET,httpEntity, Map.class);

        return (String) responseEntity.getBody().get("access_token");
    }

    public PaymentResponse stkPushRequst(StkPushRequest request){
      String timestamp= new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
      String password = Base64.getEncoder().encodeToString((shortCode+passKey+timestamp).getBytes());

      Map<String,Object> payLoad = new HashMap<>();
      payLoad.put("PartyA",request.getPhoneNumber());
      payLoad.put("PartyB",shortCode);
      payLoad.put("BusinessShortCode",shortCode);
      payLoad.put("PhoneNumber",request.getPhoneNumber());
      payLoad.put("Timestamp",timestamp);
      payLoad.put("Password",password);
      payLoad.put("AccountReference",request.getAccountReference() == null? "Ref": request.getAccountReference());
      payLoad.put("TransactionDesc",request.getTransactionDescription() != null ? request.getTransactionDescription() : "transaction");
      payLoad.put("TransactionType","CustomerPayBillOnline");
      payLoad.put("Amount",request.getAmount());
      payLoad.put("CallBackURL",callbackUrl);

      String token = getAuthenticationToken();

      HttpHeaders headers = new HttpHeaders();
      headers.setBearerAuth(token);

      HttpEntity<Map<String,Object>> httpEntity = new HttpEntity<>(payLoad,headers);

    ResponseEntity responseEntity =
            restTemplate.exchange(stkPushUrl,HttpMethod.POST,httpEntity, StkPushResponse.class);

    StkPushResponse response = (StkPushResponse) responseEntity.getBody();

    Payment payment = Payment.builder()
            .merchantRequestId(response.getMerchantRequestId())
            .checkoutRequestId(response.getCheckoutRequestId())
            .phone(request.getPhoneNumber())
            .responseCode(response.getResponseCode())
            .responseDesc(response.getResponseDescription())
            .amount(Double.valueOf(request.getAmount()))
            .status("Pending")
            .build();

    PaymentResponse paymentResponse = PaymentResponse.builder()
            .merchantRequestId(response.getMerchantRequestId())
            .checkoutRequestId(response.getCheckoutRequestId())
            .phone(request.getPhoneNumber())
            .responseCode(response.getResponseCode())
            .responseDesc(response.getResponseDescription())
            .amount(Double.valueOf(request.getAmount()))
            .status("Pending")
            .build();

    repository.save(payment);

    logger.info("Payment object {}",payment);

    return paymentResponse;
    }

    public void storeCallbackResult(StkCallbackResponse response){

        StkCallbackResponse.StkCallback  stkCallback = response.getBody().getStkCallback() != null?
                response.getBody().getStkCallback(): null ;

        StkCallbackResponse.CallbackMetadata  callbackMetadata = response.getBody().getStkCallback().getCallbackMetadata()
                != null ? response.getBody().getStkCallback().getCallbackMetadata(): null;

        Optional<Payment> optional = repository.getByMerchantId(response.getBody().getStkCallback().getMerchantRequestId());


        Payment payment = optional.orElseGet(()->null);

        if(payment != null && callbackMetadata != null){

           payment.setResultCode(stkCallback.getResponseCode());
           payment.setResultDescription(stkCallback.getResponseDescription());
           payment.setStatus("Success");

           for(StkCallbackResponse.Item item: callbackMetadata.getItem()){

               switch (item.getName()){

                   case "MpesaReceiptNumber"->{
                       payment.setMpesaReceiptNumber((String) item.getValue());
                   }

                   case "Amount" ->{
                       if(item.getValue() instanceof  Number number){
                           payment.setAmount(number.doubleValue());
                       }

                       else{
                           payment.setAmount(Double.valueOf(item.getValue().toString()));
                       }
                   }

                   case "TransactionDate" ->{
                       String dateString = String.valueOf(item.getValue());
                       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                       LocalDateTime dateTime = LocalDateTime.parse(dateString,formatter);
                       payment.setTransactionDate(dateTime);
                   }

               }

           }






        }

        else{
           payment.setStatus("Failed");
        }
        repository.save(payment);
        producer.sendMessage(payment);

    }



}
