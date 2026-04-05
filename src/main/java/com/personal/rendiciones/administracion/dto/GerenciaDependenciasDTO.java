package com.personal.rendiciones.administracion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GerenciaDependenciasDTO {
    private long usuariosCount;
    private long proyectosCount;

    public boolean hatDependencias() {
        return usuariosCount > 0 || proyectosCount > 0;
    }
}
