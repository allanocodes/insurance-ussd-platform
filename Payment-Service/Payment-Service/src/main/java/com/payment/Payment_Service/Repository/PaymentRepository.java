package com.payment.Payment_Service.Repository;

import com.payment.Payment_Service.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    @Query("from Payment where merchantRequestId=:id")
    public Optional<Payment> getByMerchantId(@Param("id") String id);
}
