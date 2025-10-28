package com.notification.Notification_service.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class AuditAwareConfig {

    @Bean
    AuditorAware<String> auditorAware(){
        return ()->{
            return Optional.of("System");
        };
    }
}
