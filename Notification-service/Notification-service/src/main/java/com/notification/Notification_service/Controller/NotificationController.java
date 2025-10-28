package com.notification.Notification_service.Controller;

import com.notification.Notification_service.Dto.NotificationRequest;
import com.notification.Notification_service.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @PostMapping("/sendQuotation")
    public ResponseEntity<ResponseWrapper> sendQuotationMessages(@RequestBody NotificationRequest request){
        notificationService.sendQuotationMessages(request);
        return ResponseEntity.ok(new ResponseWrapper("Success","message sent successfully"));
    }
    @PostMapping("/sendPolicy")
    public ResponseEntity<ResponseWrapper> sendPolicyMessages(@RequestBody NotificationRequest request){
       notificationService.sendPolicyMessages(request);
        return ResponseEntity.ok(new ResponseWrapper("Success","message sent successfully"));

    }
    @PostMapping("/sendPayment")
    public ResponseEntity<ResponseWrapper> sendPaymentMessages(@RequestBody NotificationRequest request){
       notificationService.sendPaymentMessages(request);
        return ResponseEntity.ok(new ResponseWrapper("Success","message sent successfully"));
    }


}
