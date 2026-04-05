package com.personal.rendiciones.administracion.controller;

import com.personal.rendiciones.administracion.dto.PaisDTO;
import com.personal.rendiciones.administracion.repository.PaisEspecificacion;
import com.personal.rendiciones.administracion.service.PaisServicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paises")
@CrossOrigin(origins = "*")
public class PaisControlador {

    private final PaisServicio paisServicio;

    public PaisControlador(PaisServicio paisServicio) {
        this.paisServicio = paisServicio;
    }

    @GetMapping
    public ResponseEntity<Page<PaisDTO>> listar(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean lgactivo,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(paisServicio.listar(PaisEspecificacion.filtrar(search, lgactivo), pageable));
    }

    @GetMapping("/{idpais}")
    public ResponseEntity<PaisDTO> obtenerPorId(@PathVariable Long idpais) {
        return paisServicio.buscarPorId(idpais)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PaisDTO> crear(@RequestBody PaisDTO dto) {
        return ResponseEntity.ok(paisServicio.guardar(dto));
    }

    @PutMapping("/{idpais}")
    public ResponseEntity<PaisDTO> actualizar(@PathVariable Long idpais, @RequestBody PaisDTO dto) {
        dto.setIdpais(idpais);
        return ResponseEntity.ok(paisServicio.guardar(dto));
    }

    @PatchMapping("/{idpais}/status")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long idpais, @RequestBody Boolean activo) {
        paisServicio.cambiarEstado(idpais, activo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idpais}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idpais) {
        try {
            paisServicio.eliminarLogicamente(idpais);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
