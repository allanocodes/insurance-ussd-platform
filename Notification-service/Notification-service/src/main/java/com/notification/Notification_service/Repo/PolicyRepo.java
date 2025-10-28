package com.notification.Notification_service.Repo;

import com.notification.Notification_service.Entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepo extends JpaRepository<Policy,String> {
}
