package com.example.checkpointservice.service;

import com.example.basedomains.dto.PackageOnCheckpointDTO;
import com.example.checkpointservice.model.PackageInformation;
import com.example.checkpointservice.repository.CheckpointRepository;
import com.example.checkpointservice.repository.PackageInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PackageInformationService {

    @Autowired
    private PackageInformationRepository packageInformationRepository;

    @Autowired
    private CheckpointRepository checkpointRepository;

    public boolean isEmptyCheckpoint(int checkpointId, Date date){
        return  packageInformationRepository.countByCheckpointIdAndExitTimestamp(checkpointId, date) == 0;
    }

    public void addPackage(List<PackageOnCheckpointDTO> packages){
        for (PackageOnCheckpointDTO currentPackage : packages) {
            packageInformationRepository.save(
                    PackageInformation.builder()
                        .packageId(currentPackage.getPackageId())
                        .checkpoint(checkpointRepository.findByIdAndIsDeletedFalse(currentPackage.getCheckpointId()))
                        .arrivalTimestamp(new Date())
                        .build()
            );
        }
    }
}

