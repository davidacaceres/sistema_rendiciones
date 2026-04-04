package com.personal.rendiciones.administracion.repository;

import com.personal.rendiciones.administracion.model.Gerencia;
import org.springframework.data.jpa.domain.Specification;

public class GerenciaEspecificacion {

    public static Specification<Gerencia> buscarPorTexto(String search) {
        return (root, query, cb) -> {
            if (search == null || search.isEmpty()) return null;
            String likePattern = "%" + search.toLowerCase() + "%";
            return cb.or(
                cb.like(cb.lower(root.get("nombre")), likePattern),
                cb.like(cb.lower(root.get("abreviacion")), likePattern)
            );
        };
    }
}
