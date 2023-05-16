package com.example.authenticationservice.service;

import com.example.authenticationservice.models.Role;
import com.example.authenticationservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {

    @Autowired
    private RoleRepository roleRepository;
    public Role getRolByName(String name){
        return roleRepository.findByName(name);
    }
}
