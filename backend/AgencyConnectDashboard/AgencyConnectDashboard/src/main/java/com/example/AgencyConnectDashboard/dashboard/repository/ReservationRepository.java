package com.example.AgencyConnectDashboard.dashboard.repository;

import com.example.AgencyConnectDashboard.dashboard.domain.Reservation;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends R2dbcRepository<Reservation, String> {}