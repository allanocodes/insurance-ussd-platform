package com.USSD.USSD_SERVICE.helper;

import com.USSD.USSD_SERVICE.Entity.Payment;
import com.USSD.USSD_SERVICE.Entity.Policy;
import com.USSD.USSD_SERVICE.Entity.Quotation;
import com.USSD.USSD_SERVICE.repository.QuotationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProcessPayment {

    @Autowired
    QuotationRepo quotationRepo;

    public PolicyRequest checkAmount(Payment payment){
        Double amount = payment.getAmount();
        String phone = payment.getPhone();

        Optional<Quotation> quotationOptional = quotationRepo.findById("id");

        Quotation quotation = quotationOptional.orElseGet(null);

        BigDecimal payedAmount = BigDecimal.valueOf(amount);
        if(quotation != null && payedAmount.compareTo(quotation.getPremium()) >= 0 ){
            PolicyRequest policyRequest = PolicyRequest.builder()
                    .amount(amount)
                    .quotation(quotation)
                    .balance(0.0)
                    .build();
            return policyRequest;
        } else if (quotation != null && payedAmount.compareTo(quotation.getPremium()) >= 0 ) {
            PolicyRequest policyRequest = PolicyRequest.builder()
                    .amount(amount)
                    .quotation(quotation)
                    .balance(quotation.getPremium().subtract(BigDecimal.valueOf(amount)).toBigInteger().doubleValue())
                    .build();
        }

        return null;


    }

}
