package com.personal.rendiciones.administracion.repository;

import com.personal.rendiciones.administracion.model.Ciudad;
import com.personal.rendiciones.administracion.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;
import java.util.List;

public interface CiudadRepository extends JpaRepository<Ciudad, Long>, JpaSpecificationExecutor<Ciudad> {
    Optional<Ciudad> findByIdciudadAndFceliminacionIsNull(Long idciudad);
    List<Ciudad> findAllByPaisAndFceliminacionIsNullOrderByNombreAsc(Pais pais);
    long countByPaisAndFceliminacionIsNullAndLgactivoIsTrue(Pais pais);
}
