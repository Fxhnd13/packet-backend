package com.example.authenticationservice.service;
import com.example.authenticationservice.auth.RegisterRequest;
import com.example.authenticationservice.models.User;
import com.example.authenticationservice.repository.UserRepository;
import com.example.authenticationservice.source.RoleType;
import com.example.basedomains.exception.NameAlreadyRegisteredException;
import com.example.basedomains.exception.RequiredFieldException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;


    public void validateRequiredFields(RegisterRequest registerRequest) throws RequiredFieldException {
        validateStringFields(registerRequest);
        validateNumericField(registerRequest.getAge());
    }

    /**
     * @apiNote Valida que el atributo Integer del objeto RegisterRequest no sea nulo
     * @param age Atributo a validar
     * @throws RequiredFieldException
     */
    private void validateNumericField(Integer age) throws RequiredFieldException {
        if(age == null)
            throw  new RequiredFieldException();
    }

    /**
     * @apiNote Valida que todos los atributos String del objeto RegisterRequest no esten vacios o esten en blanco
     * @param registerRequest Datos a validar
     * @throws RequiredFieldException
     */
    private void validateStringFields(RegisterRequest registerRequest) throws RequiredFieldException {
        if( (registerRequest.getFullname().isBlank() || registerRequest.getFullname().isEmpty()) ||
                (registerRequest.getUsername().isBlank() || registerRequest.getUsername().isEmpty()) ||
                (registerRequest.getPassword().isBlank() || registerRequest.getPassword().isEmpty()) ||
                (registerRequest.getNit().isBlank() || registerRequest.getNit().isEmpty()) ||
                (registerRequest.getAddress().isBlank() || registerRequest.getAddress().isEmpty()) ||
                (registerRequest.getEmail().isBlank() || registerRequest.getEmail().isEmpty()) ||
                (registerRequest.getNumberPhone().isBlank() || registerRequest.getNumberPhone().isEmpty())
        ) throw new RequiredFieldException();
    }

    /**
     * @apiNote Valida que el nombre de usuario no se encuentre registrado en la base de datos
     * @param username Nombre de usuario a validar
     * @throws NameAlreadyRegisteredException
     */
    public void validateUsernameIsUnique(String username) throws NameAlreadyRegisteredException {
        if(userRepository.findByUsername(username).isPresent())
            throw new NameAlreadyRegisteredException("Nombre de usuario ya registrado en el sistema");
    }

    /**
     * @apiNote Registra el usuario administrador por defecto si este no se encuenta
     * previamente registrado.
     */
    public void addAdmin(){
        if(userRepository.findByUsername(RoleType.ADMIN.name().toUpperCase()).isEmpty()){
            userRepository.save(
                    User.builder()
                            .username(RoleType.ADMIN.name().toUpperCase())
                            .fullname("Default System Administrator")
                            .password(passwordEncoder.encode("admin"))
                            .role(roleService.getRolByName(RoleType.ADMIN.name()))
                            .build()
            );
        }
    }

}
