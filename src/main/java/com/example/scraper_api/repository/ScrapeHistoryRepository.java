package com.example.scraper_api.repository;

import com.example.scraper_api.model.ScrapeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapeHistoryRepository extends JpaRepository<ScrapeHistory, Long> {
}
