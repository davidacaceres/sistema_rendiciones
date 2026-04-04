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

    public GerenciaDependenciasDTO() {
    }

    public GerenciaDependenciasDTO(long usuariosCount, long proyectosCount) {
        this.usuariosCount = usuariosCount;
        this.proyectosCount = proyectosCount;
    }

    public long getUsuariosCount() {
        return usuariosCount;
    }

    public void setUsuariosCount(long usuariosCount) {
        this.usuariosCount = usuariosCount;
    }

    public long getProyectosCount() {
        return proyectosCount;
    }

    public void setProyectosCount(long proyectosCount) {
        this.proyectosCount = proyectosCount;
    }

    public boolean hatDependencias() {
        return usuariosCount > 0 || proyectosCount > 0;
    }
}
