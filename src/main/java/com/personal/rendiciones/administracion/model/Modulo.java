package com.personal.rendiciones.administracion.model;

import com.personal.rendiciones.common.model.BaseEntidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "modulo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Modulo extends BaseEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmodulo")
    private Long idmodulo;

    @Column(nullable = false)
    private String nombre; // Ej: Administración

    @Column(nullable = false)
    private String icono; // Ej: settings

    @Column(nullable = false)
    private Integer orden; // Posición en el menú

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubVista> subvistas;
}
