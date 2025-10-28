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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Policy {

    @Id
    private String policyNumber;
    @Enumerated(EnumType.STRING)
    private PolicyType policyType;
    private BigDecimal premium;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;

    @LastModifiedBy
    private String updatedBy;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @CreatedBy
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "email_sent")
    private Boolean email_sent;
    @Column(name = "sms_Sent")
    private Boolean smsSent;



    @OneToOne(cascade = CascadeType.ALL)
    private Vehicle vehicle;
    @Embedded
    private PolicyHolder policyHolder;
}
