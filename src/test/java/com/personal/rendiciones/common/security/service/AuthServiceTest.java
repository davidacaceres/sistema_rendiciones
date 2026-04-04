package com.personal.rendiciones.common.security.service;

import com.personal.rendiciones.administracion.model.Gerencia;
import com.personal.rendiciones.administracion.model.Usuario;
import com.personal.rendiciones.administracion.model.enums.OrigenAutenticacion;
import com.personal.rendiciones.administracion.model.enums.RolUsuario;
import com.personal.rendiciones.administracion.repository.UsuarioRepository;
import com.personal.rendiciones.common.security.dto.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private KeycloakService keycloakService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_SuccessLocalUser() {
        // Arrange
        String username = "localUser";
        String password = "password";
        
        Usuario user = new Usuario();
        user.setIdusuario(1L);
        user.setUsername(username);
        user.setPassword("hashedPass");
        user.setNombre("Nombre");
        user.setOrigen(OrigenAutenticacion.LOCAL);
        user.setRoles(Collections.singleton(RolUsuario.ADMINISTRADOR));
        user.setBlactivo(true);
        
        when(usuarioRepository.findByUsernameAndFceliminacionIsNull(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, "hashedPass")).thenReturn(true);
        when(jwtService.generateToken(anyString(), anyString(), anyList())).thenReturn("jwt-token");

        // Act
        LoginResponse result = authService.login(username, password);
        
        // Assert
        assertNotNull(result);
        assertEquals("jwt-token", result.getToken());
        assertEquals("Nombre", result.getName());
        verify(keycloakService, never()).authenticate(anyString(), anyString());
    }

    @Test
    void login_SuccessKeycloakUser() {
        // Arrange
        String username = "keycloakUser";
        String password = "password";
        
        Usuario user = new Usuario();
        user.setIdusuario(2L);
        user.setUsername(username);
        user.setNombre("Nombre KC");
        user.setOrigen(OrigenAutenticacion.KEYCLOAK);
        user.setRoles(Collections.singleton(RolUsuario.USUARIO));
        user.setBlactivo(true);

        when(usuarioRepository.findByUsernameAndFceliminacionIsNull(username)).thenReturn(Optional.of(user));
        
        Map<String, Object> keycloakResponse = new HashMap<>();
        keycloakResponse.put("access_token", "kc-token");
        when(keycloakService.authenticate(username, password)).thenReturn(keycloakResponse);
        when(jwtService.generateToken(anyString(), anyString(), anyList())).thenReturn("unified-token");

        // Act
        LoginResponse result = authService.login(username, password);
        
        // Assert
        assertNotNull(result);
        assertEquals("unified-token", result.getToken());
        assertEquals("Nombre KC", result.getName());
    }

    @Test
    void login_Failure() {
        // Arrange
        String username = "unknown";
        String password = "password";
        
        when(usuarioRepository.findByUsernameAndFceliminacionIsNull(username)).thenReturn(Optional.empty());

        // Act
        LoginResponse result = authService.login(username, password);
        
        // Assert
        assertNull(result);
    }
}
