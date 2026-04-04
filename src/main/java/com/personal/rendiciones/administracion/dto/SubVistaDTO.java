package com.personal.rendiciones.administracion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubVistaDTO {
    private Long idsubvista;
    private String nombre;
    private String ruta;
    private String permiso;
    private boolean blconfiguracion;
    private Instant fccreacion;
    private Instant fcmodificacion;
    private Instant fceliminacion;
}
