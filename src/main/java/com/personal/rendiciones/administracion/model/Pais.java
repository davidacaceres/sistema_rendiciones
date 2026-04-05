package com.personal.rendiciones.administracion.model;

import com.personal.rendiciones.common.model.BaseEntidad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pais")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pais extends BaseEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idpais;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 10)
    private String codigopais;

    @Column(nullable = false)
    private boolean lgnacional;

    @Column(nullable = false)
    private boolean lgactivo;

    // Métodos manuales para asegurar compatibilidad si Lombok falla (visto en conversaciones previas)
    public Long getIdpais() { return idpais; }
    public void setIdpais(Long idpais) { this.idpais = idpais; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCodigopais() { return codigopais; }
    public void setCodigopais(String codigopais) { this.codigopais = codigopais; }
    public boolean isLgnacional() { return lgnacional; }
    public void setLgnacional(boolean lgnacional) { this.lgnacional = lgnacional; }
    public boolean isLgactivo() { return lgactivo; }
    public void setLgactivo(boolean lgactivo) { this.lgactivo = lgactivo; }
}
