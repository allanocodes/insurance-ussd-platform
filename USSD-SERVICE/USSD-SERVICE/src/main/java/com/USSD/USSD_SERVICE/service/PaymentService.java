package com.USSD.USSD_SERVICE.service;

import com.USSD.USSD_SERVICE.dto.PaymentResponse;
import com.USSD.USSD_SERVICE.dto.StkPushRequest;
import com.USSD.USSD_SERVICE.helper.ResponseWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Payment-Service")
public interface PaymentService {

    @PostMapping("/pay")
    public ResponseEntity<ResponseWrapper<PaymentResponse>> sendStkPushRequest(@RequestBody StkPushRequest request);

}
