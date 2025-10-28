package com.payment.Payment_Service.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private String phone;
    private Double amount;
    private String merchantRequestId;
    private String checkoutRequestId;
    private  String responseCode;
    private String responseDesc;
    private String status;
}
