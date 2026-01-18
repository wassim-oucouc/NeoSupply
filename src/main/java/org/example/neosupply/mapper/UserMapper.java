package org.example.neosupply.mapper;


import org.example.neosupply.dto.request.UsersDTO;
import org.example.neosupply.dto.response.UserDtoResponse;
import org.example.neosupply.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", source = "roles")
    Users toEntity(UsersDTO usersDTO);
    @Mapping(target = "roles", source = "roles")
    UserDtoResponse toDtoResponse(Users users);
}
