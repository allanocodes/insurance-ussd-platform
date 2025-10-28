package com.payment.Payment_Service.Helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {


    public static <T> ResponseEntity<ResponseWrapper<T>> status(String message, T data, HttpStatus status){

       return ResponseEntity.status(status).body(new ResponseWrapper<>("success",message,data));
    }
}
