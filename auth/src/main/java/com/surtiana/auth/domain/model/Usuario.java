
package com.surtiana.auth.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {
    private String cedula;
    private String nombre;
    private String correo;
    private String contrasena;
    private String telefono;
    private String rol;
    private String resetPasswordToken;
    private LocalDateTime resetPasswordTokenExpiry;
}
