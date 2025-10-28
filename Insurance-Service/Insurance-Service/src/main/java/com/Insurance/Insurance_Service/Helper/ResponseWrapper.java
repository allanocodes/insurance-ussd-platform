package com.Insurance.Insurance_Service.Helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper <T>{
    private String status;
    private String message;
    private T data;
}
