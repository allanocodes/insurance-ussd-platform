package com.USSD.USSD_SERVICE.Entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComprehensiveVehicle extends Vehicle{

    private String make;
    private String model;
    private int yearOfManufacture;
    private BigDecimal sumInsured;

}
