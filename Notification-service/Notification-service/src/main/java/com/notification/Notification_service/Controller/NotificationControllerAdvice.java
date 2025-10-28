package com.notification.Notification_service.Controller;

import com.notification.Notification_service.Exception.EmailNotSentException;
import com.notification.Notification_service.Exception.SmsNotSendException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotificationControllerAdvice {

    @ExceptionHandler(EmailNotSentException.class)
    public ResponseEntity<ResponseWrapper> handleEmailNotSent(EmailNotSentException ex){

      ResponseWrapper wrapper = ResponseWrapper.builder()
              .message(ex.getMessage())
              .status("Error")
              .build();
        return ResponseEntity.status(500).body(wrapper);
    }


    @ExceptionHandler(SmsNotSendException.class)
    public ResponseEntity<ResponseWrapper> smsNotSent(SmsNotSendException ex){
        ResponseWrapper wrapper = ResponseWrapper.builder()
                .message(ex.getMessage())
                .status("Error")
                .build();
        return ResponseEntity.status(500).body(wrapper);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper> handleInternalErrors(Exception ex){
        ResponseWrapper wrapper = ResponseWrapper.builder()
                .message(ex.getMessage())
                .status("Error")
                .build();
        return ResponseEntity.status(500).body(wrapper);
    }
}
