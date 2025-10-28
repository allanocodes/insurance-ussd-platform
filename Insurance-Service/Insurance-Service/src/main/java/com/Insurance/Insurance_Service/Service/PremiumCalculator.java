package com.Insurance.Insurance_Service.Service;

import com.Insurance.Insurance_Service.Entity.Vehicle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Year;

@Service
public class PremiumCalculator {

    public BigDecimal calculateThirdPartyPremium(String vehicleType,String vehicleUse,int capacity){

        BigDecimal baseRate;
        switch (vehicleType.toLowerCase()){
            case "private": baseRate= BigDecimal.valueOf(10000);break;
            case "commercial": baseRate= BigDecimal.valueOf(15000);break;
            case "psv": baseRate = BigDecimal.valueOf(20000);
            case "motorcycle": baseRate= BigDecimal.valueOf(5000);break;
            default: baseRate= BigDecimal.valueOf(11000);
        }

        BigDecimal useFactor= BigDecimal.ZERO;
        if(vehicleUse.equalsIgnoreCase("commercial")){
            useFactor = baseRate.multiply(BigDecimal.valueOf(0.20));
        }

        BigDecimal capacityFactor = BigDecimal.ZERO;
        if (capacity > 5 && capacity <= 12) {
            capacityFactor = BigDecimal.valueOf(5000);
        } else if (capacity > 12) {
            capacityFactor = BigDecimal.valueOf(10000);
        }


        return baseRate.add(useFactor).add(capacityFactor);
    }

    public BigDecimal calculateComprehensivePremium(String make,int yearOfManufacture,BigDecimal sumInsured,String vehicleUse){

     BigDecimal rate =BigDecimal.valueOf(0.05);

     BigDecimal premium = sumInsured.multiply(rate);

     if(vehicleUse.equalsIgnoreCase("commercial")){
         premium = premium.multiply(BigDecimal.valueOf(1.20));
     }

     int age = Year.now().getValue()- yearOfManufacture;
     if(age > 10){
         premium = premium.multiply(BigDecimal.valueOf(1.10));
     }

     if(make.equalsIgnoreCase("bmw") || make.equalsIgnoreCase("mercedes")){
         premium = premium.multiply(BigDecimal.valueOf(1.15));
     }


        return premium;
    }
}
