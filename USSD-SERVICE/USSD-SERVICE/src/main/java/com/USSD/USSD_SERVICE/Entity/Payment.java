package com.USSD.USSD_SERVICE.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
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
    private String quotationNumber;

    @LastModifiedBy
    private String updatedBy;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @CreatedBy
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "email_sent")
    private Boolean emailSent;

    @Column(name = "sms_sent")
    private Boolean smsSent;
}
