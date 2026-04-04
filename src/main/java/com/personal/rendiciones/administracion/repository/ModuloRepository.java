package com.personal.rendiciones.administracion.repository;

import com.personal.rendiciones.administracion.model.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Long> {
    List<Modulo> findAllByOrderByOrdenAsc();
}
