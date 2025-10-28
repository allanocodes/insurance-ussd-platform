package com.Insurance.Insurance_Service.repository;

import com.Insurance.Insurance_Service.Entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepo extends JpaRepository<Policy,String> {
}
