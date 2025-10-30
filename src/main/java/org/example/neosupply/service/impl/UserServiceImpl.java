package org.example.neosupply.service.impl;
import org.example.neosupply.dto.request.UsersDTO;
import org.example.neosupply.dto.response.UserDtoResponse;
import org.example.neosupply.entity.Users;
import org.example.neosupply.enumeration.Role;
import org.example.neosupply.exceptions.UserAlreadyExistsException;
import org.example.neosupply.exceptions.UserNotFoundException;
import org.example.neosupply.mapper.UserMapper;
import org.example.neosupply.repository.UserRepository;
import org.example.neosupply.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserDtoResponse registerUser(UsersDTO usersDTO)
    {
        if(this.userRepository.existsUsersByEmail(usersDTO.getEmail()))
        {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        String passwordEncode = this.passwordEncoder.encode(usersDTO.getPassword());

        Users users = new Users();
        users.setPrenom(usersDTO.getPrenom());
        users.setNom(usersDTO.getNom());
        users.setEmail(usersDTO.getEmail());
        users.setPassword(passwordEncode);
        users.setActive(true);
        users.setRole(Role.valueOf(usersDTO.getRole().name()));

        this.userRepository.save(users);

        return this.userMapper.toDtoResponse(users);

    }

    public UserDtoResponse getUserByEmail(String email)
    {
        UserDtoResponse userDtoResponse = null;
       Optional<Users> userFound = Optional.ofNullable(this.userRepository.findUsersByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found")));
       if(userFound.isPresent())
       {
        userDtoResponse =  this.userMapper.toDtoResponse(userFound.get());
       }
       return userDtoResponse;
    }

    public Boolean checkEmailAndPassword(String email,String password)
    {
        if(!this.userRepository.existsUsersByEmail(email))
        {
            throw new UserNotFoundException("User with this email not exists");
        }

        Users userFound = this.userRepository.findUsersByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found"));
        boolean check = this.passwordEncoder.matches(password,userFound.getPassword());
        if(!check)
        {
          return false;
        }
        return true;



    }
}
