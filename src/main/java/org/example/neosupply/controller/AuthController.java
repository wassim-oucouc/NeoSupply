package org.example.neosupply.controller;

import org.example.neosupply.dto.request.UsersDTO;
import org.example.neosupply.dto.response.UserDtoResponse;
import org.example.neosupply.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


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
}
