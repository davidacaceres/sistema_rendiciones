package com.personal.rendiciones.common.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

/**
 * Configuración global de Jackson para el manejo estandarizado de fechas.
 * Asegura que los tipos java.time (como Instant) se serialicen como cadenas ISO-8601.
 */
@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            // Desactiva la serialización de fechas como números (timestamps UNIX)
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            // Registra el módulo para tipos de fecha modernos de Java 8+
            builder.modules(new JavaTimeModule());
            // Establece UTC como zona horaria por defecto para la serialización
            builder.timeZone(TimeZone.getTimeZone("UTC"));
        };
    }
}
