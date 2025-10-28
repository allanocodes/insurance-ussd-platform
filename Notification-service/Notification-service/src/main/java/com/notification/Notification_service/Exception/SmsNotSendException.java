package com.notification.Notification_service.Exception;

public class SmsNotSendException extends RuntimeException{
    public SmsNotSendException(String message){
        super(message);
    }

    public SmsNotSendException(String message,Throwable cause){
        super(message,cause);
    }
}
