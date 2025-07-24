package com.example.scraper_api.repository;
import org.springframework.stereotype.Repository;

import com.example.scraper_api.model.ScrapedData;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ScrapedDataRepository extends JpaRepository<ScrapedData, Long> {
    // Optional: Add custom query methods here
}

