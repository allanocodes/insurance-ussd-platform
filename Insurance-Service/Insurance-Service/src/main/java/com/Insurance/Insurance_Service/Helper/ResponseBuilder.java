package com.Insurance.Insurance_Service.Helper;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.crypto.Data;

public class ResponseBuilder {

    public static <T> ResponseEntity<ResponseWrapper<T>> success(String message, T data, HttpStatus status){

        return ResponseEntity.status(status).body(new ResponseWrapper<>("success",message,data));
    }
}
