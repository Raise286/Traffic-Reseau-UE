package com.example.AgencyConnectDashboard.dashboard.dto;

import com.example.AgencyConnectDashboard.dashboard.domain.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDTO {
    private String id;
    private String eventName;
    private NotificationPayloadDTO payload;
    private NotificationStatus status;
    private LocalDateTime receivedAt;
}