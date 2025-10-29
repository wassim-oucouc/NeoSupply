package org.example.neosupply.service;


import org.example.neosupply.dto.request.UsersDTO;
import org.example.neosupply.dto.response.UserDtoResponse;
import org.example.neosupply.entity.Users;
import org.example.neosupply.exceptions.UserAlreadyExists;
import org.example.neosupply.mapper.UserMapper;
import org.example.neosupply.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public interface UserService {


    public UserDtoResponse registerUser(UsersDTO usersDTO);

}
