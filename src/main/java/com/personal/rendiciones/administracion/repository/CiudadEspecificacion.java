package com.personal.rendiciones.administracion.repository;

import com.personal.rendiciones.administracion.model.Ciudad;
import org.springframework.data.jpa.domain.Specification;

public class CiudadEspecificacion {

    public static Specification<Ciudad> filtrar(String search, Long idPais, Boolean activo) {
        return (root, query, cb) -> {
            Specification<Ciudad> spec = Specification.where(noEliminado());

            if (search != null && !search.isEmpty()) {
                String likePattern = "%" + search.toLowerCase() + "%";
                spec = spec.and((r, q, c) -> cb.or(
                    cb.like(cb.lower(r.get("nombre")), likePattern),
                    cb.like(cb.lower(r.join("pais").get("nombre")), likePattern)
                ));
            }

            if (idPais != null) {
                spec = spec.and((r, q, rcb) -> rcb.equal(r.get("pais").get("idpais"), idPais));
            }

            if (activo != null) {
                spec = spec.and((r, q, c) -> c.equal(r.get("lgactivo"), activo));
            }

            return spec.toPredicate(root, query, cb);
        };
    }

    private static Specification<Ciudad> noEliminado() {
        return (root, query, cb) -> cb.isNull(root.get("fceliminacion"));
    }
}
