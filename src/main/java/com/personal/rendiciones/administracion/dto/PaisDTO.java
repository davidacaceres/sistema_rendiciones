package com.personal.rendiciones.administracion.dto;

import java.time.Instant;

public class PaisDTO {
    private Long idpais;
    private String nombre;
    private String codigopais;
    private boolean lgnacional;
    private boolean lgactivo;
    private Instant fccreacion;
    private Instant fcmodificacion;
    private Instant fceliminacion;

    public PaisDTO() {}

    public PaisDTO(Long idpais, String nombre, String codigopais, boolean lgnacional, boolean lgactivo, 
                   Instant fccreacion, Instant fcmodificacion, Instant fceliminacion) {
        this.idpais = idpais;
        this.nombre = nombre;
        this.codigopais = codigopais;
        this.lgnacional = lgnacional;
        this.lgactivo = lgactivo;
        this.fccreacion = fccreacion;
        this.fcmodificacion = fcmodificacion;
        this.fceliminacion = fceliminacion;
    }

    public static PaisDTOBuilder builder() {
        return new PaisDTOBuilder();
    }

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
    public Instant getFccreacion() { return fccreacion; }
    public void setFccreacion(Instant fccreacion) { this.fccreacion = fccreacion; }
    public Instant getFcmodificacion() { return fcmodificacion; }
    public void setFcmodificacion(Instant fcmodificacion) { this.fcmodificacion = fcmodificacion; }
    public Instant getFceliminacion() { return fceliminacion; }
    public void setFceliminacion(Instant fceliminacion) { this.fceliminacion = fceliminacion; }

    public static class PaisDTOBuilder {
        private Long idpais;
        private String nombre;
        private String codigopais;
        private boolean lgnacional;
        private boolean lgactivo;
        private Instant fccreacion;
        private Instant fcmodificacion;
        private Instant fceliminacion;

        public PaisDTOBuilder idpais(Long idpais) { this.idpais = idpais; return this; }
        public PaisDTOBuilder nombre(String nombre) { this.nombre = nombre; return this; }
        public PaisDTOBuilder codigopais(String codigopais) { this.codigopais = codigopais; return this; }
        public PaisDTOBuilder lgnacional(boolean lgnacional) { this.lgnacional = lgnacional; return this; }
        public PaisDTOBuilder lgactivo(boolean lgactivo) { this.lgactivo = lgactivo; return this; }
        public PaisDTOBuilder fccreacion(Instant fccreacion) { this.fccreacion = fccreacion; return this; }
        public PaisDTOBuilder fcmodificacion(Instant fcmodificacion) { this.fcmodificacion = fcmodificacion; return this; }
        public PaisDTOBuilder fceliminacion(Instant fceliminacion) { this.fceliminacion = fceliminacion; return this; }
        public PaisDTO build() {
            return new PaisDTO(idpais, nombre, codigopais, lgnacional, lgactivo, fccreacion, fcmodificacion, fceliminacion);
        }
    }
}
