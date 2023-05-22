package com.example.packetservice.service;

import com.example.basedomains.dto.FeeDTO;
import com.example.basedomains.constants.Constants;
import com.example.packetservice.model.Fee;
import com.example.packetservice.respository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FeeService {

    @Autowired
    private FeeRepository feeRepository;

    /**
     * Crea una nueva tarifa(Fee) en la base de datos.
     * @param fee
     * @param prioritized
     */
    private void addFee(double fee, boolean prioritized){
        feeRepository.save(
                Fee.builder()
                        .amount(fee)
                        .creationDate(new Date())
                        .isActive(true)
                        .priority(prioritized)
                        .build()
        );
    }

    /**
     * @apiNote Obtiene el valor de la tarifa normal y la tarifa priorizada.
     * @return Tarifas obtenidas
     */
    public FeeDTO getFee(){
        return FeeDTO.builder()
                .fee(feeRepository.findByIsActiveTrueAndPriorityFalse().getAmount())
                .prioritizedFee(feeRepository.findByIsActiveTrueAndPriorityTrue().getAmount())
                .build();
    }

    /**
     * @apiNote Llama a la actualizacion de las tarifas.
      * @param feeDTO
     */
    public void updateFee(FeeDTO feeDTO){
        updateNoPrioritizedFee(feeDTO.getFee());
        updatePrioritizedFee(feeDTO.getPrioritizedFee());
    }

    /**
     * @apiNote Si la tarifa no es igual a la tarifa actual actualiza el valor active de la tarifa actual e inserta una nueva
     * tarifa no priorizada en la base de datos.
     * @param fee
     */
    private void updateNoPrioritizedFee(Double fee){
        Fee tempFee = feeRepository.findByIsActiveTrueAndPriorityFalse();
        if(tempFee.getAmount() != fee){
            tempFee.setActive(false);
            feeRepository.save(tempFee);
            addFee(fee, false);
        }
    }

    /**
     * @apiNote Si la tarifa priorizada no es igual a la tarifa actual actualiza el valor active de la tarifa actual e inserta una nueva
     * tarifa  priorizada en la base de datos.
     * @param prioritizedFee
     */
    private void updatePrioritizedFee(Double prioritizedFee){
        Fee tempFee = feeRepository.findByIsActiveTrueAndPriorityTrue();
        if(tempFee.getAmount() != prioritizedFee){
            tempFee.setActive(false);
            feeRepository.save(tempFee);
            addFee(prioritizedFee, true);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    private void addDefaultFee(){
        if(feeRepository.findByIsActiveTrueAndPriorityFalse() == null){
            addFee(Constants.DEFAULT_FEE, false);
        }

        if(feeRepository.findByIsActiveTrueAndPriorityTrue() == null){
            addFee(Constants.DEFAULT_PRIORITIZED_FEE, true);
        }
    }
}
