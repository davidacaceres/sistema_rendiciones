package com.personal.rendiciones.administracion.repository;

import com.personal.rendiciones.administracion.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;
import java.util.List;

public interface PaisRepository extends JpaRepository<Pais, Long>, JpaSpecificationExecutor<Pais> {
    Optional<Pais> findByIdpaisAndFceliminacionIsNull(Long idpais);
    Optional<Pais> findByLgnacionalIsTrueAndFceliminacionIsNull();
    Optional<Pais> findByCodigopaisAndFceliminacionIsNull(String codigopais);
    List<Pais> findAllByFceliminacionIsNullOrderByNombreAsc();
}
