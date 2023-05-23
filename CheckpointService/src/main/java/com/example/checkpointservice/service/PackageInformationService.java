package com.example.checkpointservice.service;

import com.example.basedomains.dto.PackageInformationDTO;
import com.example.basedomains.dto.PackageOnCheckpointDTO;
import com.example.basedomains.dto.ProcessPackageDTO;
import com.example.checkpointservice.kafka.producer.PackageIdProducer;
import com.example.checkpointservice.model.Checkpoint;
import com.example.checkpointservice.model.PackageInformation;
import com.example.checkpointservice.repository.CheckpointRepository;
import com.example.checkpointservice.repository.PackageInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PackageInformationService {

    @Autowired
    private PackageInformationRepository packageInformationRepository;

    @Autowired
    private CheckpointRepository checkpointRepository;

    @Autowired
    private PackageIdProducer packageIdProducer;

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

    public Page<PackageInformationDTO> getPackages(String pattern, int page, int size){
        Page<PackageInformation> packages;
        if(pattern == null)
            packages = packageInformationRepository.findByExitTimestampIsNull(PageRequest.of(page, size, Sort.by("id")));
        else
            packages = packageInformationRepository.findByIdStartingWithAndExitTimestampIsNull(pattern, PageRequest.of(page, size, Sort.by("id")));

        return new PageImpl<PackageInformationDTO>(
            packages.getContent().stream().map(
                    packet ->
                        PackageInformationDTO.builder()
                        .idPackage(packet.getPackageId())
                        .idCheckpoint(packet.getCheckpoint().getId())
                        .checkpointName(packet.getCheckpoint().getName())
                        .latitude(packet.getCheckpoint().getLatitude())
                        .length(packet.getCheckpoint().getLength())
                        .arrivalDate(packet.getArrivalTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                        .build())
                    .collect(Collectors.toList()),
            PageRequest.of(page, size, Sort.by("id")),
            packages.getTotalElements()
        );
    }

    public void processPackage(ProcessPackageDTO processPackageDTO){
        //Establecer la fecha de salida
        PackageInformation packageInformation = packageInformationRepository.findByPackageIdAndExitTimestampIsNull(processPackageDTO.getIdPackage());
        packageInformation.setExitTimestamp(new Date());
        packageInformationRepository.save(packageInformation);

        if(!processPackageDTO.isDelivered()){
            //Crear un nuevo registro hacia el siguiente checkpoint
            packageInformationRepository.save(
                    PackageInformation.builder()
                            .arrivalTimestamp(new Date())
                            .packageId(processPackageDTO.getIdPackage())
                            .checkpoint(checkpointRepository.findById(processPackageDTO.getIdNextCheckpoint()).get())
                            .build()
            );
        } else{
            //lanzar evento para marcar como entregado
            packageIdProducer.sendPackageId(processPackageDTO.getIdPackage());
        }
    }

}

