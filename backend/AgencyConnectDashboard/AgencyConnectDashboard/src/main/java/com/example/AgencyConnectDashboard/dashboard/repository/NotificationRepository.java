package com.example.AgencyConnectDashboard.dashboard.repository;
import com.example.AgencyConnectDashboard.dashboard.domain.Notification;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends R2dbcRepository<Notification, String> {}