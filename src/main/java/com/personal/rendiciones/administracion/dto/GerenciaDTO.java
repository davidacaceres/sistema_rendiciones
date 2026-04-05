package com.personal.rendiciones.administracion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

/**
 * Objeto de transferencia para Gerencia, evitando proxies de Hibernate.
 * Incluye información del gerente asignado y de la gerencia padre.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GerenciaDTO {
    private Long idgerencia;
    private String abreviacion;
    private String nombre;

    // Gerente de la gerencia (usuario activo de la misma gerencia)
    private Long idGerenteUsuario;
    private String nombreGerente;
    private String trigramaGerente;

    // Jerarquía: información resumida de la gerencia padre (sin recursión)
    private Long idgerenciaPadre;
    private String nombreGerenciaPadre;
    private String abreviacionGerenciaPadre;

    private Instant fccreacion;
    private Instant fcmodificacion;
    private Instant fceliminacion;
}
