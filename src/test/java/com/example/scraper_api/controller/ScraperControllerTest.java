package com.example.scraper_api.controller;

import com.example.scraper_api.dto.ScrapeRequest;
import com.example.scraper_api.services.ScraperServices;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class ScraperControllerTest {

    @Mock
    private ScraperServices scraperServices;

    @InjectMocks
    private ScraperController scraperController;

    public ScraperControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testScrape_ReturnsSuccessMessage() {
        // Arrange
        ScrapeRequest request = new ScrapeRequest();
        request.setSource("araiindia");
        request.setLimit(5);

        when(scraperServices.scrapeAraiToCSV("araiindia", 5))
                .thenReturn("✅ Data written to output.csv and saved to DB (Rows saved: 5)");

        // Act
        String response = scraperController.scrape(request);

        // Assert
        assertEquals("✅ Data written to output.csv and saved to DB (Rows saved: 5)", response);
    }
}
