package com.personal.rendiciones.administracion.controller;

import com.personal.rendiciones.administracion.dto.ModuloDTO;
import com.personal.rendiciones.administracion.model.Modulo;
import com.personal.rendiciones.administracion.service.ConfiguracionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConfiguracionController.class)
@AutoConfigureMockMvc(addFilters = false) // Deshabilitamos seguridad para el test unitario de controlador
class ConfiguracionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConfiguracionService configuracionService;

    @Test
    void shouldReturnMenuList() throws Exception {
        // Given
        ModuloDTO modulo = ModuloDTO.builder()
                .nombre("Administración")
                .icono("settings")
                .subvistas(Collections.emptyList())
                .build();

        when(configuracionService.getMenu()).thenReturn(Collections.singletonList(modulo));

        // When & Then
        mockMvc.perform(get("/api/configuracion/menu")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Administración"))
                .andExpect(jsonPath("$").isArray());
    }
}
