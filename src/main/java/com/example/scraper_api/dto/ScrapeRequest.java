package com.example.scraper_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ScrapeRequest {

    @NotBlank(message = "Source cannot be blank")
    private String source;

    @NotNull(message = "Limit is required")
    @Min(value = 1, message = "Limit must be at least 1")
    private Integer limit;

    // Getters and setters
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}

