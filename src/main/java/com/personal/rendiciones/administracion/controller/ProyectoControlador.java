package com.personal.rendiciones.administracion.controller;

import com.personal.rendiciones.administracion.dto.ProyectoDTO;
import com.personal.rendiciones.administracion.service.ProyectoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proyectos")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class ProyectoControlador {

    private final ProyectoService proyectoService;

    public ProyectoControlador(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @GetMapping
    public ResponseEntity<Page<ProyectoDTO>> listarProyectos(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long filterGerencia,
            @RequestParam(required = false) Boolean filterActivo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        
        Page<ProyectoDTO> proyectos = proyectoService.listarProyectos(search, filterGerencia, filterActivo, pageable);
        return ResponseEntity.ok(proyectos);
    }

    @PostMapping
    public ResponseEntity<ProyectoDTO> crearProyecto(@jakarta.validation.Valid @RequestBody ProyectoDTO dto) {
        ProyectoDTO creado = proyectoService.crearProyecto(dto);
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProyectoDTO> actualizarProyecto(@PathVariable Long id, @jakarta.validation.Valid @RequestBody ProyectoDTO dto) {
        ProyectoDTO actualizado = proyectoService.actualizarProyecto(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        proyectoService.eliminarProyecto(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ProyectoDTO> toggleActivo(@PathVariable Long id) {
        ProyectoDTO actualizado = proyectoService.toggleActivo(id);
        return ResponseEntity.ok(actualizado);
    }
}
