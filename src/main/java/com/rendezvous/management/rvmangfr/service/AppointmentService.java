package com.rendezvous.management.rvmangfr.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rendezvous.management.rvmangfr.model.Appointment;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AppointmentService {

    private final ApiService apiService;
    private final ObjectMapper objectMapper;

    public AppointmentService() {
        apiService = new ApiService();
        objectMapper = new ObjectMapper();
    }

    public List<Appointment> getAllAppointments() throws IOException, InterruptedException {
        String response = apiService.get("/appointments");
        return objectMapper.readValue(response, new TypeReference<List<Appointment>>() {});
    }

    public List<Appointment> getAppointmentsByDate(LocalDate date) throws IOException, InterruptedException {
        String response = apiService.get("/appointments/" + date.toString());
        return objectMapper.readValue(response, new TypeReference<List<Appointment>>() {});
    }

    public Appointment addAppointment(Appointment appointment) throws IOException, InterruptedException {
        String appointmentJson = objectMapper.writeValueAsString(appointment);
        String response = apiService.post("/appointments", appointmentJson);
        return objectMapper.readValue(response, Appointment.class);
    }

    public void deleteAppointment(Long id) throws IOException, InterruptedException {
        apiService.delete("/appointments/" + id);
    }
}
