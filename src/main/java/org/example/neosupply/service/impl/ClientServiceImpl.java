package org.example.neosupply.service.impl;

import org.example.neosupply.dto.request.ClientDTO;
import org.example.neosupply.dto.response.ClientDtoResponse;
import org.example.neosupply.entity.Client;
import org.example.neosupply.exceptions.ClientNotFoundException;
import org.example.neosupply.mapper.ClientMapper;
import org.example.neosupply.repository.ClientRepository;
import org.example.neosupply.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository,ClientMapper clientMapper,PasswordEncoder passwordEncoder)
    {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public ClientDtoResponse createClient(ClientDTO clientDTO)
    {
        Client client = this.clientMapper.toEntity(clientDTO);
        String passwordHashed = this.passwordEncoder.encode(client.getPassword());
        client.setPassword(passwordHashed);
      Client clientCreated =   this.clientRepository.save(client);
        return this.clientMapper.toDtoResponse(clientCreated);
    }
    public ClientDtoResponse updateClient(Long id,ClientDTO clientDTO)
    {
        if(id == null)
        {
            throw new IllegalArgumentException("client id is null");
        }
       Client clientMapped =  this.clientMapper.toEntity(clientDTO);

     Client client = this.clientRepository
             .findById(id)
             .orElseThrow(() -> new ClientNotFoundException("Client Not Found with Id : " + id));

     client.setPrenom(clientMapped.getPrenom());
     client.setNom(clientMapped.getNom());
     client.setEmail(clientMapped.getEmail());
     client.setActive(clientMapped.getActive());
     client.setPhoneNumber(clientMapped.getPhoneNumber());

     Client clientCreated = this.clientRepository.save(client);

     return this.clientMapper.toDtoResponse(clientCreated);

    }
    public void deleteClient(Long id)
    {
        if(id == null)
        {
            throw new IllegalArgumentException("client id is null");
        }

        Client client = this.clientRepository
                .findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client Not Found with Id : " + id));

        this.clientRepository.delete(client);
    }
    public List<ClientDtoResponse> getAllClients()
    {
        return this.clientRepository.findAll().stream().map(clientMapper::toDtoResponse).toList();
    }
    public ClientDtoResponse banClient(Long id)
    {
        if(id == null)
        {
            throw new IllegalArgumentException("client id is null");
        }

        Client client = this.clientRepository
                .findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client Not Found with Id : " + id));

        client.setActive(false);
        Client clientBanned = this.clientRepository.save(client);
       return  this.clientMapper.toDtoResponse(clientBanned);

    }


}
