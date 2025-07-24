package com.example.scraper_api.services;

import com.example.scraper_api.exceptions.ScraperException;
import com.example.scraper_api.model.ScrapedData;
import com.example.scraper_api.model.ScrapeHistory;
import com.example.scraper_api.repository.ScrapedDataRepository;
import com.example.scraper_api.repository.ScrapeHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class ScraperServicesTest {

    @Mock
    private ScrapedDataRepository scrapedDataRepository;

    @Mock
    private ScrapeHistoryRepository scrapeHistoryRepository;

    @InjectMocks
    @Spy
    private ScraperServices scraperServices;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testScrapeAraiToCSV_SuccessfulScrape() {
        // Arrange
        WebElement mockRow = mock(WebElement.class);
        WebElement cell1 = mock(WebElement.class);
        WebElement cell2 = mock(WebElement.class);
        WebElement cell3 = mock(WebElement.class);

        when(cell1.getText()).thenReturn("CODE001");
        when(cell2.getText()).thenReturn("Test Title");
        when(cell3.getText()).thenReturn("http://example.com/file.pdf");

        List<WebElement> mockCells = Arrays.asList(cell1, cell2, cell3);
        when(mockRow.findElements(By.tagName("td"))).thenReturn(mockCells);

        List<WebElement> mockRows = Arrays.asList(mockRow, mockRow);

        doReturn(mockRows).when(scraperServices).fetchAraiRows(any(WebDriver.class));

        // Act
        String result = scraperServices.scrapeAraiToCSV("araiindia", 1);

        // Assert
        assert result.contains("✅ Data written to output.csv");
        verify(scrapedDataRepository, times(1)).save(any(ScrapedData.class));
        verify(scrapeHistoryRepository, times(1)).save(any(ScrapeHistory.class));
    }

    @Test
    public void testScrapeAraiToCSV_UnknownSource_ThrowsException() {
        // Act & Assert
        try {
            scraperServices.scrapeAraiToCSV("invalidsource", 1);
        } catch (ScraperException e) {
            assert e.getMessage().contains("❌ Unknown source");
        }

        verify(scrapeHistoryRepository, times(1)).save(any(ScrapeHistory.class));
    }
}
