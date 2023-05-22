package com.example.clientservice.kafka.consumer;

import com.example.basedomains.dto.ClientDTO;
import com.example.clientservice.model.Client;
import com.example.clientservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class ClientConsumer {

    @Autowired
    private ClientService clientService;


    /**
     * @apiNote Captura un evento y llama al registro de un nuevo cliente en la base de datos.
     * @param clientDTO Datos del cliente a registrar
     */
    @KafkaListener(topics = "client", containerFactory = "clientKafkaListenerContainerFactory")
    public void consume(ClientDTO clientDTO){
        clientService.add(
                Client.builder()
                        .fullname(clientDTO.getFullname())
                        .email(clientDTO.getEmail())
                        .nit(clientDTO.getNit())
                        .address(clientDTO.getAddress())
                        .numberPhone(clientDTO.getNumberPhone())
                        .age(clientDTO.getAge())
                        .idUser(clientDTO.getIdUser())
                        .build()
        );
    }

}

