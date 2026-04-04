package com.personal.rendiciones.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * Clase base para todas las entidades del sistema que requieren auditoría automática.
 * Gestiona las fechas de creación y modificación en formato UTC.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntidad {

    @CreatedDate
    @Column(name = "fccreacion", nullable = false, updatable = false)
    private Instant fccreacion;

    @LastModifiedDate
    @Column(name = "fcmodificacion")
    private Instant fcmodificacion;

    @Column(name = "fceliminacion")
    private Instant fceliminacion;

    public Instant getFccreacion() { return fccreacion; }
    public void setFccreacion(Instant fccreacion) { this.fccreacion = fccreacion; }

    public Instant getFcmodificacion() { return fcmodificacion; }
    public void setFcmodificacion(Instant fcmodificacion) { this.fcmodificacion = fcmodificacion; }

    public Instant getFceliminacion() { return fceliminacion; }
    public void setFceliminacion(Instant fceliminacion) { this.fceliminacion = fceliminacion; }
}
