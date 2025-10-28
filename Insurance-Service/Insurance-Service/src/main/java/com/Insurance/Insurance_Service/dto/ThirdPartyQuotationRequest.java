package com.Insurance.Insurance_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ThirdPartyQuotationRequest {
    private String vehicleRegistrationNumber;
    private String vehicleType;
    private String vehicleUse;
    private int capacity;

    private String policyHolderName;
    private String idOrPassportNumber;
    private String email;
    private String phoneNumber;
}
