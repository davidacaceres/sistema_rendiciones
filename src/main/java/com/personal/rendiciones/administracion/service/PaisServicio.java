package com.personal.rendiciones.administracion.service;

import com.personal.rendiciones.administracion.dto.PaisDTO;
import com.personal.rendiciones.administracion.model.Pais;
import com.personal.rendiciones.administracion.repository.PaisRepository;
import com.personal.rendiciones.administracion.repository.CiudadRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaisServicio {

    private final PaisRepository paisRepository;
    private final CiudadRepository ciudadRepository;

    public PaisServicio(PaisRepository paisRepository, CiudadRepository ciudadRepository) {
        this.paisRepository = paisRepository;
        this.ciudadRepository = ciudadRepository;
    }

    @Transactional(readOnly = true)
    public Page<PaisDTO> listar(Specification<Pais> spec, Pageable pageable) {
        Specification<Pais> noEliminadosSpec = (root, query, cb) -> cb.isNull(root.get("fceliminacion"));
        return paisRepository.findAll(Specification.where(spec).and(noEliminadosSpec), pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public Optional<PaisDTO> buscarPorId(Long idpais) {
        return paisRepository.findByIdpaisAndFceliminacionIsNull(idpais).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public List<PaisDTO> listarTodosActivos() {
        return paisRepository.findAllByFceliminacionIsNullOrderByNombreAsc().stream()
                .filter(Pais::isLgactivo)
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public PaisDTO guardar(PaisDTO dto) {
        if (dto.getCodigopais() != null) {
            dto.setCodigopais(dto.getCodigopais().trim().toUpperCase());
        }
        
        validarUnicoCodigo(dto);
        validarUnicoNacional(dto);

        Pais pais;
        if (dto.getIdpais() == null) {
            pais = new Pais();
        } else {
            pais = paisRepository.findById(dto.getIdpais())
                    .orElseThrow(() -> new IllegalArgumentException("PAIS_NOT_FOUND"));
        }

        pais.setNombre(dto.getNombre());
        pais.setCodigopais(dto.getCodigopais());
        pais.setLgnacional(dto.isLgnacional());
        pais.setLgactivo(dto.isLgactivo());

        return toDTO(paisRepository.save(pais));
    }

    private void validarUnicoCodigo(PaisDTO dto) {
        if (dto.getCodigopais() == null || dto.getCodigopais().trim().isEmpty()) {
            throw new IllegalArgumentException("CODIGO_PAIS_REQUERIDO");
        }
        paisRepository.findByCodigopaisAndFceliminacionIsNull(dto.getCodigopais())
            .ifPresent(existente -> {
                if (dto.getIdpais() == null || !existente.getIdpais().equals(dto.getIdpais())) {
                    throw new IllegalArgumentException("CODIGO_PAIS_DUPLICADO");
                }
            });
    }

    private void validarUnicoNacional(PaisDTO dto) {
        if (dto.isLgnacional()) {
            paisRepository.findByLgnacionalIsTrueAndFceliminacionIsNull().ifPresent(existente -> {
                if (dto.getIdpais() == null || !existente.getIdpais().equals(dto.getIdpais())) {
                    // Si se marca uno nuevo como nacional, el anterior deja de serlo
                    existente.setLgnacional(false);
                    paisRepository.save(existente);
                }
            });
        }
    }

    @Transactional
    public void eliminarLogicamente(Long idpais) {
        Pais pais = paisRepository.findById(idpais)
                .orElseThrow(() -> new IllegalArgumentException("PAIS_NOT_FOUND"));

        long ciudadesActivas = ciudadRepository.countByPaisAndFceliminacionIsNullAndLgactivoIsTrue(pais);
        if (ciudadesActivas > 0) {
            throw new IllegalArgumentException("PAIS_CON_CIUDADES_ACTIVAS");
        }

        pais.setFceliminacion(Instant.now());
        pais.setLgactivo(false);
        paisRepository.save(pais);
    }

    @Transactional
    public void cambiarEstado(Long idpais, boolean activo) {
        paisRepository.findById(idpais).ifPresent(p -> {
            p.setLgactivo(activo);
            paisRepository.save(p);
        });
    }

    public PaisDTO toDTO(Pais p) {
        if (p == null) return null;
        return PaisDTO.builder()
                .idpais(p.getIdpais())
                .nombre(p.getNombre())
                .codigopais(p.getCodigopais())
                .lgnacional(p.isLgnacional())
                .lgactivo(p.isLgactivo())
                .fccreacion(p.getFccreacion())
                .fcmodificacion(p.getFcmodificacion())
                .fceliminacion(p.getFceliminacion())
                .build();
    }
}
