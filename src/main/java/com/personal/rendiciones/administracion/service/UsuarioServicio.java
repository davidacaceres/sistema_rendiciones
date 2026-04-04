package com.personal.rendiciones.administracion.service;

import com.personal.rendiciones.administracion.dto.UsuarioDTO;
import com.personal.rendiciones.administracion.model.Usuario;
import com.personal.rendiciones.administracion.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

import com.personal.rendiciones.administracion.model.enums.OrigenAutenticacion;

@Service
@RequiredArgsConstructor
public class UsuarioServicio {

    private final UsuarioRepository usuarioRepository;
    private final GerenciaServicio gerenciaServicio;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<UsuarioDTO> listar(Specification<Usuario> spec, Pageable pageable) {
        // Garantizar que solo se busquen los no eliminados
        Specification<Usuario> noEliminadosSpec = (root, query, cb) -> cb.isNull(root.get("fceliminacion"));
        return usuarioRepository.findAll(Specification.where(spec).and(noEliminadosSpec), pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> buscarPorId(Long idusuario) {
        return usuarioRepository.findByIdusuarioAndFceliminacionIsNull(idusuario).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> buscarPorUsername(String username) {
        return usuarioRepository.findByUsernameAndFceliminacionIsNull(username).map(this::toDTO);
    }

    public UsuarioDTO toDTO(Usuario u) {
        if (u == null)
            return null;
        return UsuarioDTO.builder().idusuario(u.getIdusuario()).username(u.getUsername()).email(u.getEmail()).nombre(u.getNombre()).trigrama(u.getTrigrama()).origen(u.getOrigen())
                .roles(u.getRoles()).gerencia(gerenciaServicio.toDTO(u.getGerencia())).blactivo(u.isBlactivo()).fccreacion(u.getFccreacion()).fcmodificacion(u.getFcmodificacion())
                .build();
    }

    @Transactional
    public Usuario guardar(Usuario usuario) {
        // Normalización preventiva
        if (usuario.getUsername() != null) usuario.setUsername(usuario.getUsername().trim());
        if (usuario.getEmail() != null) usuario.setEmail(usuario.getEmail().trim().toLowerCase());
        if (usuario.getTrigrama() != null) usuario.setTrigrama(usuario.getTrigrama().trim().toUpperCase());

        System.out.println("PRE-SAVE: " + usuario.getUsername() + " (ID: " + usuario.getIdusuario() + ")");

        // 1. Validaciones de unicidad (Username, Email, Trigrama)
        validarUnicidad(usuario);

        // 2. Si el usuario es nuevo y el origen es LOCAL, hashear la contraseña
        if (usuario.getIdusuario() == null && usuario.getOrigen() == OrigenAutenticacion.LOCAL && usuario.getPassword() != null) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepository.save(usuario);
    }

    private void validarUnicidad(Usuario u) {
        Long id = u.getIdusuario();

        // Validar Username
        usuarioRepository.findByUsername(u.getUsername()).ifPresent(existente -> {
            if (id == null || !existente.getIdusuario().equals(id)) {
                if (existente.getFceliminacion() != null) {
                    throw new RuntimeException("DELETED_USER_EXISTS:" + existente.getIdusuario() + ":username");
                } else {
                    throw new RuntimeException("DUPLICATE_USERNAME");
                }
            }
        });

        // Validar Email
        usuarioRepository.findByEmail(u.getEmail()).ifPresent(existente -> {
            if (id == null || !existente.getIdusuario().equals(id)) {
                if (existente.getFceliminacion() != null) {
                    throw new RuntimeException("DELETED_USER_EXISTS:" + existente.getIdusuario() + ":email");
                } else {
                    throw new RuntimeException("DUPLICATE_EMAIL");
                }
            }
        });

        // Validar Trigrama
        usuarioRepository.findByTrigrama(u.getTrigrama()).ifPresent(existente -> {
            if (id == null || !existente.getIdusuario().equals(id)) {
                if (existente.getFceliminacion() != null) {
                    throw new RuntimeException("DELETED_USER_EXISTS:" + existente.getIdusuario() + ":trigrama");
                } else {
                    throw new RuntimeException("DUPLICATE_TRIGRAMA");
                }
            }
        });
    }

    @Transactional
    public Usuario recuperar(Long id) {
        return usuarioRepository.findById(id).map(u -> {
            u.setFceliminacion(null);
            u.setBlactivo(true);
            return usuarioRepository.save(u);
        }).orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
    }

    @Transactional
    public void eliminarLogicamente(Long idusuario) {
        usuarioRepository.findById(idusuario).ifPresent(u -> {
            u.setFceliminacion(Instant.now());
            u.setBlactivo(false);
            usuarioRepository.save(u);
        });
    }
}
