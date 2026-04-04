package com.personal.rendiciones.administracion.model;

import com.personal.rendiciones.administracion.model.enums.OrigenAutenticacion;
import com.personal.rendiciones.administracion.model.enums.RolUsuario;
import com.personal.rendiciones.common.model.BaseEntidad;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario extends BaseEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Long idusuario;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; 

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, length = 3)
    private String trigrama;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrigenAutenticacion origen;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuarioroles", joinColumns = @JoinColumn(name = "idusuario"))
    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private Set<RolUsuario> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idgerencia", nullable = false)
    private Gerencia gerencia;

    @Column(name = "blactivo", nullable = false)
    private boolean blactivo = true;

    public Usuario() {}

    public Long getIdusuario() { return idusuario; }
    public void setIdusuario(Long idusuario) { this.idusuario = idusuario; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTrigrama() { return trigrama; }
    public void setTrigrama(String trigrama) { this.trigrama = trigrama; }

    public OrigenAutenticacion getOrigen() { return origen; }
    public void setOrigen(OrigenAutenticacion origen) { this.origen = origen; }

    public Set<RolUsuario> getRoles() { return roles; }
    public void setRoles(Set<RolUsuario> roles) { this.roles = roles; }

    public Gerencia getGerencia() { return gerencia; }
    public void setGerencia(Gerencia gerencia) { this.gerencia = gerencia; }

    public boolean isBlactivo() { return blactivo; }
    public void setBlactivo(boolean blactivo) { this.blactivo = blactivo; }
}
