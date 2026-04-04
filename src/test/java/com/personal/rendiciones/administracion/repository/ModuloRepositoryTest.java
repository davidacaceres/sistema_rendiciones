package com.personal.rendiciones.administracion.repository;

import com.personal.rendiciones.administracion.model.Modulo;
import com.personal.rendiciones.administracion.model.SubVista;
import com.personal.rendiciones.common.config.AuditoriaConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuditoriaConfig.class)
class ModuloRepositoryTest {

    @Autowired
    private ModuloRepository moduloRepository;

    @Test
    void shouldSaveAndRetrieveModuloWithSubvistas() {
        // Given
        Modulo modulo = new Modulo();
        modulo.setNombre("Administración");
        modulo.setIcono("settings");
        modulo.setOrden(1);
        modulo.setSubvistas(new ArrayList<>());

        SubVista vista = new SubVista();
        vista.setNombre("Usuarios");
        vista.setRuta("/admin/users");
        vista.setPermiso("ADMIN");
        vista.setBlconfiguracion(true);
        vista.setModulo(modulo);

        modulo.getSubvistas().add(vista);

        // When
        Modulo saved = moduloRepository.save(modulo);

        // Then
        assertThat(saved.getIdmodulo()).isNotNull();
        assertThat(saved.getSubvistas()).hasSize(1);
        assertThat(saved.getSubvistas().get(0).getNombre()).isEqualTo("Usuarios");
    }
}
