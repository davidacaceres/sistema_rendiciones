package com.personal.rendiciones.administracion.model;

import com.personal.rendiciones.common.model.BaseEntidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "subvista")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubVista extends BaseEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsubvista")
    private Long idsubvista;

    @Column(nullable = false)
    private String nombre; // Ej: Usuarios y Roles

    @Column(nullable = false)
    private String ruta; // Ej: /admin/users

    @Column(nullable = false)
    private String permiso; // Ej: ADMINISTRADOR

    @Column(name = "blconfiguracion", nullable = false)
    private boolean blconfiguracion; // Indica si se muestra en el menú sistémico de Administración

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmodulo")
    @JsonIgnore
    private Modulo modulo;
}
