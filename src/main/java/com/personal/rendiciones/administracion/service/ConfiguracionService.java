package com.personal.rendiciones.administracion.service;

import com.personal.rendiciones.administracion.dto.ModuloDTO;
import com.personal.rendiciones.administracion.dto.SubVistaDTO;
import com.personal.rendiciones.administracion.model.Modulo;
import com.personal.rendiciones.administracion.model.SubVista;
import com.personal.rendiciones.administracion.repository.ModuloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConfiguracionService {

    private final ModuloRepository moduloRepository;

    @Transactional(readOnly = true)
    public List<ModuloDTO> getMenu() {
        return moduloRepository.findAllByOrderByOrdenAsc()
                .stream()
                .map(this::toModuloDTO)
                .collect(Collectors.toList());
    }

    private ModuloDTO toModuloDTO(Modulo m) {
        if (m == null) return null;
        return ModuloDTO.builder()
                .idmodulo(m.getIdmodulo())
                .nombre(m.getNombre())
                .icono(m.getIcono())
                .orden(m.getOrden())
                .subvistas(m.getSubvistas() != null ? 
                    m.getSubvistas().stream().map(this::toSubVistaDTO).collect(Collectors.toList()) : 
                    null)
                .build();
    }

    private SubVistaDTO toSubVistaDTO(SubVista s) {
        if (s == null) return null;
        return SubVistaDTO.builder()
                .idsubvista(s.getIdsubvista())
                .nombre(s.getNombre())
                .ruta(s.getRuta())
                .permiso(s.getPermiso())
                .blconfiguracion(s.isBlconfiguracion())
                .build();
    }
}

