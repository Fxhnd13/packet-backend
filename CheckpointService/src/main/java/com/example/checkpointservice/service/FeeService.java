package com.example.checkpointservice.service;


import com.example.checkpointservice.model.Checkpoint;
import com.example.checkpointservice.model.Fee;
import com.example.checkpointservice.repository.FeeRepository;
import com.example.checkpointservice.source.CheckpointFeeType;
import com.example.checkpointservice.source.NotANumberException;
import com.example.checkpointservice.source.RequiredFieldException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FeeService {

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private FeeTypeService feeTypeService;

    public void add(Fee fee){
        feeRepository.save(fee);
    }

    public void add(Checkpoint checkpoint, Double fee) throws RequiredFieldException, NotANumberException {
        add(
            new Fee(
            0,
                checkpoint,
                feeTypeService.findByName(CheckpointFeeType.OPERATIVE.name()),
                fee,
                new Date()
            )
        );
    }

    /**
     * Metodo que compara si el valor de la tarifa  del punto de control que se recibe como
     * parametro es igual a la cantidad que se recibe en el parametro amount, si no son iguales
     * se hace un llamado para guardar la nueva tarifa.
     * @param checkpoint
     * @param amount
     */
    public void update(Checkpoint checkpoint, double amount) {
        Fee fee = feeRepository.findFirstByCheckpointIdAndFeeTypeIdOrderByDateDesc(checkpoint.getId(), feeTypeService.findByName(CheckpointFeeType.OPERATIVE.name()).getId());
        if (fee.getAmount() != amount) {
            add(new Fee(0, checkpoint, fee.getFeeType(), amount, new Date()));
        }
    }

}
