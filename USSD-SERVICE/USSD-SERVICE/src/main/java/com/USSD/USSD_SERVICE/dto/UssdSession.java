package com.USSD.USSD_SERVICE.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UssdSession {
    private String sessionId;
    private String serviceCode;
    private String phoneNumber;
    private String text;
    private Map<String,String> attribute = new HashMap<>();
    private String state = "NEW";


    public UssdSession(String sessionId, String phoneNumber, String serviceCode) {
        this.sessionId = sessionId;
        this.phoneNumber = phoneNumber;
        this.serviceCode = serviceCode;

    }

}
