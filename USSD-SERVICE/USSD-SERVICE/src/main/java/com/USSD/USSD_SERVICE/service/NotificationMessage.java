package com.USSD.USSD_SERVICE.service;

import com.USSD.USSD_SERVICE.Entity.Policy;
import com.USSD.USSD_SERVICE.Entity.Quotation;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class NotificationMessage {

    public String sendQuotation(Quotation quotation){
        if (quotation == null || quotation.getPolicyHolder() == null) {
            throw new IllegalArgumentException("Invalid quotation");
        }
        if (quotation.getPolicyHolder().getFullName() == null || quotation.getPolicyHolder().getFullName().isBlank()) {
            throw new IllegalArgumentException("Policyholder name cannot be empty");
        }
        String text = String.format(
                "Dear %s,\n\n" +
                        "Thank you for requesting an insurance quotation from us. Below are the details of your quotation:\n\n" +
                        "Quotation Number: %s\n" +
                        "Policy Type: %s\n" +
                        "Premium: KES %s\n" +
                        "Vehicle Registration: %s\n" +
                        "Coverage Details: %s\n\n" +
                        "Please review the quotation and let us know if you wish to proceed or have any questions. " +
                        "You can reach us at support@allankinsurancecompany.com or +254 711 XXX YYY.\n\n" +
                        "Thank you for choosing our services!\n\n" +
                        "Best regards,\n" +
                        "Your Insurance Company",
                quotation.getPolicyHolder().getFullName(),
                quotation.getQuotationNumber(),
                quotation.getPolicyType(),
                quotation.getPremium().toString(),
                quotation.getVehicle() != null ? quotation.getVehicle().getRegistrationNumber() : "N/A",
                quotation.getCoverageDetails() != null ? quotation.getCoverageDetails() : "N/A"
        );

        return text;
    }


    public String sendPolicyMessage(Policy policy){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String text = String.format(
                "Dear %s,\n\n" +
                        "We are pleased to confirm the details of your insurance policy with us:\n\n" +
                        "Policy Number: %s\n" +
                        "Policy Type: %s\n" +
                        "Premium: KES %s\n" +
                        "Start Date: %s\n" +
                        "End Date: %s\n" +
                        "Status: %s\n" +
                        "Vehicle Registration: %s\n\n" +
                        "Thank you for choosing our insurance services. If you have any questions or need further assistance, " +
                        "please contact us at support@insurancecompany.com or +254 711 XXX YYY.\n\n" +
                        "Best regards,\n" +
                        "Your Insurance Company",
                policy.getPolicyHolder().getFullName(),
                policy.getPolicyNumber(),
                policy.getPolicyType(),
                policy.getPremium().toString(),
                policy.getStartDate() != null ? policy.getStartDate().format(formatter) : "N/A",
                policy.getEndDate() != null ? policy.getEndDate().format(formatter) : "N/A",
                policy.getStatus(),
                policy.getVehicle() != null ? policy.getVehicle().getRegistrationNumber() : "N/A");

        return text;
    }

    public String insufficientPayment(Quotation quotation){
        return "Insufficient amount pay the required remaining payment to get policy";
    }
}
