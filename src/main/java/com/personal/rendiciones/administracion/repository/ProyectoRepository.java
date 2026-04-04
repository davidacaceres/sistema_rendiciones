package com.personal.rendiciones.administracion.repository;

import com.personal.rendiciones.administracion.model.Gerencia;
import com.personal.rendiciones.administracion.model.Proyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long>, JpaSpecificationExecutor<Proyecto> {

    @Query("SELECT p FROM Proyecto p WHERE p.fceliminacion IS NULL AND " + "(LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) OR "
            + "LOWER(p.identificador) LIKE LOWER(CONCAT('%', :nombre, '%')))")
    Page<Proyecto> findByNombreContainingIgnoreCase(@Param("nombre") String nombre, Pageable pageable);

    @Query("SELECT p FROM Proyecto p WHERE p.fceliminacion IS NULL")
    Page<Proyecto> findAllActivos(Pageable pageable);

    boolean existsByIdentificadorAndFceliminacionIsNull(String identificador);

    boolean existsByNombreAndFceliminacionIsNull(String nombre);

    long countByGerenciaAndFceliminacionIsNull(Gerencia gerencia);

    @Modifying
    @Query("UPDATE Proyecto p SET p.gerencia = :reemplazo WHERE p.gerencia = :eliminar AND p.fceliminacion IS NULL")
    int reasignarGerencia(@Param("eliminar") Gerencia eliminar, @Param("reemplazo") Gerencia reemplazo);
}
