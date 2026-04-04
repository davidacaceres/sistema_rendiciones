package com.personal.rendiciones.administracion.repository;

import com.personal.rendiciones.administracion.model.Gerencia;
import com.personal.rendiciones.administracion.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {
    Optional<Usuario> findByUsernameAndFceliminacionIsNull(String username);
    Optional<Usuario> findByIdusuarioAndFceliminacionIsNull(Long idusuario);
    
    // Búsquedas para validación de unicidad (incluye eliminados)
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByTrigrama(String trigrama);

    long countByGerenciaAndFceliminacionIsNull(Gerencia gerencia);

    @Modifying
    @Query("UPDATE Usuario u SET u.gerencia = :reemplazo WHERE u.gerencia = :eliminar AND u.fceliminacion IS NULL")
    int reasignarGerencia(@Param("eliminar") Gerencia eliminar, @Param("reemplazo") Gerencia reemplazo);
}
