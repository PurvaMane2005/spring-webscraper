package com.example.scraper_api.services;

import com.example.scraper_api.exceptions.ScraperException;
import com.example.scraper_api.model.ScrapedData;
import com.example.scraper_api.model.ScrapeHistory;
import com.example.scraper_api.repository.ScrapedDataRepository;
import com.example.scraper_api.repository.ScrapeHistoryRepository;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScraperServices {

    @Autowired
    private ScrapedDataRepository scrapedDataRepository;

    @Autowired
    private ScrapeHistoryRepository scrapeHistoryRepository;

    public String scrapeAraiToCSV(String source, int limit) {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\Z0314682\\OneDrive - ZF Friedrichshafen AG\\Desktop\\Demo\\ChromeDriver\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = null;

        try {
            driver = new ChromeDriver();

            String url;
            if ("araiindia".equalsIgnoreCase(source)) {
                url = "https://www.araiindia.com/downloads";
            } else {
                throw new ScraperException("❌ Unknown source: " + source);
            }

            driver.get(url);

            List<WebElement> rows = fetchAraiRows(driver);

            int rowsToWrite = Math.min(limit, rows.size());
            System.out.println("✅ Scraping " + rowsToWrite + " of " + rows.size() + " rows");

            try (FileWriter csvWriter = new FileWriter("output.csv")) {
                csvWriter.append("Sr.,Code,Title,Attached Files\n");

                for (int i = 0; i < rowsToWrite; i++) {
                    WebElement row = rows.get(i);
                    List<WebElement> cells = row.findElements(By.tagName("td"));

                    String code = cells.size() > 0 ? cells.get(0).getText().trim() : "";
                    String title = cells.size() > 1 ? cells.get(1).getText().trim() : "";
                    String fileLinks = cells.size() > 2 ? cells.get(2).getText().trim() : "";

                    csvWriter.append(code.replaceAll(",", " ") + "," +
                            title.replaceAll(",", " ") + "," +
                            fileLinks.replaceAll(",", " ") + "\n");

                    ScrapedData data = new ScrapedData();
                    data.setCode(code);
                    data.setTitle(title);
                    data.setFileLinks(fileLinks);
                    scrapedDataRepository.save(data);
                }

                csvWriter.flush();

                // ✅ Log success
                ScrapeHistory history = new ScrapeHistory();
                history.setSource(source);
                history.setTimestamp(LocalDateTime.now());
                history.setRowsSaved(rowsToWrite);
                history.setStatus("SUCCESS");
                scrapeHistoryRepository.save(history);

                return "✅ Data written to output.csv and saved to DB (Rows saved: " + rowsToWrite + ")";
            } catch (IOException e) {
                logFailure(source, 0);
                throw new RuntimeException("❌ Failed to write CSV file: " + e.getMessage(), e);
            }

        } catch (ScraperException e) {
            logFailure(source, 0);
            throw e;
        } catch (Exception e) {
            logFailure(source, 0);
            throw new RuntimeException("⚠️ Unexpected error: " + e.getMessage(), e);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    // ✅ Extracted for mocking in tests
    protected List<WebElement> fetchAraiRows(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("table.table.table-striped.table-bordered")));
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector("table.table.table-striped.table-bordered tbody tr")));
        } catch (TimeoutException e) {
            throw new ScraperException("❌ Table not found or failed to load on the given page.");
        }
    }

    private void logFailure(String source, int rowsSaved) {
        ScrapeHistory history = new ScrapeHistory();
        history.setSource(source);
        history.setTimestamp(LocalDateTime.now());
        history.setRowsSaved(rowsSaved);
        history.setStatus("FAILED");
        scrapeHistoryRepository.save(history);
    }
}
