package com.example.scraper_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "scrape_history")
public class ScrapeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String source;
    private LocalDateTime timestamp;
    private int rowsSaved;
    private String status;

    // Getters & Setters
    public Long getId() { return id; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public int getRowsSaved() { return rowsSaved; }
    public void setRowsSaved(int rowsSaved) { this.rowsSaved = rowsSaved; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
