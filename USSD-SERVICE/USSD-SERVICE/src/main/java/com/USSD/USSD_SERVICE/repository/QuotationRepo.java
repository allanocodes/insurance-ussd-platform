package com.USSD.USSD_SERVICE.repository;


import com.USSD.USSD_SERVICE.Entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuotationRepo extends JpaRepository<Quotation,String> {

}
