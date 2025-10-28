package com.payment.Payment_Service.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;
    private Double amount;
    private String merchantRequestId;
    private String checkoutRequestId;
    private  String responseCode;
    private String responseDesc;
    private int resultCode;
    private String resultDescription;
    private String mpesaReceiptNumber;
    private String status;
    private LocalDateTime transactionDate;
}
