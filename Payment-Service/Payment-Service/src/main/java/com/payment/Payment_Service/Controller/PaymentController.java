package com.payment.Payment_Service.Controller;


import com.payment.Payment_Service.Dto.PaymentResponse;
import com.payment.Payment_Service.Dto.StkCallbackResponse;
import com.payment.Payment_Service.Dto.StkPushRequest;
import com.payment.Payment_Service.Entity.Payment;
import com.payment.Payment_Service.Helpers.ResponseBuilder;
import com.payment.Payment_Service.Helpers.ResponseWrapper;
import com.payment.Payment_Service.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentService service;

    @PostMapping("/pay")
    public ResponseEntity<ResponseWrapper<PaymentResponse>> sendStkPushRequest(@RequestBody StkPushRequest request){
      PaymentResponse payment = service.stkPushRequst(request);

        return ResponseBuilder.status("payment request sent",payment, HttpStatus.OK);
    }

    @PostMapping("/callback")
    public ResponseEntity<Map<String,String>> callback(@RequestBody StkCallbackResponse response){
        service.storeCallbackResult(response);

        Map<String,String> mapResponse = new HashMap<>();
        mapResponse.put("status","success");
        mapResponse.put("message","call received");
        return ResponseEntity.status(200).body(mapResponse);
    }
}
