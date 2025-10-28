package com.Insurance.Insurance_Service.controller;


import com.Insurance.Insurance_Service.Entity.Policy;
import com.Insurance.Insurance_Service.Entity.Quotation;
import com.Insurance.Insurance_Service.Helper.ResponseBuilder;
import com.Insurance.Insurance_Service.Helper.ResponseWrapper;
import com.Insurance.Insurance_Service.Service.InsuranceService;
import com.Insurance.Insurance_Service.dto.ComprehensiveQuotationRequest;
import com.Insurance.Insurance_Service.dto.ThirdPartyQuotationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("insurance")
public class InsuranceController {

    @Autowired
    InsuranceService insuranceService;

    @PostMapping("/comprehensiveQuotation")
    public ResponseEntity<ResponseWrapper<Quotation>> getComprehensiveQuotation(@RequestBody  ComprehensiveQuotationRequest request){
        Quotation quotation=  insuranceService.comprehensiveQuotation(request);
        return ResponseBuilder.success("quotation processed successfully",quotation, HttpStatus.OK);
    }


    @PostMapping("/thirdPartyQuotation")
    public ResponseEntity<ResponseWrapper<Quotation>> getThirdPartyQuotation(@RequestBody ThirdPartyQuotationRequest request){
        Quotation quotation=  insuranceService.produceThirdPartyQuotation(request);
        return ResponseBuilder.success("quotation processed successfully",quotation, HttpStatus.OK);
    }


    @PostMapping("/awardPolicy")
    public ResponseEntity<ResponseWrapper<Policy>> awardPolicy(@RequestParam("payed_amount") Double amount,Quotation quotation){
       Policy policy = insuranceService.awardFullpayedPolicy(quotation,amount);
        return ResponseBuilder.success("policy awarded",policy,HttpStatus.ACCEPTED);
    }
}
