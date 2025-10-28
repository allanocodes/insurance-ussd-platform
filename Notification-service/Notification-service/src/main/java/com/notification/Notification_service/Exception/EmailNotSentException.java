package com.notification.Notification_service.Exception;

import java.security.PrivateKey;

public class EmailNotSentException extends RuntimeException{


    public EmailNotSentException(String message){
        super(message);
    }

    public EmailNotSentException(String message,Throwable cause){
       super(message,cause);
    }
}
