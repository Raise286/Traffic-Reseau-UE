package com.example.agency.dashboard.repository;

import com.example.agency.dashboard.domain.WebhookEvent;
import com.example.agency.dashboard.domain.WebhookEvent;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WebhookEventRepository extends ReactiveCrudRepository<WebhookEvent, UUID> {
}