package com.surtiana.auth.infraestructure.driver_adapters.jpa_repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
@Data

public class UsuarioData {

    @Id
    private String cedula;
    private String nombre;

    @Column(length = 30, nullable = false)
    private String correo;
    private String contrasena;

    @Column(length = 10)
    private String telefono;
    private String rol;

    @Column(nullable = true)
    private String resetPasswordToken;

    @Column(nullable = true)
    private LocalDateTime resetPasswordTokenExpiry;
}
