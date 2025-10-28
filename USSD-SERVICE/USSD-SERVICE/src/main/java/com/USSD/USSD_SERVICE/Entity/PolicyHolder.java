package com.USSD.USSD_SERVICE.Entity;

import jakarta.persistence.Embeddable;
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
