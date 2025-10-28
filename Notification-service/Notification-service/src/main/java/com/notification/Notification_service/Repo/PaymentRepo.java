package com.notification.Notification_service.Repo;

import com.notification.Notification_service.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment,String> {
}
