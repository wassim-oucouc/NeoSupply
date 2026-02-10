package org.example.neosupply.service;


import org.example.neosupply.dto.request.ClientDTO;
import org.example.neosupply.dto.response.ClientDtoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {

    public ClientDtoResponse createClient(ClientDTO clientDTO);
    public ClientDtoResponse updateClient(Long id,ClientDTO clientDTO);
    public void deleteClient(Long id);
    public List<ClientDtoResponse> getAllClients();
    public ClientDtoResponse banClient(Long id);

}
