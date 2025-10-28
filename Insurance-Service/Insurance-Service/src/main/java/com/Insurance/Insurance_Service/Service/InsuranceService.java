package com.Insurance.Insurance_Service.Service;

import com.Insurance.Insurance_Service.Entity.*;
import com.Insurance.Insurance_Service.dto.ComprehensiveQuotationRequest;
import com.Insurance.Insurance_Service.dto.ThirdPartyQuotationRequest;
import com.Insurance.Insurance_Service.repository.PolicyRepo;
import com.Insurance.Insurance_Service.repository.QuotationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class InsuranceService {

    @Autowired
    QuotationRepo quotationRepo;

    @Autowired
    PolicyRepo policyRepo;

    @Autowired
    PremiumCalculator calculator;

    public Quotation produceThirdPartyQuotation(ThirdPartyQuotationRequest request){
      BigDecimal premium = calculator.calculateThirdPartyPremium(request.getVehicleType(),request.getVehicleUse(),request.getCapacity());

        Vehicle vehicle = ThirdPartyVehicle.builder()
                .capacity(request.getCapacity())
                .registrationNumber(request.getVehicleRegistrationNumber())
                .vehicleType(request.getVehicleType())
                .vehicleUse(request.getVehicleUse())
                .build();

        PolicyHolder policyHolder = PolicyHolder.builder()
                .email(request.getEmail())
                .fullName(request.getPolicyHolderName())
                .idOrPassportNumber(request.getIdOrPassportNumber())
                .phoneNumber(request.getPhoneNumber())
                .build();

        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        Quotation quotation = Quotation.builder()
                .coverageDetails("Accidents")
                .policyType(PolicyType.THIRD_PARTY)
                .premium(premium)
                .vehicle(vehicle)
                .policyHolder(policyHolder)
                .quotationNumber("QUOTE:"+ date)
                .build();

       quotationRepo.save(quotation);

        return quotation;
    }


    public Quotation comprehensiveQuotation(ComprehensiveQuotationRequest request){

        BigDecimal premium = calculator.calculateComprehensivePremium(
                request.getVehicleMake(),request.getYearOfManufacture()
                ,request.getSumInsured(),request.getVehicleUse()
        );

        Vehicle vehicle = ComprehensiveVehicle.builder()
                .registrationNumber(request.getVehicleRegistrationNumber())
                .vehicleUse(request.getVehicleUse())
                .make(request.getVehicleMake())
                .model(request.getVehicleModel())
                .yearOfManufacture(request.getYearOfManufacture())
                .sumInsured(request.getSumInsured())
                .build();

        PolicyHolder policyHolder = PolicyHolder.builder()
                .email(request.getEmail())
                .fullName(request.getPolicyHolderName())
                .idOrPassportNumber(request.getIdOrPassportNumber())
                .phoneNumber(request.getPhoneNumber())
                .build();

        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        Quotation quotation = Quotation.builder()
                .coverageDetails("Accidents")
                .policyType(PolicyType.COMPREHENSIVE)
                .premium(premium)
                .vehicle(vehicle)
                .policyHolder(policyHolder)
                .quotationNumber("QUOTE:"+ date)
                .build();

        quotationRepo.save(quotation);

        return quotation;
    }


    public Policy awardFullpayedPolicy(Quotation quotation,Double amount){
       String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

       Date now = new Date();
        LocalDateTime oneYearFromNow = now.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .plusYears(1);


       Policy policy = Policy.builder()
               .policyNumber("ISU:"+ date)
               .premium(quotation.getPremium())
               .policyType(quotation.getPolicyType())
               .endDate(oneYearFromNow)
               .startDate(LocalDateTime.now())
               .payType("full payment")
               .payedAmount(amount)
               .vehicle(quotation.getVehicle())
               .premium(quotation.getPremium())
               .policyHolder(quotation.getPolicyHolder())
               .build();

       policyRepo.save(policy);


       return policy;
    }



}
