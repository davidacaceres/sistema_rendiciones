package com.personal.rendiciones.administracion.repository;

import com.personal.rendiciones.administracion.model.Pais;
import org.springframework.data.jpa.domain.Specification;

public class PaisEspecificacion {

    public static Specification<Pais> filtrar(String search, Boolean activo) {
        return (root, query, cb) -> {
            Specification<Pais> spec = Specification.where(noEliminado());

            if (search != null && !search.isEmpty()) {
                String likePattern = "%" + search.toLowerCase() + "%";
                spec = spec.and((r, q, c) -> c.like(c.lower(r.get("nombre")), likePattern));
            }

            if (activo != null) {
                spec = spec.and((r, q, c) -> c.equal(r.get("lgactivo"), activo));
            }

            return spec.toPredicate(root, query, cb);
        };
    }

    private static Specification<Pais> noEliminado() {
        return (root, query, cb) -> cb.isNull(root.get("fceliminacion"));
    }
}
