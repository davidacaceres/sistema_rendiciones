package com.personal.rendiciones.administracion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuloDTO {
    private Long idmodulo;
    private String nombre;
    private String icono;
    private Integer orden;
    private List<SubVistaDTO> subvistas;
    private Instant fccreacion;
    private Instant fcmodificacion;
    private Instant fceliminacion;
}
