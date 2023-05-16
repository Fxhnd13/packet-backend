package com.example.checkpointservice.service;

import com.example.checkpointservice.repository.PackageInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PackageInformationService {

    @Autowired
    private PackageInformationRepository packageInformationRepository;

    public boolean isEmptyCheckpoint(int checkpointId, Date date){
        return  packageInformationRepository.countByCheckpointIdAndExitTimestamp(checkpointId, date) == 0;
    }
}

