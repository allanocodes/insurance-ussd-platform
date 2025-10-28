package com.Insurance.Insurance_Service.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PolicyHolder {

    private String fullName;
    private String idOrPassportNumber;
    private String phoneNumber;
    private String email;
}
