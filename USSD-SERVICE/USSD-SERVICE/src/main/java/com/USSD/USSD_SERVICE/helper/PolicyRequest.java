package com.USSD.USSD_SERVICE.helper;

import com.USSD.USSD_SERVICE.Entity.Quotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyRequest {

    Double amount;
    Double balance;
    Quotation quotation;
}
