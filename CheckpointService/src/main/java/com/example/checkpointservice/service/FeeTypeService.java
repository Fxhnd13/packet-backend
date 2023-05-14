package com.example.checkpointservice.service;

import com.example.checkpointservice.source.CheckpointFeeType;
import com.example.checkpointservice.model.FeeType;
import com.example.checkpointservice.repository.FeeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class FeeTypeService {

    @Autowired
    private FeeTypeRepository feeTypeRepository;

    /**
     * Si no existen datos acerca del tipo de tarifa, inserta en la base de datos los dos tipos de tarifa
     * que existen para un punto de control.
     */
    @EventListener(ApplicationReadyEvent.class)
    private  void addFeeTypes(){
        if(feeTypeRepository.count() == 0){
            feeTypeRepository.save(new FeeType(1, CheckpointFeeType.OPERATIVE.name(), "Costo operacional"));
            feeTypeRepository.save(new FeeType(2, CheckpointFeeType.TAGS.name(), "Impuestos varios"));
        }

    }

    public FeeType findByName(String name){
        return feeTypeRepository.findByName(name);
    }
}
