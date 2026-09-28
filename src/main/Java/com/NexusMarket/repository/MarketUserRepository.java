package com.NexusMarket.repository;

import com.NexusMarket.model.MarketUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketUserRepository extends JpaRepository<MarketUser, Long> {
    boolean existsByEmailIgnoreCase(String email);
}