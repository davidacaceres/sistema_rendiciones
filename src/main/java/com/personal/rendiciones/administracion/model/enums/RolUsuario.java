package com.personal.rendiciones.administracion.model.enums;

/**
 * Roles fijos del sistema para control de acceso modular (RBAC).
 * Un usuario puede poseer múltiples roles de esta lista.
 */
public enum RolUsuario {
    USUARIO,       // Operador de solicitudes y rendiciones comunes.
    ADMINISTRADOR, // Gestor de configuración, usuarios y gerencias.
    GERENTE        // Aprobador de flujos y administrador de áreas.
}
