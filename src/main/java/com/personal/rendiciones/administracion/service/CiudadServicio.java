package com.personal.rendiciones.administracion.service;

import com.personal.rendiciones.administracion.dto.CiudadDTO;
import com.personal.rendiciones.administracion.model.Ciudad;
import com.personal.rendiciones.administracion.model.Pais;
import com.personal.rendiciones.administracion.repository.CiudadRepository;
import com.personal.rendiciones.administracion.repository.PaisRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CiudadServicio {

    private final CiudadRepository ciudadRepository;
    private final PaisRepository paisRepository;

    public CiudadServicio(CiudadRepository ciudadRepository, PaisRepository paisRepository) {
        this.ciudadRepository = ciudadRepository;
        this.paisRepository = paisRepository;
    }

    @Transactional(readOnly = true)
    public Page<CiudadDTO> listar(Specification<Ciudad> spec, Pageable pageable) {
        // Corregir ordenamiento si es por campos virtuales del DTO
        Pageable adaptedPageable = adaptPageable(pageable);
        
        Specification<Ciudad> noEliminadosSpec = (root, query, cb) -> cb.isNull(root.get("fceliminacion"));
        return ciudadRepository.findAll(Specification.where(spec).and(noEliminadosSpec), adaptedPageable).map(this::toDTO);
    }

    private Pageable adaptPageable(Pageable pageable) {
        Sort sort = pageable.getSort();
        if (sort.isUnsorted()) {
            sort = Sort.by(Sort.Order.asc("pais.nombre"), Sort.Order.asc("nombre"));
        } else {
            List<Sort.Order> orders = sort.stream()
                .filter(order -> !"asc".equalsIgnoreCase(order.getProperty()) && !"desc".equalsIgnoreCase(order.getProperty()))
                .map(order -> {
                    if ("nombrepais".equals(order.getProperty())) {
                        return new Sort.Order(order.getDirection(), "pais.nombre");
                    }
                    if ("codigopais".equals(order.getProperty())) {
                        return new Sort.Order(order.getDirection(), "pais.codigopais");
                    }
                    return order;
                })
                .toList();
            sort = Sort.by(orders);
        }

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }

    @Transactional(readOnly = true)
    public Optional<CiudadDTO> buscarPorId(Long idciudad) {
        return ciudadRepository.findByIdciudadAndFceliminacionIsNull(idciudad).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public List<CiudadDTO> listarPorPaisActivo(Long idpais) {
        Pais pais = paisRepository.findById(idpais)
                .orElseThrow(() -> new IllegalArgumentException("PAIS_NOT_FOUND"));
        return ciudadRepository.findAllByPaisAndFceliminacionIsNullOrderByNombreAsc(pais).stream()
                .filter(Ciudad::isLgactivo)
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public CiudadDTO guardar(CiudadDTO dto) {
        validarMontos(dto);
        Pais pais = paisRepository.findById(dto.getIdpais())
                .orElseThrow(() -> new IllegalArgumentException("PAIS_NOT_FOUND"));

        if (!pais.isLgactivo()) {
            throw new IllegalArgumentException("PAIS_INACTIVO");
        }

        Ciudad ciudad;
        if (dto.getIdciudad() == null) {
            ciudad = new Ciudad();
        } else {
            ciudad = ciudadRepository.findById(dto.getIdciudad())
                    .orElseThrow(() -> new IllegalArgumentException("CIUDAD_NOT_FOUND"));
        }

        ciudad.setNombre(dto.getNombre());
        ciudad.setPais(pais);
        ciudad.setViaticoger(dto.getViaticoger());
        ciudad.setViaticojpr(dto.getViaticojpr());
        ciudad.setViaticojar(dto.getViaticojar());
        ciudad.setViaticotec(dto.getViaticotec());
        ciudad.setViaticoaux(dto.getViaticoaux());
        ciudad.setViaticoing(dto.getViaticoing());
        ciudad.setLgactivo(dto.isLgactivo());

        return toDTO(ciudadRepository.save(ciudad));
    }

    private void validarMontos(CiudadDTO dto) {
        if (dto.getViaticoger().compareTo(BigDecimal.ZERO) < 0 ||
                dto.getViaticojpr().compareTo(BigDecimal.ZERO) < 0 ||
                dto.getViaticojar().compareTo(BigDecimal.ZERO) < 0 ||
                dto.getViaticotec().compareTo(BigDecimal.ZERO) < 0 ||
                dto.getViaticoaux().compareTo(BigDecimal.ZERO) < 0 ||
                dto.getViaticoing().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("VIATICO_NEGATIVO");
        }
    }

    @Transactional
    public void eliminarLogicamente(Long idciudad) {
        ciudadRepository.findById(idciudad).ifPresent(c -> {
            c.setFceliminacion(Instant.now());
            c.setLgactivo(false);
            ciudadRepository.save(c);
        });
    }

    @Transactional
    public void cambiarEstado(Long idciudad, boolean activo) {
        ciudadRepository.findById(idciudad).ifPresent(c -> {
            c.setLgactivo(activo);
            ciudadRepository.save(c);
        });
    }

    public CiudadDTO toDTO(Ciudad c) {
        if (c == null) return null;
        return CiudadDTO.builder()
                .idciudad(c.getIdciudad())
                .nombre(c.getNombre())
                .idpais(c.getPais().getIdpais())
                .nombrepais(c.getPais().getNombre())
                .codigopais(c.getPais().getCodigopais())
                .lgnacionalpais(c.getPais().isLgnacional())
                .viaticoger(c.getViaticoger())
                .viaticojpr(c.getViaticojpr())
                .viaticojar(c.getViaticojar())
                .viaticotec(c.getViaticotec())
                .viaticoaux(c.getViaticoaux())
                .viaticoing(c.getViaticoing())
                .lgactivo(c.isLgactivo())
                .fccreacion(c.getFccreacion())
                .fcmodificacion(c.getFcmodificacion())
                .fceliminacion(c.getFceliminacion())
                .build();
    }
}
