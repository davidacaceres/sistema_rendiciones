package com.personal.rendiciones.common.security.service;

import com.personal.rendiciones.common.security.dto.LoginResponse;
import com.personal.rendiciones.administracion.model.Usuario;
import com.personal.rendiciones.administracion.model.enums.OrigenAutenticacion;
import com.personal.rendiciones.administracion.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final KeycloakService keycloakService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, 
                      KeycloakService keycloakService, 
                      JwtService jwtService, 
                      PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.keycloakService = keycloakService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(String username, String password) {
        // En este sistema no hay auto-registro, el usuario debe existir localmente
        Optional<Usuario> userOpt = usuarioRepository.findByUsernameAndFceliminacionIsNull(username);
        
        if (userOpt.isEmpty() || !userOpt.get().isBlactivo()) {
            return null; // Usuario no existe, inactivo o eliminado
        }

        Usuario usuario = userOpt.get();
        List<String> roleNames = usuario.getRoles().stream()
                                    .map(r -> "ROLE_" + r.name())
                                    .collect(Collectors.toList());

        // 1. Autenticación según ORIGEN
        if (usuario.getOrigen() == OrigenAutenticacion.LOCAL) {
            if (passwordEncoder.matches(password, usuario.getPassword())) {
                return createLoginResponse(username, usuario.getNombre(), roleNames);
            }
        } else if (usuario.getOrigen() == OrigenAutenticacion.KEYCLOAK) {
            Map<String, Object> keycloakResponse = keycloakService.authenticate(username, password);
            if (keycloakResponse != null) {
                return createLoginResponse(username, usuario.getNombre(), roleNames);
            }
        }

        return null; // Autenticación fallida
    }

    public LoginResponse refreshToken(String refreshToken) {
        try {
            String username = jwtService.extractUsername(refreshToken);
            if (jwtService.validateToken(refreshToken, username)) {
                Optional<Usuario> userOpt = usuarioRepository.findByUsernameAndFceliminacionIsNull(username);
                if (userOpt.isPresent() && userOpt.get().isBlactivo()) {
                    Usuario usuario = userOpt.get();
                    List<String> roleNames = usuario.getRoles().stream()
                                                .map(r -> "ROLE_" + r.name())
                                                .collect(Collectors.toList());
                    return createLoginResponse(username, usuario.getNombre(), roleNames);
                }
            }
        } catch (Exception e) {
            // Token inválido o expirado
        }
        return null;
    }

    private LoginResponse createLoginResponse(String username, String name, List<String> roles) {
        String token = jwtService.generateToken(username, name, roles);
        String refreshToken = jwtService.generateRefreshToken(username);
        
        return new LoginResponse(
            token,
            refreshToken,
            jwtService.getExpiration(),
            jwtService.getRefreshExpiration(),
            username,
            name,
            roles
        );
    }
}
