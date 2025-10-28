package com.USSD.USSD_SERVICE.repository;


import com.USSD.USSD_SERVICE.Entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepo extends JpaRepository<Policy,String> {
}
