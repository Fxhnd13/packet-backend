package com.example.clientservice.service;

import com.example.clientservice.model.Client;
import com.example.clientservice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public void add(Client client){
        clientRepository.save(client);
    }

    public String getEmail(String nit){
        return clientRepository.findByNit(nit).getEmail();
    }
}
