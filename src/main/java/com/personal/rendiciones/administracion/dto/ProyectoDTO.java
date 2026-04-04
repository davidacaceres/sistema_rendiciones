package com.personal.rendiciones.administracion.dto;

import java.time.Instant;

public class ProyectoDTO {
    private Long idproyecto;
    @jakarta.validation.constraints.Size(max = 10, message = "El identificador no puede exceder los 10 caracteres")
    private String identificador;
    private String nombre;
    private boolean blactivo;
    private Long idgerencia;
    private String nombreGerencia;
    
    // Auditing fields
    private Instant fccreacion;
    private Instant fcmodificacion;

    public ProyectoDTO() {}

    public Long getIdproyecto() {
        return idproyecto;
    }

    public void setIdproyecto(Long idproyecto) {
        this.idproyecto = idproyecto;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isBlactivo() {
        return blactivo;
    }

    public void setBlactivo(boolean blactivo) {
        this.blactivo = blactivo;
    }

    public Long getIdgerencia() {
        return idgerencia;
    }

    public void setIdgerencia(Long idgerencia) {
        this.idgerencia = idgerencia;
    }

    public String getNombreGerencia() {
        return nombreGerencia;
    }

    public void setNombreGerencia(String nombreGerencia) {
        this.nombreGerencia = nombreGerencia;
    }

    public Instant getFccreacion() {
        return fccreacion;
    }

    public void setFccreacion(Instant fccreacion) {
        this.fccreacion = fccreacion;
    }

    public Instant getFcmodificacion() {
        return fcmodificacion;
    }

    public void setFcmodificacion(Instant fcmodificacion) {
        this.fcmodificacion = fcmodificacion;
    }
}
