package md.practice.ecommerceapi.test;

import md.practice.ecommerceapi.dto.UserDTO;
import md.practice.ecommerceapi.entity.User;
import md.practice.ecommerceapi.enums.Role;
import md.practice.ecommerceapi.exception.UserException;
import md.practice.ecommerceapi.repository.UserRepository;
import md.practice.ecommerceapi.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImpl authenticationServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testSignUp_successfulSignUp(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("new_user");
        userDTO.setPassword("password");
        userDTO.setRole(Role.USER);
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("password");
        authenticationServiceImpl.signup(userDTO);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testSignUp_usernameInUse(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("new_user");
        userDTO.setPassword("password");
        when(userRepository.findByUsername(userDTO.getUsername())).thenThrow(new UserException("username already in use"));
        assertThrows(UserException.class, () -> authenticationServiceImpl.signup(userDTO));
        verify(userRepository, never()).save(any(User.class));
    }
}
