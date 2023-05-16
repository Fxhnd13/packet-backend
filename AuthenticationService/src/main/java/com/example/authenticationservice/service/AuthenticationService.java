package com.example.authenticationservice.service;

import com.example.authconfigurations.auth.jwt.JwtService;
import com.example.authenticationservice.auth.AuthenticationRequest;
import com.example.authenticationservice.auth.AuthenticationResponse;
import com.example.authenticationservice.auth.RegisterRequest;
import com.example.authenticationservice.models.Role;
import com.example.authenticationservice.repository.RoleRepository;
import com.example.authenticationservice.models.User;
import com.example.authenticationservice.repository.UserRepository;
import com.example.authenticationservice.source.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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
        Role role = roleRepository.findByName(RoleType.CLIENT.name());
        var user = User.builder()
                .fullname(request.getFullname())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.builder().id(request.getRoleId()).name(role.getName()).build())
                .build();
        repository.save(user);

        //Evento para crear cliente
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

    @EventListener(ApplicationReadyEvent.class)
    private void initialize(){
        addRoles();
        addAdmin();
    }

    /**
     * Metodo que verifica los roles, si no existen datos sobre los Roles, inserta en la base de datos los tres tipos de
     * roles que existen en el sistema.
     */
    private  void addRoles(){
        if(roleRepository.count() == 0){
            roleRepository.save(new Role(1, RoleType.ADMIN.name(), "Administador"));
            roleRepository.save(new Role(2, RoleType.OPERATOR.name(), "Operador"));
            roleRepository.save(new Role(3, RoleType.CLIENT.name(), "Cliente"));
        }
    }

    /**
     * Metodo que verifica el usuario administrador por default, si este no existe en la base de datos lo crea.
     */
    private void addAdmin(){
        if(repository.findByUsername(RoleType.ADMIN.name()).isEmpty()){
            repository.save(
                new User(
                0,
                    RoleType.ADMIN.name(),
            "Default System Administrator",
                    passwordEncoder.encode("admin"),
                    roleRepository.findByName(RoleType.ADMIN.name())
                )
            );
        }
    }
}
