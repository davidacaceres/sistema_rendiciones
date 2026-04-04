package com.personal.rendiciones.administracion.service;

import com.personal.rendiciones.administracion.dto.GerenciaDTO;
import com.personal.rendiciones.administracion.dto.GerenciaDependenciasDTO;
import com.personal.rendiciones.administracion.dto.GerenciaRequestDTO;
import com.personal.rendiciones.administracion.model.Gerencia;
import com.personal.rendiciones.administracion.model.Usuario;
import com.personal.rendiciones.administracion.repository.GerenciaRepository;
import com.personal.rendiciones.administracion.repository.ProyectoRepository;
import com.personal.rendiciones.administracion.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GerenciaServicio {

    private final GerenciaRepository gerenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProyectoRepository proyectoRepository;

    public GerenciaServicio(GerenciaRepository gerenciaRepository,
                             UsuarioRepository usuarioRepository,
                             ProyectoRepository proyectoRepository) {
        this.gerenciaRepository = gerenciaRepository;
        this.usuarioRepository = usuarioRepository;
        this.proyectoRepository = proyectoRepository;
    }

    // -------------------------------------------------------------------------
    // Consultas
    // -------------------------------------------------------------------------

    @Transactional(readOnly = true)
    public GerenciaDependenciasDTO obtenerDependencias(Long idgerencia) {
        return gerenciaRepository.findById(idgerencia).map(g -> {
            long usuarios = usuarioRepository.countByGerenciaAndFceliminacionIsNull(g);
            long proyectos = proyectoRepository.countByGerenciaAndFceliminacionIsNull(g);
            return new GerenciaDependenciasDTO(usuarios, proyectos);
        }).orElse(new GerenciaDependenciasDTO(0, 0));
    }

    @Transactional(readOnly = true)
    public Page<GerenciaDTO> listar(String search, Pageable pageable) {
        Specification<Gerencia> spec = (root, query, cb) -> {
            var pred = cb.isNull(root.get("fceliminacion"));
            if (search != null && !search.isEmpty()) {
                var searchLower = "%" + search.toLowerCase() + "%";
                pred = cb.and(pred, cb.or(
                        cb.like(cb.lower(root.get("abreviacion")), searchLower),
                        cb.like(cb.lower(root.get("nombre")), searchLower)
                ));
            }
            return pred;
        };
        return gerenciaRepository.findAll(spec, pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public GerenciaDTO obtenerPorId(Long idgerencia) {
        return gerenciaRepository.findByIdgerenciaAndFceliminacionIsNull(idgerencia)
                .map(this::toDTO)
                .orElse(null);
    }

    /**
     * Lista todas las gerencias activas para el selector de gerencia padre.
     * Excluye la gerencia indicada (para evitar auto-referencia directa).
     */
    @Transactional(readOnly = true)
    public List<GerenciaDTO> listarActivasParaSelectorPadre(Long excluirId) {
        return gerenciaRepository.findByFceliminacionIsNullOrderByNombreAsc()
                .stream()
                .filter(g -> !g.getIdgerencia().equals(excluirId))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------------------------
    // Conversión DTO
    // -------------------------------------------------------------------------

    public GerenciaDTO toDTO(Gerencia g) {
        if (g == null) return null;

        GerenciaDTO.GerenciaDTOBuilder builder = GerenciaDTO.builder()
                .idgerencia(g.getIdgerencia())
                .abreviacion(g.getAbreviacion())
                .nombre(g.getNombre())
                .fccreacion(g.getFccreacion())
                .fcmodificacion(g.getFcmodificacion());

        // Mapear gerente (solo campos planos, sin recursión de relaciones)
        if (g.getGerente() != null) {
            Usuario gerente = g.getGerente();
            builder.idGerenteUsuario(gerente.getIdusuario())
                   .nombreGerente(gerente.getNombre())
                   .trigramaGerente(gerente.getTrigrama());
        }

        // Mapear padre (solo campos planos, sin recursión)
        if (g.getGerenciaPadre() != null) {
            Gerencia padre = g.getGerenciaPadre();
            builder.idgerenciaPadre(padre.getIdgerencia())
                   .nombreGerenciaPadre(padre.getNombre())
                   .abreviacionGerenciaPadre(padre.getAbreviacion());
        }

        return builder.build();
    }

    // -------------------------------------------------------------------------
    // Creación y actualización
    // -------------------------------------------------------------------------

    @Transactional
    public Gerencia crear(GerenciaRequestDTO request) {
        Gerencia g = new Gerencia();
        aplicarRequest(g, request);
        return gerenciaRepository.save(g);
    }

    @Transactional
    public Gerencia actualizar(Long idgerencia, GerenciaRequestDTO request) {
        return gerenciaRepository.findById(idgerencia)
                .map(existente -> {
                    aplicarRequest(existente, request);
                    return gerenciaRepository.save(existente);
                }).orElse(null);
    }

    /**
     * Aplica los campos del DTO de request a la entidad Gerencia.
     * Valida que el gerente pertenezca a la misma gerencia.
     */
    private void aplicarRequest(Gerencia g, GerenciaRequestDTO request) {
        g.setAbreviacion(request.getAbreviacion());
        g.setNombre(request.getNombre());

        // Resolver gerencia padre
        if (request.getIdgerenciaPadre() != null) {
            Gerencia padre = gerenciaRepository.findById(request.getIdgerenciaPadre())
                    .orElseThrow(() -> new RuntimeException("Gerencia padre no encontrada: " + request.getIdgerenciaPadre()));
            g.setGerenciaPadre(padre);
        } else {
            g.setGerenciaPadre(null);
        }

        // Resolver gerente
        if (request.getIdGerenteUsuario() != null) {
            Usuario gerente = usuarioRepository.findByIdusuarioAndFceliminacionIsNull(request.getIdGerenteUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario gerente no encontrado o inactivo: " + request.getIdGerenteUsuario()));

            // Validar que el gerente pertenece a la misma gerencia
            // Solo aplicable cuando la gerencia ya tiene ID (caso edición)
            if (g.getIdgerencia() != null && gerente.getGerencia() != null
                    && !gerente.getGerencia().getIdgerencia().equals(g.getIdgerencia())) {
                throw new RuntimeException("GERENTE_GERENCIA_INVALIDA: El usuario no pertenece a esta gerencia");
            }

            g.setGerente(gerente);
        } else {
            g.setGerente(null);
        }
    }

    // -------------------------------------------------------------------------
    // Eliminación
    // -------------------------------------------------------------------------

    @Transactional
    public void eliminarConReasignacion(Long idEliminar, Long idReemplazo) {
        Gerencia gEliminar = gerenciaRepository.findById(idEliminar)
                .orElseThrow(() -> new RuntimeException("Gerencia a eliminar no encontrada"));

        // Validar que no tenga gerencias hijas activas
        long hijosActivos = gerenciaRepository.countByGerenciaPadreAndFceliminacionIsNull(gEliminar);
        if (hijosActivos > 0) {
            throw new RuntimeException("GERENCIA_TIENE_HIJOS: La gerencia tiene " + hijosActivos + " sub-gerencia(s) activa(s). Elimínelas primero.");
        }

        if (idReemplazo != null) {
            Gerencia gReemplazo = gerenciaRepository.findById(idReemplazo)
                    .orElseThrow(() -> new RuntimeException("Gerencia de reemplazo no encontrada"));

            if (idEliminar.equals(idReemplazo)) {
                throw new RuntimeException("La gerencia de reemplazo no puede ser la misma que se desea eliminar");
            }

            usuarioRepository.reasignarGerencia(gEliminar, gReemplazo);
            proyectoRepository.reasignarGerencia(gEliminar, gReemplazo);
        }

        gEliminar.setFceliminacion(Instant.now());
        gerenciaRepository.save(gEliminar);
    }

    @Transactional
    public void eliminar(Long idgerencia) {
        gerenciaRepository.findById(idgerencia).ifPresent(g -> {
            // Validar que no tenga gerencias hijas activas
            long hijosActivos = gerenciaRepository.countByGerenciaPadreAndFceliminacionIsNull(g);
            if (hijosActivos > 0) {
                throw new RuntimeException("GERENCIA_TIENE_HIJOS: La gerencia tiene " + hijosActivos + " sub-gerencia(s) activa(s). Elimínelas primero.");
            }
            g.setFceliminacion(Instant.now());
            gerenciaRepository.save(g);
        });
    }
}
