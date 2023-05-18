package com.example.authenticationservice.service;

import com.example.authenticationservice.models.Role;
import com.example.authenticationservice.repository.RoleRepository;
import com.example.authenticationservice.source.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * @apiNote Busca un rol en base al nombre.
     * @param name
     * @return Rol encontrado
     */
    public Role getRolByName(String name){
        return roleRepository.findByName(name);
    }

    /**
     * @apiNote Registra los roles de usuarios en el sistema si estos no se encuentan
     * previamente registrados
     */
    public  void addRoles(){
        if(roleRepository.count() == 0){
            roleRepository.save(new Role(1, RoleType.ADMIN.name(), "Administador"));
            roleRepository.save(new Role(2, RoleType.OPERATOR.name(), "Operador"));
            roleRepository.save(new Role(3, RoleType.CLIENT.name(), "Cliente"));
        }
    }
}
