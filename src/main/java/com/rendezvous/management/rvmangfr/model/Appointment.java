package com.rendezvous.management.rvmangfr.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {

    private Long id;
    private LocalDate date;
    private LocalTime time;
    private Client client;

    public Appointment() {
    }

    public Appointment(Long id, LocalDate date, LocalTime time, Client client) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.client = client;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Client getClient() {
        return client;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
