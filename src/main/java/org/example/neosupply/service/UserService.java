package org.example.neosupply.service;


import org.example.neosupply.dto.request.UsersDTO;
import org.example.neosupply.dto.response.UserDtoResponse;
import org.example.neosupply.entity.Users;
import org.example.neosupply.enumeration.Role;
import org.example.neosupply.mapper.UserMapper;
import org.example.neosupply.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {


    public UserDtoResponse registerUser(UsersDTO usersDTO);
    public UserDtoResponse getUserByEmail(String email);
    public Boolean checkEmailAndPassword(String email,String password);
    public Users findUserById(Long id);
    public List<UserDtoResponse> getWarehouseManagers();
    public UserDtoResponse updateUserById(Long id,Users users);
    public void deleteUser(Long id);

}
