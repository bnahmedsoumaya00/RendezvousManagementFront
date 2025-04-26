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
        return objectMapper.readValue(response, new TypeReference<List<Client>>() {});
    }

    public Client addClient(Client client) throws IOException, InterruptedException {
        String clientJson = objectMapper.writeValueAsString(client);
        String response = apiService.post("/clients", clientJson);
        return objectMapper.readValue(response, Client.class);
    }

    public Client updateClient(Client client) throws IOException, InterruptedException {
        String clientJson = objectMapper.writeValueAsString(client);
        String response = apiService.put("/clients/" + client.getId(), clientJson); // 🛠️ fixed here
        return objectMapper.readValue(response, Client.class);
    }

    public void deleteClient(Long id) throws IOException, InterruptedException {
        apiService.delete("/clients/" + id);
    }
}
