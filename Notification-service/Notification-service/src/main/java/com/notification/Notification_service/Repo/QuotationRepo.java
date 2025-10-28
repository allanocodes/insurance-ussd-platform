package com.notification.Notification_service.Repo;

import com.notification.Notification_service.Entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotationRepo extends JpaRepository<Quotation,String> {
}
