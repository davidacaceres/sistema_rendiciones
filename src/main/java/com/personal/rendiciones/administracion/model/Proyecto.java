package com.personal.rendiciones.administracion.model;

import com.personal.rendiciones.common.model.BaseEntidad;
import jakarta.persistence.*;

/**
 * Representa un Proyecto Institucional.
 */
@Entity
@Table(name = "proyecto")
public class Proyecto extends BaseEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproyecto")
    private Long idproyecto;

    @Column(name = "identificador", nullable = false, unique = true, length = 10)
    private String identificador;

    @Column(nullable = false, unique = true, length = 200)
    private String nombre;

    @Column(name = "blactivo", nullable = false)
    private boolean blactivo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idgerencia", nullable = true)
    private Gerencia gerencia;

    public Proyecto() {}

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

    public Gerencia getGerencia() {
        return gerencia;
    }

    public void setGerencia(Gerencia gerencia) {
        this.gerencia = gerencia;
    }
}
