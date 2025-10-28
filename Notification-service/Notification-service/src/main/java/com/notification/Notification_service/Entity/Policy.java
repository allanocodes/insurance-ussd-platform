package com.notification.Notification_service.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
        @Column(name = "policyNumber",nullable = false)
        private String policyNumber;

        @Column(name = "startDate")
        private LocalDateTime startDate;

        @Column(name = "endDate")
        private LocalDateTime endDate;

        @Column(name = "status")
        private String status;

        @Column(name = "email_sent")
        private Boolean emailSent;

        @Column(name = "sms_sent")
        private Boolean smsSent;

        @LastModifiedDate
        @Column(name = "updatedAt")
        private LocalDateTime updatedAt;

        @LastModifiedBy
        @Column(name = "updatedBy")
        private String updatedBy;

}
