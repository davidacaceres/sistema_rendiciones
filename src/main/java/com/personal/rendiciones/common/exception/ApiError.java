package com.personal.rendiciones.common.exception;

import java.time.Instant;

/**
 * Estructura estandarizada para respuestas de error de la API.
 * Se evita el uso de Lombok debido a incompatibilidades temporales con el procesador de anotaciones.
 */
public class ApiError {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ApiError() {}

    public ApiError(Instant timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // Getters y Setters
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    // Builder alternativo simple
    public static ApiErrorBuilder builder() {
        return new ApiErrorBuilder();
    }

    public static class ApiErrorBuilder {
        private Instant timestamp;
        private int status;
        private String error;
        private String message;
        private String path;

        public ApiErrorBuilder timestamp(Instant timestamp) { this.timestamp = timestamp; return this; }
        public ApiErrorBuilder status(int status) { this.status = status; return this; }
        public ApiErrorBuilder error(String error) { this.error = error; return this; }
        public ApiErrorBuilder message(String message) { this.message = message; return this; }
        public ApiErrorBuilder path(String path) { this.path = path; return this; }

        public ApiError build() {
            return new ApiError(timestamp, status, error, message, path);
        }
    }
}
