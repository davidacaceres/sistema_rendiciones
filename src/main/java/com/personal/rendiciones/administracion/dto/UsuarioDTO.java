package com.personal.rendiciones.administracion.dto;

import java.time.Instant;
import java.util.Set;

import com.personal.rendiciones.administracion.model.enums.OrigenAutenticacion;
import com.personal.rendiciones.administracion.model.enums.RolUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de transferencia para Usuario, desacoplando la entidad y evitando
 * proxies de Hibernate.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long idusuario;
    private String username;
    private String email;
    private String nombre;
    private String trigrama;
    private OrigenAutenticacion origen;
    private Set<RolUsuario> roles;
    private GerenciaDTO gerencia;
    private boolean blactivo;
    private Instant fccreacion;
    private Instant fcmodificacion;
    private Instant fceliminacion;

}
