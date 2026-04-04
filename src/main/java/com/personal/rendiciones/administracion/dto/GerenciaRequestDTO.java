package com.personal.rendiciones.administracion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para crear o actualizar una Gerencia.
 * Separa la entidad de dominio del contrato de la API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GerenciaRequestDTO {

    /** Abreviación identificadora (ej: TI, GFIN). Requerida. */
    private String abreviacion;

    /** Nombre completo de la gerencia. Requerido. */
    private String nombre;

    /**
     * ID del usuario que será gerente de esta gerencia.
     * El usuario debe pertenecer a la misma gerencia.
     * Nullable: si es null, la gerencia queda sin gerente asignado.
     */
    private Long idGerenteUsuario;

    /**
     * ID de la gerencia padre en la jerarquía organizacional.
     * Nullable: si es null, esta es una gerencia raíz.
     */
    private Long idgerenciaPadre;

    // Getters y setters explícitos (respaldo al problema de Lombok en IDE)

    public String getAbreviacion() { return abreviacion; }
    public void setAbreviacion(String abreviacion) { this.abreviacion = abreviacion; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Long getIdGerenteUsuario() { return idGerenteUsuario; }
    public void setIdGerenteUsuario(Long idGerenteUsuario) { this.idGerenteUsuario = idGerenteUsuario; }

    public Long getIdgerenciaPadre() { return idgerenciaPadre; }
    public void setIdgerenciaPadre(Long idgerenciaPadre) { this.idgerenciaPadre = idgerenciaPadre; }
}
