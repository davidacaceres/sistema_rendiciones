package com.personal.rendiciones.administracion.controller;

import com.personal.rendiciones.administracion.dto.CiudadDTO;
import com.personal.rendiciones.administracion.repository.CiudadEspecificacion;
import com.personal.rendiciones.administracion.service.CiudadServicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ciudades")
@CrossOrigin(origins = "*")
public class CiudadControlador {

    private final CiudadServicio ciudadServicio;

    public CiudadControlador(CiudadServicio ciudadServicio) {
        this.ciudadServicio = ciudadServicio;
    }

    @GetMapping
    public ResponseEntity<Page<CiudadDTO>> listar(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long idpais,
            @RequestParam(required = false) Boolean lgactivo,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(ciudadServicio.listar(CiudadEspecificacion.filtrar(search, idpais, lgactivo), pageable));
    }

    @GetMapping("/{idciudad}")
    public ResponseEntity<CiudadDTO> obtenerPorId(@PathVariable Long idciudad) {
        return ciudadServicio.buscarPorId(idciudad)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pais/{idpais}")
    public ResponseEntity<List<CiudadDTO>> listarPorPais(@PathVariable Long idpais) {
        return ResponseEntity.ok(ciudadServicio.listarPorPaisActivo(idpais));
    }

    @PostMapping
    public ResponseEntity<CiudadDTO> crear(@RequestBody CiudadDTO dto) {
        return ResponseEntity.ok(ciudadServicio.guardar(dto));
    }

    @PutMapping("/{idciudad}")
    public ResponseEntity<CiudadDTO> actualizar(@PathVariable Long idciudad, @RequestBody CiudadDTO dto) {
        dto.setIdciudad(idciudad);
        return ResponseEntity.ok(ciudadServicio.guardar(dto));
    }

    @PatchMapping("/{idciudad}/status")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long idciudad, @RequestBody Boolean activo) {
        ciudadServicio.cambiarEstado(idciudad, activo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idciudad}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idciudad) {
        try {
            ciudadServicio.eliminarLogicamente(idciudad);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
