package com.USSD.USSD_SERVICE.service;

import com.USSD.USSD_SERVICE.Entity.Policy;
import com.USSD.USSD_SERVICE.Entity.Quotation;
import com.USSD.USSD_SERVICE.dto.ComprehensiveQuotationRequest;
import com.USSD.USSD_SERVICE.dto.ThirdPartyQuotationRequest;
import com.USSD.USSD_SERVICE.helper.ResponseWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Insurance-Service")
public interface InsuranceService {

    @PostMapping("/insurance/comprehensiveQuotation")
    public ResponseEntity<ResponseWrapper<Quotation>> getComprehensiveQuotation(@RequestBody ComprehensiveQuotationRequest request);


    @PostMapping("/insurance/thirdPartyQuotation")
    public ResponseEntity<ResponseWrapper<Quotation>> getThirdPartyQuotation(@RequestBody ThirdPartyQuotationRequest request);


    @PostMapping("/insurance/awardPolicy")
    public ResponseEntity<ResponseWrapper<Policy>> awardPolicy(@RequestParam("payed_amount") Double amount, Quotation quotation);


}
