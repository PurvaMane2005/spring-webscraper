
package com.example.scraper_api.errorResponse;

import java.time.LocalDateTime;

public class errorResponse {
    private LocalDateTime timestamp;
    private String error;
    private String details;
    private int status;

    public errorResponse(LocalDateTime timestamp, String error, String details, int status) {
        this.timestamp = timestamp;
        this.error = error;
        this.details = details;
        this.status = status;
    }

    // Getters and setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getError() {
        return error;
    }

    public String getDetails() {
        return details;
    }

    public int getStatus() {
        return status;
    }
    
}

