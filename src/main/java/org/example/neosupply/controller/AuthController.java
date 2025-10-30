package org.example.neosupply.controller;

import jakarta.servlet.http.HttpSession;
import org.example.neosupply.dto.request.UsersDTO;
import org.example.neosupply.dto.response.UserDtoResponse;
import org.example.neosupply.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class AuthController {


    private final UserService userService;


    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/register")
    public ResponseEntity<UserDtoResponse> registerUser(@RequestBody UsersDTO usersDTO)
    {
        UserDtoResponse userDtoResponse = this.userService.registerUser(usersDTO);

        return ResponseEntity.ok().body(userDtoResponse);
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> loginUser(@RequestBody UsersDTO usersDTO, HttpSession httpSession)
    {
        String email = usersDTO.getEmail();
        String password = usersDTO.getPassword();

       Boolean check =  this.userService.checkEmailAndPassword(email,password);
       if(!check)
       {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid email or password"));
       }

       UserDtoResponse userDtoResponse = this.userService.getUserByEmail(email);
        httpSession.setAttribute("user",userDtoResponse);
        return ResponseEntity.ok().body(Map.of("success","login with sucess"));


    }
}
