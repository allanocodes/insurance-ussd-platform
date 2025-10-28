package com.Insurance.Insurance_Service.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Quotation {
    @Id
    private String quotationNumber;
    @Enumerated(EnumType.STRING)
    private PolicyType policyType;
    private BigDecimal premium;
    private String vehicleRegistration;
    private String coverageDetails;

    @Embedded
    private PolicyHolder policyHolder;
    @OneToOne(cascade = CascadeType.ALL)
    private Vehicle vehicle;
}


