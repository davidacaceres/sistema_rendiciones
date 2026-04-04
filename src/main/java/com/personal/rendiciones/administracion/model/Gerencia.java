package com.personal.rendiciones.administracion.model;

import com.personal.rendiciones.common.model.BaseEntidad;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa una unidad organizacional (Gerencia o Área) del sistema.
 * Soporta jerarquía mediante gerenciaPadre (auto-referencial) y
 * tiene un usuario responsable designado como gerente.
 */
@Entity
@Table(name = "gerencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Gerencia extends BaseEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgerencia")
    private Long idgerencia;

    public Long getIdgerencia() {
        return idgerencia;
    }

    public void setIdgerencia(Long idgerencia) {
        this.idgerencia = idgerencia;
    }

    @Column(nullable = false, length = 10)
    private String abreviacion; // Ej: TI, HR, FIN

    @Column(nullable = false)
    private String nombre; // Ej: Tecnología de Información

    @Column(name = "blactivo", nullable = false)
    private boolean blactivo;

    /** Usuario responsable de esta gerencia. Debe pertenecer a la misma gerencia. Nullable. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario_gerente", nullable = true)
    private Usuario gerente;

    /**
     * Gerencia padre en la jerarquía organizacional.
     * Null indica que es una gerencia raíz.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idgerencia_padre", nullable = true)
    private Gerencia gerenciaPadre;

    public String getAbreviacion() {
        return abreviacion;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
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

    public Usuario getGerente() {
        return gerente;
    }

    public void setGerente(Usuario gerente) {
        this.gerente = gerente;
    }

    public Gerencia getGerenciaPadre() {
        return gerenciaPadre;
    }

    public void setGerenciaPadre(Gerencia gerenciaPadre) {
        this.gerenciaPadre = gerenciaPadre;
    }
}
