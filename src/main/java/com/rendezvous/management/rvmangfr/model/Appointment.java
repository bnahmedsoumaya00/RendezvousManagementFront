package com.rendezvous.management.rvmangfr.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Appointment {

    private Long id;
    private LocalDate date; // Date of the appointment
    private LocalDateTime time; // Time of the appointment

    // Default constructor
    public Appointment() {}

    // Constructor
    public Appointment(Long id, LocalDate date, LocalDateTime time) {
        this.id = id;
        this.date = date;
        this.time = time;
    }

    @JsonProperty
    public Long getId() {
        return id;
    }

    @JsonProperty
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty
    public LocalDate getDate() {
        return date;
    }

    @JsonProperty
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @JsonProperty
    public LocalDateTime getTime() {
        return time;
    }

    @JsonProperty
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
