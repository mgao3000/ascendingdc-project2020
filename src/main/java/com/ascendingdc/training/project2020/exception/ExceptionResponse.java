package com.ascendingdc.training.project2020.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {

    private String message;

    public ExceptionResponse(String message, LocalDateTime dateTime, String description) {
        this.message = message;
        this.dateTime = dateTime;
        this.description = description;
    }

    private LocalDateTime dateTime;


    private String description;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
