package org.example.neosupply.service;

import org.example.neosupply.entity.RefreshToken;
import org.springframework.stereotype.Service;

@Service
public interface RefreshTokenService {

    public RefreshToken create(String email);

    public RefreshToken verify(String token);

    public void deleteByEmail(String email);
}
