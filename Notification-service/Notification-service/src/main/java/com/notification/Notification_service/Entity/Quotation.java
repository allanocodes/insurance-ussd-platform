package com.notification.Notification_service.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Quotation {
    @Id
    @Column(name = "quotationNumber", nullable = false)
    private String quotationNumber;

    @LastModifiedBy
    @Column(name = "updatedBy")
    private String updatedBy;

    @LastModifiedDate
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "email_sent")
    private Boolean emailSent;

    @Column(name = "sms_sent")
    private Boolean smsSent;


}


