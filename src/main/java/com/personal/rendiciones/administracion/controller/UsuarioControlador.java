package com.personal.rendiciones.administracion.controller;

import com.personal.rendiciones.administracion.dto.UsuarioDTO;
import com.personal.rendiciones.administracion.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.personal.rendiciones.administracion.model.Usuario;
import com.personal.rendiciones.administracion.model.enums.OrigenAutenticacion;
import com.personal.rendiciones.administracion.model.enums.RolUsuario;
import com.personal.rendiciones.administracion.repository.GerenciaRepository;
import com.personal.rendiciones.administracion.repository.UsuarioEspecificacion;
import com.personal.rendiciones.administracion.service.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;
    private final UsuarioRepository usuarioRepository;
    private final GerenciaRepository gerenciaRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> listar(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long idgerencia,
            @RequestParam(required = false) OrigenAutenticacion origen,
            @RequestParam(required = false) RolUsuario rol,
            @RequestParam(required = false) Boolean blactivo,
            @PageableDefault(size = 10) Pageable pageable) {
        
        return ResponseEntity.ok(usuarioServicio.listar(
            UsuarioEspecificacion.filtrar(search, idgerencia, origen, rol, blactivo), 
            pageable
        ));
    }

    @GetMapping("/{idusuario}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Long idusuario) {
        return usuarioServicio.buscarPorId(idusuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Busca usuarios activos para el selector de gerente.
     * Filtra por gerencia (obligatorio para la regla: gerente debe pertenecer a la misma gerencia)
     * y opcionalmente por texto de búsqueda (nombre, trigrama, username).
     * Retorna máx 50 resultados sin paginación.
     */
    @GetMapping("/activos")
    public ResponseEntity<List<UsuarioDTO>> buscarActivos(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long idgerencia) {
        var spec = UsuarioEspecificacion.filtrar(search, idgerencia, null, null, true);
        var pageable = PageRequest.of(0, 50);
        List<UsuarioDTO> resultados = usuarioServicio.listar(spec, pageable).getContent();
        return ResponseEntity.ok(resultados);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioServicio.toDTO(usuarioServicio.guardar(usuario)));
    }

    @PostMapping("/recuperar/{idusuario}")
    public ResponseEntity<UsuarioDTO> recuperar(@PathVariable Long idusuario) {
        return ResponseEntity.ok(usuarioServicio.toDTO(usuarioServicio.recuperar(idusuario)));
    }

    @PutMapping("/{idusuario}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Long idusuario, @RequestBody Usuario usuario) {
        return usuarioRepository.findById(idusuario)
                .filter(u -> u.getFceliminacion() == null)
                .map(existente -> {
                    existente.setNombre(usuario.getNombre());
                    existente.setEmail(usuario.getEmail());
                    existente.setTrigrama(usuario.getTrigrama());
                    existente.setRoles(usuario.getRoles());
                    
                    if (usuario.getGerencia() != null) {
                        gerenciaRepository.findById(usuario.getGerencia().getIdgerencia())
                                .ifPresent(existente::setGerencia);
                    }
                    
                    existente.setBlactivo(usuario.isBlactivo());
                    existente.setOrigen(usuario.getOrigen());
                    
                    if (usuario.getOrigen() == OrigenAutenticacion.LOCAL && 
                        usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                        existente.setPassword(passwordEncoder.encode(usuario.getPassword()));
                    }
                    
                    Usuario guardado = usuarioRepository.save(existente);
                    return ResponseEntity.ok(usuarioServicio.toDTO(guardado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{idusuario}/status")
    public ResponseEntity<UsuarioDTO> patchStatus(@PathVariable Long idusuario, @RequestBody Boolean blactivo) {
       return usuarioRepository.findById(idusuario)
               .filter(u -> u.getFceliminacion() == null)
               .map(u -> {
                   u.setBlactivo(blactivo);
                   return ResponseEntity.ok(usuarioServicio.toDTO(usuarioRepository.save(u)));
               }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idusuario}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idusuario) {
        usuarioServicio.eliminarLogicamente(idusuario);
        return ResponseEntity.noContent().build();
    }
}
