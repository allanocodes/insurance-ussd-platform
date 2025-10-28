package com.USSD.USSD_SERVICE.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StkPushRequest {
    private String phoneNumber;
    private String amount;
    private String AccountReference;
    private String transactionDescription;
}
