package com.rendezvous.management.rvmangfr.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rendezvous.management.rvmangfr.model.Client;

import java.io.IOException;
import java.util.List;

public class ClientService {

    private final ApiService apiService;
    private final ObjectMapper objectMapper;

    public ClientService() {
        apiService = new ApiService();
        objectMapper = new ObjectMapper();
    }

    public List<Client> getAllClients() throws IOException, InterruptedException {
        String response = apiService.get("/clients");

        if (response == null || response.isBlank()) {
            throw new IOException("Received empty response from /api/clients");
        }

        try {
            return objectMapper.readValue(response, new TypeReference<List<Client>>() {});
        } catch (IOException e) {
            System.err.println("Failed to parse client list from API response: " + e.getMessage());
            throw new IOException("Error parsing client data", e);
        }
    }

    public Client addClient(Client client) throws IOException, InterruptedException {
        String clientJson = objectMapper.writeValueAsString(client);
        String response = apiService.post("/clients", clientJson);
        return objectMapper.readValue(response, Client.class);
    }

    public Client updateClient(Client client) throws IOException, InterruptedException {
        String clientJson = objectMapper.writeValueAsString(client);
        String response = apiService.put("/clients/" + client.getId(), clientJson); // ID is now Integer
        return objectMapper.readValue(response, Client.class);
    }

    public void deleteClient(Integer id) throws IOException, InterruptedException {  // ID is now Integer
        apiService.delete("/clients/" + id);
    }
}
