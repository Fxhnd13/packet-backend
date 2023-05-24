package com.example.authenticationservice.service;
import com.example.authenticationservice.auth.RegisterRequest;
import com.example.authenticationservice.models.Role;
import com.example.authenticationservice.models.User;
import com.example.authenticationservice.repository.UserRepository;
import com.example.authenticationservice.source.RoleType;
import com.example.basedomains.exception.ElementNoExistsException;
import com.example.basedomains.exception.NameAlreadyRegisteredException;
import com.example.basedomains.exception.RequiredFieldException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;


    /**
     * @apiNote  Modifica el atributo isDeleted del usuario cuyo id se recibe como parametro.
     * @param id
     * @throws ElementNoExistsException
     */
    public void delete(int id) throws ElementNoExistsException {
        User user = userRepository.findByIdAndIsDeletedFalse(id);
        if(user == null)
            throw  new ElementNoExistsException();

        user.setDeleted(true);
        userRepository.save(user);
    }

    /**
     * @api\ Obtiene un listado paginado con todos los usuarios registrados en la base de datos cuyo estado deleted sea false.
     * Permite obtener el listado en base a un patron de busqueda
     * @param pattern Patron de busqueda
     * @param page numero de hojas
     * @param size tamaño de hoja
     * @return
     */
    public Page<User> getAll(String pattern, int page, int size){
        if(pattern == null)
            return userRepository.findByIsDeletedFalse(PageRequest.of(page, size, Sort.by("id")));
        if(pattern.matches("[0-9]+"))
            return userRepository.findByIdStartingWithAndIsDeletedFalse(Integer.parseInt(pattern), PageRequest.of(page, size, Sort.by("id")));
        else
            return userRepository.findByIsDeletedFalseAndUsernameIgnoreCaseContaining(pattern, PageRequest.of(page, size, Sort.by("id")));
    }


    /**
     * @apiNote Obtiene el usuario cuyo id sea el que se recibe como parametro.
     * @param id
     * @return
     * @throws ElementNoExistsException
     */
    public User getUser(int id) throws ElementNoExistsException {
        User user = userRepository.findByIdAndIsDeletedFalse(id);
        if(user == null)
            throw  new ElementNoExistsException();
        return user;
    }

    /**
     * @apiNote Actualiza el rol de un usuario en la base de datos.
     * @param user
     */
    public void update(User user) throws ElementNoExistsException {
        User tempUser = userRepository.findByIdAndIsDeletedFalse(user.getId());

        if(tempUser == null)
            throw  new ElementNoExistsException();

        Role role = roleService.getRolByName(user.getRole().getName());
        user.setRole(role);
        userRepository.save(user);
    }

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
        if(userRepository.findByUsernameAndIsDeletedFalse(username).isPresent())
            throw new NameAlreadyRegisteredException("Nombre de usuario ya registrado en el sistema");
    }

    /**
     * @apiNote Registra el usuario administrador por defecto si este no se encuenta
     * previamente registrado.
     */
    public void addAdmin(){
        if(userRepository.findByUsernameAndIsDeletedFalse(RoleType.ADMIN.name().toUpperCase()).isEmpty()){
            userRepository.save(
                    User.builder()
                            .username(RoleType.ADMIN.name().toUpperCase())
                            .fullname("Default System Administrator")
                            .password(passwordEncoder.encode("admin"))
                            .role(roleService.getRolByName(RoleType.ADMIN.name()))
                            .isDeleted(false)
                            .build()
            );
        }
    }

}
