package org.example.neosupply.controller;

import jakarta.servlet.http.HttpSession;
import org.example.neosupply.Security.JwtUtil;
import org.example.neosupply.dto.request.UsersDTO;
import org.example.neosupply.dto.response.UserDtoResponse;
import org.example.neosupply.entity.RefreshToken;
import org.example.neosupply.service.RefreshTokenService;
import org.example.neosupply.service.UserService;
import org.example.neosupply.service.impl.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final CustomUserDetailsService customUserDetailsService;


    public AuthController(UserService userService, JwtUtil jwtUtil, RefreshTokenService refreshTokenService, CustomUserDetailsService customUserDetailsService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDtoResponse> registerUser(@RequestBody UsersDTO usersDTO)
    {
        UserDtoResponse userDtoResponse = this.userService.registerUser(usersDTO);

        return ResponseEntity.ok().body(userDtoResponse);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {

        String refreshToken = body.get("refreshToken");

        RefreshToken token =
                refreshTokenService.verify(refreshToken);

        UserDetails userDetails =
                customUserDetailsService.loadUserByUsername(token.getEmail());

        String newAccessToken =
                jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UsersDTO usersDTO)
    {
        String email = usersDTO.getEmail();
        String password = usersDTO.getPassword();

        Boolean check =  this.userService.checkEmailAndPassword(email,password);
        if(!check)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid email or password"));
        }

        UserDtoResponse userDtoResponse = this.userService.getUserByEmail(email);
        String jwtToken = jwtUtil.generateToken(userDtoResponse.getEmail());
        RefreshToken refreshToken =
                refreshTokenService.create(usersDTO.getEmail());

        return ResponseEntity.ok().body(Map.of("accessToken",jwtToken,"refreshToken",refreshToken.getToken()));
    }
}