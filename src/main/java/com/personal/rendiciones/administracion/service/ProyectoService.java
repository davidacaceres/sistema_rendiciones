package com.personal.rendiciones.administracion.service;

import com.personal.rendiciones.administracion.dto.ProyectoDTO;
import com.personal.rendiciones.administracion.model.Gerencia;
import com.personal.rendiciones.administracion.model.Proyecto;
import com.personal.rendiciones.administracion.repository.GerenciaRepository;
import com.personal.rendiciones.administracion.repository.ProyectoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final GerenciaRepository gerenciaRepository;
    private static final String ERROR_PROYECTO_NO_ENCONTRADO = "Proyecto no encontrado";

    public ProyectoService(ProyectoRepository proyectoRepository, GerenciaRepository gerenciaRepository) {
        this.proyectoRepository = proyectoRepository;
        this.gerenciaRepository = gerenciaRepository;
    }


    public Page<ProyectoDTO> listarProyectos(String search, Long filterGerencia, Boolean filterActivo, Pageable pageable) {
        Specification<Proyecto> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.isNull(root.get("fceliminacion")));
            
            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search.trim().toLowerCase() + "%";
                predicates.add(cb.or(
                    cb.like(cb.lower(root.get("nombre")), searchPattern),
                    cb.like(cb.lower(root.get("identificador")), searchPattern)
                ));
            }
            
            if (filterGerencia != null) {
                predicates.add(cb.equal(root.join("gerencia").get("idgerencia"), filterGerencia));
            }
            
            if (filterActivo != null) {
                predicates.add(cb.equal(root.get("blactivo"), filterActivo));
            }
            
            return cb.and(predicates.toArray(Predicate[]::new));
        };
        
        return proyectoRepository.findAll(spec, pageable).map(this::mapToDTO);
    }

    @Transactional
    public ProyectoDTO crearProyecto(ProyectoDTO dto) {
        if (proyectoRepository.existsByIdentificadorAndFceliminacionIsNull(dto.getIdentificador())) {
            throw new IllegalArgumentException("Ya existe un proyecto con este identificador: " + dto.getIdentificador());
        }
        
        if (proyectoRepository.existsByNombreAndFceliminacionIsNull(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un proyecto con este nombre: " + dto.getNombre());
        }

        Proyecto proyecto = new Proyecto();
        mapToEntity(dto, proyecto);
        proyecto.setFccreacion(Instant.now());
        proyecto = proyectoRepository.save(proyecto);
        return mapToDTO(proyecto);
    }

    @Transactional
    public ProyectoDTO actualizarProyecto(Long id, ProyectoDTO dto) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ERROR_PROYECTO_NO_ENCONTRADO));

        if (!proyecto.getIdentificador().equals(dto.getIdentificador()) &&
                proyectoRepository.existsByIdentificadorAndFceliminacionIsNull(dto.getIdentificador())) {
            throw new IllegalArgumentException("Ya existe un proyecto con este identificador: " + dto.getIdentificador());
        }
        
        if (!proyecto.getNombre().equalsIgnoreCase(dto.getNombre())) {
            Page<Proyecto> proyectosMismoNombre = proyectoRepository.findByNombreContainingIgnoreCase(dto.getNombre(), Pageable.unpaged());
            boolean nombreExiste = proyectosMismoNombre.stream()
                    .anyMatch(p -> p.getNombre().equalsIgnoreCase(dto.getNombre()) && !p.getIdproyecto().equals(id));
            if (nombreExiste) {
                throw new IllegalArgumentException("Ya existe un proyecto con este nombre: " + dto.getNombre());
            }
        }

        mapToEntity(dto, proyecto);
        proyecto.setFcmodificacion(Instant.now());
        proyecto = proyectoRepository.save(proyecto);
        return mapToDTO(proyecto);
    }

    @Transactional
    public void eliminarProyecto(Long id) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ERROR_PROYECTO_NO_ENCONTRADO));
        proyecto.setFceliminacion(Instant.now());
        proyecto.setBlactivo(false);
        proyectoRepository.save(proyecto);
    }

    @Transactional
    public ProyectoDTO toggleActivo(Long id) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ERROR_PROYECTO_NO_ENCONTRADO));
        proyecto.setBlactivo(!proyecto.isBlactivo());
        proyecto.setFcmodificacion(Instant.now());
        proyecto = proyectoRepository.save(proyecto);
        return mapToDTO(proyecto);
    }

    private ProyectoDTO mapToDTO(Proyecto proyecto) {
        ProyectoDTO dto = new ProyectoDTO();
        dto.setIdproyecto(proyecto.getIdproyecto());
        dto.setIdentificador(proyecto.getIdentificador());
        dto.setNombre(proyecto.getNombre());
        dto.setBlactivo(proyecto.isBlactivo());
        if (proyecto.getGerencia() != null) {
            dto.setIdgerencia(proyecto.getGerencia().getIdgerencia());
            dto.setNombreGerencia(proyecto.getGerencia().getNombre());
        }
        dto.setFccreacion(proyecto.getFccreacion());
        dto.setFcmodificacion(proyecto.getFcmodificacion());
        return dto;
    }

    private void mapToEntity(ProyectoDTO dto, Proyecto proyecto) {
        proyecto.setIdentificador(dto.getIdentificador());
        proyecto.setNombre(dto.getNombre());
        proyecto.setBlactivo(dto.isBlactivo());
        if (dto.getIdgerencia() != null) {
            Gerencia gerencia = gerenciaRepository.findById(dto.getIdgerencia())
                    .orElseThrow(() -> new EntityNotFoundException("Gerencia no encontrada"));
            proyecto.setGerencia(gerencia);
        } else {
            proyecto.setGerencia(null);
        }
    }
}
