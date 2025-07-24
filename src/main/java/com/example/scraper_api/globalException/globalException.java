package com.example.scraper_api.globalException;

import com.example.scraper_api.exceptions.ScraperException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.scraper_api.errorResponse.errorResponse;


import java.time.LocalDateTime;

@RestControllerAdvice
public class globalException {

	@ExceptionHandler(ScraperException.class)
    public ResponseEntity<errorResponse> handleScraperException(ScraperException ex) {
        errorResponse error = new errorResponse(
                LocalDateTime.now(),
                "Scraper Error",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<errorResponse> handleGenericException(Exception ex) {
        errorResponse error = new errorResponse(
                LocalDateTime.now(),
                "Unexpected Error: " + ex.getClass().getSimpleName(),
                ex.getMessage().split("\n")[0],
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

