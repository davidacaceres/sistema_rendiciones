package com.personal.rendiciones.administracion.repository;

import com.personal.rendiciones.administracion.model.Usuario;
import com.personal.rendiciones.administracion.model.enums.OrigenAutenticacion;
import com.personal.rendiciones.administracion.model.enums.RolUsuario;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class UsuarioEspecificacion {

    public static Specification<Usuario> filtrar(String search, Long gerenciaId, OrigenAutenticacion origen, RolUsuario rol, Boolean activo) {
        return (root, query, cb) -> {
            Specification<Usuario> spec = Specification.where(noEliminado());

            if (search != null && !search.isEmpty()) {
                String likePattern = "%" + search.toLowerCase() + "%";
                spec = spec.and((r, q, c) -> c.or(
                    c.like(c.lower(r.get("nombre")), likePattern),
                    c.like(c.lower(r.get("username")), likePattern),
                    c.like(c.lower(r.get("email")), likePattern),
                    c.like(c.lower(r.get("trigrama")), likePattern)
                ));
            }

            if (gerenciaId != null) {
                spec = spec.and((r, q, c) -> c.equal(r.get("gerencia").get("idgerencia"), gerenciaId));
            }

            if (origen != null) {
                spec = spec.and((r, q, c) -> c.equal(r.get("origen"), origen));
            }

            if (rol != null) {
                spec = spec.and((r, q, c) -> {
                    Join<Object, Object> rolesJoin = r.join("roles");
                    return c.equal(rolesJoin, rol);
                });
            }

            if (activo != null) {
                spec = spec.and((r, q, c) -> c.equal(r.get("blactivo"), activo));
            }

            return spec.toPredicate(root, query, cb);
        };
    }

    private static Specification<Usuario> noEliminado() {
        return (root, query, cb) -> cb.isNull(root.get("fceliminacion"));
    }
}
