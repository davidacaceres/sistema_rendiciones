package com.personal.rendiciones.administracion.controller;

import com.personal.rendiciones.administracion.dto.ModuloDTO;
import com.personal.rendiciones.administracion.service.ConfiguracionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/configuracion")
@RequiredArgsConstructor
public class ConfiguracionController {

    private final ConfiguracionService configuracionService;

    @GetMapping("/menu")
    public List<ModuloDTO> getMenu() {
        return configuracionService.getMenu();
    }
}

