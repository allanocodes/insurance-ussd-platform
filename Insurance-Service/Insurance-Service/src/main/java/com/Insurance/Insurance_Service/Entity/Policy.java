package com.Insurance.Insurance_Service.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Policy {

    @Id
    private String policyNumber;
    @Enumerated(EnumType.STRING)
    private PolicyType policyType;
    private BigDecimal premium;
    private String payType;
    private Double payedAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    private Vehicle vehicle;
    @Embedded
    private PolicyHolder policyHolder;
}
