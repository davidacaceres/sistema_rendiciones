package com.personal.rendiciones.administracion.model.enums;

/**
 * Origen de la cuenta de usuario para procesos de autenticación.
 */
public enum OrigenAutenticacion {
    LOCAL,     // Autenticación interna con contraseña Bcrypt.
    KEYCLOAK   // Autenticación corporativa vía Keycloak/OAuth2.
}
