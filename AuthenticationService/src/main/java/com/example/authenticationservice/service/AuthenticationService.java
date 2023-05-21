package com.example.authenticationservice.service;

import com.example.authconfigurations.auth.jwt.JwtService;
import com.example.authenticationservice.auth.AuthenticationRequest;
import com.example.authenticationservice.auth.AuthenticationResponse;
import com.example.authenticationservice.auth.RegisterRequest;
import com.example.authenticationservice.kafka.producer.ClientProducer;
import com.example.authenticationservice.repository.RoleRepository;
import com.example.authenticationservice.models.User;
import com.example.authenticationservice.repository.UserRepository;
import com.example.authenticationservice.source.RoleType;
import com.example.basedomains.dto.ClientDTO;
import com.example.basedomains.exception.NameAlreadyRegisteredException;
import com.example.basedomains.exception.RequiredFieldException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final UserRepository userRepository;
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

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClientProducer clientProducer;

    /**
     * @apiNote Registra un nuevo usuario de tipo cliente en la base de datos y lanza un evento para registrar el cliente correspondiente al usuario.
     * @param request Datos del usuario a registrar
     * @return Token JWT generado para el usuario
     */
    public AuthenticationResponse register(RegisterRequest request) throws RequiredFieldException, NameAlreadyRegisteredException {
        userService.validateRequiredFields(request);
        userService.validateUsernameIsUnique(request.getUsername());
        var user = User.builder()
                .fullname(request.getFullname())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(roleRepository.findByName(RoleType.CLIENT.name()))
                .isDeleted(false)
                .build();
        User savedUser = userRepository.save(user);

        //Evento para crear cliente
        clientProducer.sendClient(
            ClientDTO.builder()
                .fullname(request.getFullname())
                .email(request.getEmail())
                .nit(request.getNit())
                .age(request.getAge())
                .address(request.getAddress())
                .numberPhone(request.getNumberPhone())
                .idUser(savedUser.getId())
                .build()
        );

        //JWT
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
        var user = userRepository.findByUsernameAndIsDeletedFalse(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * @apiNote Llama  a la creacion de los roles y usuario administrador por defecto al momento de iniciar la aplicacion.
     */
    @EventListener(ApplicationReadyEvent.class)
    private void initialize(){
        roleService.addRoles();
        userService.addAdmin();
    }

}
