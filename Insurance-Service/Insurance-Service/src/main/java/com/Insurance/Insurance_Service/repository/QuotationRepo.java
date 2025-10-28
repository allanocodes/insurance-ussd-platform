package com.Insurance.Insurance_Service.repository;

import com.Insurance.Insurance_Service.Entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotationRepo extends JpaRepository<Quotation,String> {
}
