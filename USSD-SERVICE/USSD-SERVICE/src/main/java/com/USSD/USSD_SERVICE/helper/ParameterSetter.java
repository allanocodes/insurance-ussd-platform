package com.USSD.USSD_SERVICE.helper;

import com.USSD.USSD_SERVICE.dto.ComprehensiveQuotationRequest;
import com.USSD.USSD_SERVICE.dto.ThirdPartyQuotationRequest;
import com.USSD.USSD_SERVICE.dto.UssdSession;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class ParameterSetter {

    public ThirdPartyQuotationRequest setThirdPartyParameter(UssdSession session){
        Map<String,String> attributes = session.getAttribute();
        ThirdPartyQuotationRequest request = ThirdPartyQuotationRequest.builder()
               .capacity(Integer.parseInt(attributes.get("capacity")) )
                .vehicleRegistrationNumber(attributes.get("Vehicle_Registration"))
                .vehicleType(attributes.get("Vehicle_Type"))
                .vehicleUse(attributes.get("vehicleUse"))
                .phoneNumber(session.getPhoneNumber())
                .policyHolderName(attributes.get("holderName"))
                .idOrPassportNumber(attributes.get("holderId"))
                .email(attributes.get("email"))
                .build();

        return request;
    }

    public ComprehensiveQuotationRequest setComprehensiveParameters(UssdSession session){
        Map<String,String> attributes = session.getAttribute();
        ComprehensiveQuotationRequest request = ComprehensiveQuotationRequest.builder()
                .vehicleRegistrationNumber(attributes.get("Vehicle_Registration"))
                .vehicleUse(attributes.get("vehicleUse"))
                .phoneNumber(session.getPhoneNumber())
                .policyHolderName(attributes.get("holderName"))
                .idOrPassportNumber(attributes.get("holderId"))
                .email(attributes.get("email"))
                .sumInsured(BigDecimal.valueOf(Integer.parseInt(attributes.get("Sum_Insured"))))
                .vehicleMake(attributes.get("Vehicle_Make"))
                .vehicleModel(attributes.get("Vehicle_Model"))
                .yearOfManufacture( Integer.parseInt(attributes.get("Year_Of_Manufacture")))
                .build();


        return request;
    }





}
