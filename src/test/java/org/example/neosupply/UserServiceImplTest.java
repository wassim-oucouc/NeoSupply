package org.example.neosupply;

import org.example.neosupply.dto.request.UsersDTO;
import org.example.neosupply.dto.response.UserDtoResponse;
import org.example.neosupply.entity.Users;
import org.example.neosupply.enumeration.Role;
import org.example.neosupply.exceptions.UserAlreadyExistsException;
import org.example.neosupply.exceptions.UserNotFoundException;
import org.example.neosupply.mapper.UserMapper;
import org.example.neosupply.repository.UserRepository;
import org.example.neosupply.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UsersDTO usersDTO;
    private Users user;
    private UserDtoResponse userDtoResponse;

    @BeforeEach
    void setUp() {
        usersDTO = new UsersDTO();
        usersDTO.setPrenom("John");
        usersDTO.setNom("Doe");
        usersDTO.setEmail("john.doe@example.com");
        usersDTO.setPassword("password123");
        usersDTO.setRoles(Set.of(Role.ROLE_CLIENT));

        user = new Users();
        user.setId(1L);
        user.setPrenom("John");
        user.setNom("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("encodedPassword");
        usersDTO.setRoles(Set.of(Role.ROLE_CLIENT));
        user.setActive(true);

        userDtoResponse = new UserDtoResponse();
        userDtoResponse.setId(1L);
        userDtoResponse.setPrenom("John");
        userDtoResponse.setNom("Doe");
        userDtoResponse.setEmail("john.doe@example.com");
        usersDTO.setRoles(Set.of(Role.ROLE_CLIENT));
        userDtoResponse.setActive(true);
    }

    @Test
    void testRegisterUser_success() {
        when(userRepository.existsUsersByEmail(usersDTO.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(usersDTO.getPassword())).thenReturn("encodedPassword");
        when(userMapper.toDtoResponse(any(Users.class))).thenReturn(userDtoResponse);

        UserDtoResponse result = userService.registerUser(usersDTO);

        assertNotNull(result);
        assertEquals("John", result.getPrenom());
        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    void testRegisterUser_alreadyExists() {
        when(userRepository.existsUsersByEmail(usersDTO.getEmail())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(usersDTO));
        verify(userRepository, never()).save(any());
    }

    @Test
    void testGetUserByEmail_success() {
        when(userRepository.findUsersByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
        when(userMapper.toDtoResponse(user)).thenReturn(userDtoResponse);

        UserDtoResponse result = userService.getUserByEmail("john.doe@example.com");

        assertNotNull(result);
        assertEquals("John", result.getPrenom());
    }

    @Test
    void testGetUserByEmail_notFound() {
        when(userRepository.findUsersByEmail("unknown@example.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail("unknown@example.com"));
    }

    @Test
    void testCheckEmailAndPassword_success() {
        when(userRepository.existsUsersByEmail("john.doe@example.com")).thenReturn(true);
        when(userRepository.findUsersByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);

        assertTrue(userService.checkEmailAndPassword("john.doe@example.com", "password123"));
    }

    @Test
    void testCheckEmailAndPassword_wrongPassword() {
        when(userRepository.existsUsersByEmail("john.doe@example.com")).thenReturn(true);
        when(userRepository.findUsersByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encodedPassword")).thenReturn(false);

        assertFalse(userService.checkEmailAndPassword("john.doe@example.com", "wrong"));
    }

    @Test
    void testCheckEmailAndPassword_emailNotFound() {
        when(userRepository.existsUsersByEmail("unknown@example.com")).thenReturn(false);

        assertThrows(UserNotFoundException.class, () ->
                userService.checkEmailAndPassword("unknown@example.com", "password123")
        );
    }
}
