package com.personal.rendiciones.administracion.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class CiudadDTO {
    private Long idciudad;
    private String nombre;
    private Long idpais;
    private String nombrepais;
    private String codigopais;
    private boolean lgnacionalpais;
    private BigDecimal viaticoger;
    private BigDecimal viaticojpr;
    private BigDecimal viaticojar;
    private BigDecimal viaticotec;
    private BigDecimal viaticoaux;
    private BigDecimal viaticoing;
    private boolean lgactivo;
    private Instant fccreacion;
    private Instant fcmodificacion;
    private Instant fceliminacion;

    public CiudadDTO() {}

    public CiudadDTO(Long idciudad, String nombre, Long idpais, String nombrepais, String codigopais, boolean lgnacionalpais, 
                     BigDecimal viaticoger, BigDecimal viaticojpr, BigDecimal viaticojar, 
                     BigDecimal viaticotec, BigDecimal viaticoaux, BigDecimal viaticoing, 
                     boolean lgactivo, Instant fccreacion, Instant fcmodificacion, Instant fceliminacion) {
        this.idciudad = idciudad;
        this.nombre = nombre;
        this.idpais = idpais;
        this.nombrepais = nombrepais;
        this.codigopais = codigopais;
        this.lgnacionalpais = lgnacionalpais;
        this.viaticoger = viaticoger;
        this.viaticojpr = viaticojpr;
        this.viaticojar = viaticojar;
        this.viaticotec = viaticotec;
        this.viaticoaux = viaticoaux;
        this.viaticoing = viaticoing;
        this.lgactivo = lgactivo;
        this.fccreacion = fccreacion;
        this.fcmodificacion = fcmodificacion;
        this.fceliminacion = fceliminacion;
    }

    public static CiudadDTOBuilder builder() {
        return new CiudadDTOBuilder();
    }

    // Getters and Setters
    public Long getIdciudad() { return idciudad; }
    public void setIdciudad(Long idciudad) { this.idciudad = idciudad; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Long getIdpais() { return idpais; }
    public void setIdpais(Long idpais) { this.idpais = idpais; }
    public String getNombrepais() { return nombrepais; }
    public void setNombrepais(String nombrepais) { this.nombrepais = nombrepais; }
    public String getCodigopais() { return codigopais; }
    public void setCodigopais(String codigopais) { this.codigopais = codigopais; }
    public boolean isLgnacionalpais() { return lgnacionalpais; }
    public void setLgnacionalpais(boolean lgnacionalpais) { this.lgnacionalpais = lgnacionalpais; }
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
    public Instant getFccreacion() { return fccreacion; }
    public void setFccreacion(Instant fccreacion) { this.fccreacion = fccreacion; }
    public Instant getFcmodificacion() { return fcmodificacion; }
    public void setFcmodificacion(Instant fcmodificacion) { this.fcmodificacion = fcmodificacion; }
    public Instant getFceliminacion() { return fceliminacion; }
    public void setFceliminacion(Instant fceliminacion) { this.fceliminacion = fceliminacion; }

    public static class CiudadDTOBuilder {
        private Long idciudad;
        private String nombre;
        private Long idpais;
        private String nombrepais;
        private String codigopais;
        private boolean lgnacionalpais;
        private BigDecimal viaticoger;
        private BigDecimal viaticojpr;
        private BigDecimal viaticojar;
        private BigDecimal viaticotec;
        private BigDecimal viaticoaux;
        private BigDecimal viaticoing;
        private boolean lgactivo;
        private Instant fccreacion;
        private Instant fcmodificacion;
        private Instant fceliminacion;

        public CiudadDTOBuilder idciudad(Long idciudad) { this.idciudad = idciudad; return this; }
        public CiudadDTOBuilder nombre(String nombre) { this.nombre = nombre; return this; }
        public CiudadDTOBuilder idpais(Long idpais) { this.idpais = idpais; return this; }
        public CiudadDTOBuilder nombrepais(String nombrepais) { this.nombrepais = nombrepais; return this; }
        public CiudadDTOBuilder codigopais(String codigopais) { this.codigopais = codigopais; return this; }
        public CiudadDTOBuilder lgnacionalpais(boolean lgnacionalpais) { this.lgnacionalpais = lgnacionalpais; return this; }
        public CiudadDTOBuilder viaticoger(BigDecimal viaticoger) { this.viaticoger = viaticoger; return this; }
        public CiudadDTOBuilder viaticojpr(BigDecimal viaticojpr) { this.viaticojpr = viaticojpr; return this; }
        public CiudadDTOBuilder viaticojar(BigDecimal viaticojar) { this.viaticojar = viaticojar; return this; }
        public CiudadDTOBuilder viaticotec(BigDecimal viaticotec) { this.viaticotec = viaticotec; return this; }
        public CiudadDTOBuilder viaticoaux(BigDecimal viaticoaux) { this.viaticoaux = viaticoaux; return this; }
        public CiudadDTOBuilder viaticoing(BigDecimal viaticoing) { this.viaticoing = viaticoing; return this; }
        public CiudadDTOBuilder lgactivo(boolean lgactivo) { this.lgactivo = lgactivo; return this; }
        public CiudadDTOBuilder fccreacion(Instant fccreacion) { this.fccreacion = fccreacion; return this; }
        public CiudadDTOBuilder fcmodificacion(Instant fcmodificacion) { this.fcmodificacion = fcmodificacion; return this; }
        public CiudadDTOBuilder fceliminacion(Instant fceliminacion) { this.fceliminacion = fceliminacion; return this; }
        public CiudadDTO build() {
            return new CiudadDTO(idciudad, nombre, idpais, nombrepais, codigopais, lgnacionalpais, viaticoger, 
                                 viaticojpr, viaticojar, viaticotec, viaticoaux, viaticoing, 
                                 lgactivo, fccreacion, fcmodificacion, fceliminacion);
        }
    }
}
