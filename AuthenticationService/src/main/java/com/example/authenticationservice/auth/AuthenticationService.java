package com.example.authenticationservice.auth;

import com.example.authconfigurations.auth.jwt.JwtService;
import com.example.authenticationservice.models.Role;
import com.example.authenticationservice.models.RoleRepository;
import com.example.authenticationservice.models.User;
import com.example.authenticationservice.models.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @apiNote Esta clase es responsable de la lógica de autenticación y registro de usuarios.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    /**
     * @implNote Repositorio para las consultas relacionadas con usuarios
     */
    private final UserRepository repository;
    /**
     * @implNote Repositorio para las consultas relacionadas con roles
     */
    private final RoleRepository roleRepository;
    /**
     * @implNote Codificador de contraseñas
     */
    private final PasswordEncoder passwordEncoder;
    /**
     * @implNote Servicio para la generación de tokens JWT
     */
    private final JwtService jwtService;
    /**
     * @implNote Administrador de autenticación
     */
    private final AuthenticationManager authenticationManager;

    /**
     * @apiNote Registra un nuevo usuario en la base de datos
     * @param request Datos del usuario a registrar
     * @return Token JWT generado para el usuario
     */
    public AuthenticationResponse register(RegisterRequest request) {
        Role role = roleRepository.findById(request.getRoleId());
        var user = User.builder()
                .fullname(request.getFullname())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.builder().id(request.getRoleId()).name(role.getName()).build())
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * @apiNote Autentica un usuario en el sistema
     * @param request Datos del usuario a autenticar
     * @return Token JWT generado para el usuario
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
