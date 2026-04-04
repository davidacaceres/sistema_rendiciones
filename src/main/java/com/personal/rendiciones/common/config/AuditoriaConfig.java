package com.personal.rendiciones.common.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

/**
 * Configuración global de auditoría JPA y estandarización horaria UTC.
 */
@Configuration
@EnableJpaAuditing
public class AuditoriaConfig {

    @PostConstruct
    public void init() {
        // Establecer la zona horaria predeterminada de la aplicación a UTC
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
