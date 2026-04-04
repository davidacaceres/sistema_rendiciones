package com.personal.rendiciones.administracion.controller;

import com.personal.rendiciones.administracion.dto.GerenciaDependenciasDTO;
import com.personal.rendiciones.administracion.dto.GerenciaDTO;
import com.personal.rendiciones.administracion.dto.GerenciaRequestDTO;
import com.personal.rendiciones.administracion.service.GerenciaServicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gerencias")
@CrossOrigin(origins = "*")
public class GerenciaControlador {

    private final GerenciaServicio gerenciaServicio;

    public GerenciaControlador(GerenciaServicio gerenciaServicio) {
        this.gerenciaServicio = gerenciaServicio;
    }

    @GetMapping
    public ResponseEntity<Page<GerenciaDTO>> listar(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "nombre") Pageable pageable) {
        return ResponseEntity.ok(gerenciaServicio.listar(search, pageable));
    }

    @GetMapping("/{idgerencia}")
    public ResponseEntity<GerenciaDTO> obtenerPorId(@PathVariable Long idgerencia) {
        GerenciaDTO dto = gerenciaServicio.obtenerPorId(idgerencia);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    /**
     * Lista las gerencias activas disponibles como padre en el formulario.
     * Excluye la gerencia con el ID indicado (auto-referencia directa).
     */
    @GetMapping("/disponibles-como-padre")
    public ResponseEntity<List<GerenciaDTO>> listarDisponiblesComoPadre(
            @RequestParam(required = false) Long excluirId) {
        return ResponseEntity.ok(gerenciaServicio.listarActivasParaSelectorPadre(
                excluirId != null ? excluirId : -1L));
    }

    @PostMapping
    public ResponseEntity<GerenciaDTO> crear(@RequestBody GerenciaRequestDTO request) {
        return ResponseEntity.ok(gerenciaServicio.toDTO(gerenciaServicio.crear(request)));
    }

    @PutMapping("/{idgerencia}")
    public ResponseEntity<GerenciaDTO> actualizar(@PathVariable Long idgerencia,
                                                   @RequestBody GerenciaRequestDTO request) {
        var actualizada = gerenciaServicio.actualizar(idgerencia, request);
        return actualizada != null
                ? ResponseEntity.ok(gerenciaServicio.toDTO(actualizada))
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idgerencia}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idgerencia) {
        gerenciaServicio.eliminar(idgerencia);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idgerencia}/dependencias")
    public ResponseEntity<GerenciaDependenciasDTO> obtenerDependencias(@PathVariable Long idgerencia) {
        return ResponseEntity.ok(gerenciaServicio.obtenerDependencias(idgerencia));
    }

    @DeleteMapping("/{idgerencia}/eliminar-con-reasignacion")
    public ResponseEntity<Void> eliminarConReasignacion(
            @PathVariable Long idgerencia,
            @RequestParam(required = false) Long idReemplazo) {
        gerenciaServicio.eliminarConReasignacion(idgerencia, idReemplazo);
        return ResponseEntity.noContent().build();
    }
}
