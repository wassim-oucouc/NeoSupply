package org.example.neosupply.mapper;


import org.example.neosupply.dto.request.UsersDTO;
import org.example.neosupply.dto.response.UserDtoResponse;
import org.example.neosupply.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Users toEntity(UsersDTO usersDTO);
    UserDtoResponse toDtoResponse(Users users);
}
