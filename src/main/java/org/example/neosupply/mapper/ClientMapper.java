package org.example.neosupply.mapper;


import org.example.neosupply.dto.request.ClientDTO;
import org.example.neosupply.dto.response.ClientDtoResponse;
import org.example.neosupply.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ClientMapper {

    public abstract ClientDtoResponse toDtoResponse(Client client);

    public abstract Client toEntity(ClientDTO clientDTO);
}
