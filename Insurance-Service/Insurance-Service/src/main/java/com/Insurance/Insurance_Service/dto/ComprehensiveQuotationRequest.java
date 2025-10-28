package com.Insurance.Insurance_Service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComprehensiveQuotationRequest {
    private String vehicleRegistrationNumber;
    private String vehicleMake;
    private String vehicleModel;
    private int yearOfManufacture;
    private String vehicleUse;
    private BigDecimal sumInsured;

    private String policyHolderName;
    private String idOrPassportNumber;
    private String email;
    private String phoneNumber;
}
