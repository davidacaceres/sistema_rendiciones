package com.personal.rendiciones.administracion.repository;

import com.personal.rendiciones.administracion.model.Gerencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GerenciaRepository extends JpaRepository<Gerencia, Long>, JpaSpecificationExecutor<Gerencia> {

    /**
     * Busca una gerencia activa por su ID.
     */
    Optional<Gerencia> findByIdgerenciaAndFceliminacionIsNull(Long idgerencia);

    /**
     * Cuenta las gerencias hijas activas (no eliminadas) de una gerencia padre.
     * Se usa para validar que no se pueda eliminar una gerencia con hijos activos.
     */
    long countByGerenciaPadreAndFceliminacionIsNull(Gerencia gerenciaPadre);

    /**
     * Lista todas las gerencias activas ordenadas por nombre.
     * Se usa para el selector de gerencia padre en el formulario.
     */
    List<Gerencia> findByFceliminacionIsNullOrderByNombreAsc();
}
