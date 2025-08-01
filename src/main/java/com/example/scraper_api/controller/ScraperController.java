package com.example.scraper_api.controller;

import com.example.scraper_api.dto.ScrapeRequest;
import com.example.scraper_api.services.ScraperServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/scrape")
public class ScraperController {

    @Autowired
    private ScraperServices scraperServices;

    @PostMapping("/start")
    public String scrape(@Valid @RequestBody ScrapeRequest request) {
        // Using the validated request DTO
        return scraperServices.scrapeAraiToCSV(request.getSource(), request.getLimit());
    }
    
    @GetMapping("/")
    public String home() {
        return "API is running!";
    }

}
