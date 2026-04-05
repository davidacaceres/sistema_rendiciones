package com.personal.rendiciones.administracion.model;

import com.personal.rendiciones.common.model.BaseEntidad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "ciudad")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ciudad extends BaseEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idciudad;

    @Column(nullable = false, length = 100)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpais", nullable = false)
    private Pais pais;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal viaticoger;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal viaticojpr;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal viaticojar;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal viaticotec;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal viaticoaux;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal viaticoing;

    @Column(nullable = false)
    private boolean lgactivo;

    // Métodos manuales
    public Long getIdciudad() { return idciudad; }
    public void setIdciudad(Long idciudad) { this.idciudad = idciudad; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Pais getPais() { return pais; }
    public void setPais(Pais pais) { this.pais = pais; }
    public BigDecimal getViaticoger() { return viaticoger; }
    public void setViaticoger(BigDecimal viaticoger) { this.viaticoger = viaticoger; }
    public BigDecimal getViaticojpr() { return viaticojpr; }
    public void setViaticojpr(BigDecimal viaticojpr) { this.viaticojpr = viaticojpr; }
    public BigDecimal getViaticojar() { return viaticojar; }
    public void setViaticojar(BigDecimal viaticojar) { this.viaticojar = viaticojar; }
    public BigDecimal getViaticotec() { return viaticotec; }
    public void setViaticotec(BigDecimal viaticotec) { this.viaticotec = viaticotec; }
    public BigDecimal getViaticoaux() { return viaticoaux; }
    public void setViaticoaux(BigDecimal viaticoaux) { this.viaticoaux = viaticoaux; }
    public BigDecimal getViaticoing() { return viaticoing; }
    public void setViaticoing(BigDecimal viaticoing) { this.viaticoing = viaticoing; }
    public boolean isLgactivo() { return lgactivo; }
    public void setLgactivo(boolean lgactivo) { this.lgactivo = lgactivo; }
}
