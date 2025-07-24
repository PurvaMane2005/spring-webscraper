package com.example.scraper_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "scraped_data")  // Ensures table name is predictable in MSSQL
public class ScrapedData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String title;

    @Column(name = "file_links")  // Optional: maps to a more conventional DB column
    private String fileLinks;

    // Constructors
    public ScrapedData() {
    }

    public ScrapedData(String code, String title, String fileLinks) {
        this.code = code;
        this.title = title;
        this.fileLinks = fileLinks;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileLinks() {
        return fileLinks;
    }

    public void setFileLinks(String fileLinks) {
        this.fileLinks = fileLinks;
    }

    // Optional: toString() for easy debugging
    @Override
    public String toString() {
        return "ScrapedData{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", fileLinks='" + fileLinks + '\'' +
                '}';
    }
}
