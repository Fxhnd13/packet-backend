package com.example.checkpointservice.service;

import com.example.checkpointservice.dto.CheckpointDTO;
import com.example.checkpointservice.model.Checkpoint;
import com.example.checkpointservice.model.Fee;
import com.example.checkpointservice.model.FeeType;
import com.example.checkpointservice.repository.CheckpointRepository;
import com.example.checkpointservice.source.CheckpointFeeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CheckpointService {

    @Autowired
    private CheckpointRepository checkpointRepository;

    @Autowired
    private FeeService feeService;

    @Autowired
    private FeeTypeService feeTypeService;

    public Checkpoint add(CheckpointDTO checkpointDTO){
        Checkpoint checkpoint = null;
        checkpoint = checkpointRepository.save(
             new Checkpoint(
             0,
                 checkpointDTO.getLatitude(),
                 checkpointDTO.getLength(),
                 checkpointDTO.getName(),
         true,
        false
             )
        );
        addCheckpointFee(checkpoint, checkpointDTO.getFee());
        return checkpoint;
    }

    private void addCheckpointFee(Checkpoint checkpoint, double fee){
        FeeType feeType = feeTypeService.findByName(CheckpointFeeType.OPERATIVE.name());
        feeService.add(
            new Fee(
            0,
                checkpoint,
                new FeeType(feeType.getId(),  feeType.getName() , feeType.getDescription()),
                fee,
                new Date()
            )
        );
    }

}
