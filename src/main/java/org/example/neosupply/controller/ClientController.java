package org.example.neosupply.controller;

import org.example.neosupply.dto.request.ClientDTO;
import org.example.neosupply.dto.response.ClientDtoResponse;
import org.example.neosupply.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientDtoResponse> createClient(@RequestBody ClientDTO clientDTO) {
        ClientDtoResponse createdClient = clientService.createClient(clientDTO);
        return ResponseEntity.status(201).body(createdClient);
    }

    @GetMapping
    public ResponseEntity<List<ClientDtoResponse>> getAllClients() {
        List<ClientDtoResponse> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDtoResponse> updateClient(
            @PathVariable Long id,
            @RequestBody ClientDTO clientDTO) {
        ClientDtoResponse updatedClient = clientService.updateClient(id, clientDTO);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/ban")
    public ResponseEntity<ClientDtoResponse> banClient(@PathVariable Long id) {
        ClientDtoResponse bannedClient = clientService.banClient(id);
        return ResponseEntity.ok(bannedClient);
    }
}
