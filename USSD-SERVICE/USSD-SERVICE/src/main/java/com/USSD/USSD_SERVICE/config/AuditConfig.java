package com.USSD.USSD_SERVICE.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class AuditConfig {

    AuditorAware<String> auditorAware(){
        return ()->{
            return Optional.of("System");
        };
    }
}
