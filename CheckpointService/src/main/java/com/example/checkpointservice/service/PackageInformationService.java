package com.example.checkpointservice.service;

import com.example.checkpointservice.source.PackageInformationDTO;
import com.example.basedomains.dto.PackageOnCheckpointDTO;
import com.example.checkpointservice.model.PackageInformation;
import com.example.checkpointservice.repository.CheckpointRepository;
import com.example.checkpointservice.repository.PackageInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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


    public Page<PackageInformationDTO> getPackages(int page, int size){
        Page<PackageInformation> packages = packageInformationRepository.findByExitTimestampIsNull(PageRequest.of(page, size, Sort.by("id")));

        return new PageImpl<PackageInformationDTO>(
            packages.getContent().stream().map(packet -> new PackageInformationDTO(packet.getPackageId(), packet.getCheckpoint())).collect(Collectors.toList()),
            PageRequest.of(page, size, Sort.by("id")),
            packages.getTotalElements()
        );
    }

}

