package com.USSD.USSD_SERVICE.repository;


import com.USSD.USSD_SERVICE.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    @Query("from Payment where merchantRequestId=:merchantId")
    public Optional<Payment> findByMerchantId(@Param("merchantId") String merchantId);

}
