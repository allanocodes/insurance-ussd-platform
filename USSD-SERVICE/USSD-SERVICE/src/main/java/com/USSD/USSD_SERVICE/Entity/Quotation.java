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

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "quotation")
public class Quotation {
    @Id
    private String quotationNumber;
    @Enumerated(EnumType.STRING)
    private PolicyType policyType;
    private BigDecimal premium;
    private String vehicleRegistration;
    private String coverageDetails;

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

    @Embedded
    private PolicyHolder policyHolder;
    @OneToOne(cascade = CascadeType.ALL)
    private Vehicle vehicle;
}


