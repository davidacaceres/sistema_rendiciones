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

    // Getters y setters explícitos (respaldo al problema de Lombok en IDE)

    public Long getIdgerencia() { return idgerencia; }
    public void setIdgerencia(Long idgerencia) { this.idgerencia = idgerencia; }

    public String getAbreviacion() { return abreviacion; }
    public void setAbreviacion(String abreviacion) { this.abreviacion = abreviacion; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Long getIdGerenteUsuario() { return idGerenteUsuario; }
    public void setIdGerenteUsuario(Long idGerenteUsuario) { this.idGerenteUsuario = idGerenteUsuario; }

    public String getNombreGerente() { return nombreGerente; }
    public void setNombreGerente(String nombreGerente) { this.nombreGerente = nombreGerente; }

    public String getTrigramaGerente() { return trigramaGerente; }
    public void setTrigramaGerente(String trigramaGerente) { this.trigramaGerente = trigramaGerente; }

    public Long getIdgerenciaPadre() { return idgerenciaPadre; }
    public void setIdgerenciaPadre(Long idgerenciaPadre) { this.idgerenciaPadre = idgerenciaPadre; }

    public String getNombreGerenciaPadre() { return nombreGerenciaPadre; }
    public void setNombreGerenciaPadre(String nombreGerenciaPadre) { this.nombreGerenciaPadre = nombreGerenciaPadre; }

    public String getAbreviacionGerenciaPadre() { return abreviacionGerenciaPadre; }
    public void setAbreviacionGerenciaPadre(String abreviacionGerenciaPadre) { this.abreviacionGerenciaPadre = abreviacionGerenciaPadre; }

    public Instant getFccreacion() { return fccreacion; }
    public void setFccreacion(Instant fccreacion) { this.fccreacion = fccreacion; }

    public Instant getFcmodificacion() { return fcmodificacion; }
    public void setFcmodificacion(Instant fcmodificacion) { this.fcmodificacion = fcmodificacion; }

    public Instant getFceliminacion() { return fceliminacion; }
    public void setFceliminacion(Instant fceliminacion) { this.fceliminacion = fceliminacion; }
}
