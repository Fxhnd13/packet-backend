package com.example.packetservice.service;

import com.example.basedomains.dto.OrderDTO;
import com.example.basedomains.dto.PackageDTO;
import com.example.basedomains.dto.ProcessPackageDTO;
import com.example.basedomains.dto.ProcessPackageRequest;
import com.example.packetservice.kafka.producer.OrderProducer;
import com.example.packetservice.kafka.producer.ProcessPackageProducer;
import com.example.packetservice.model.Fee;
import com.example.packetservice.model.Package;
import com.example.packetservice.respository.FeeRepository;
import com.example.packetservice.respository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private ProcessPackageProducer processPackageProducer;

    /**
     * @apiNote  Crea cada uno de los paquetes que se reciben como parametro y almacena los id de los paquetes creados en una lista.
     * Lanza un evento para registar los ids de los paquetes con su respectiva orden.
      * @param orderDTO
     */
    public void addPackages(OrderDTO orderDTO){
        Package tempPackage = null;
        for (PackageDTO currentPackage : orderDTO.getPackages()) {
            tempPackage = packageRepository.save(
                    Package.builder()
                            .weight(currentPackage.getWeight())
                            .priority(currentPackage.isPrioritized())
                            .incomeDate(currentPackage.getIncomeDate())
                            .deliveryDate(null)
                            .deliveryAddress(currentPackage.getDeliveryAddress())
                            .fee(getFee(currentPackage.isPrioritized()))
                            .routeId(currentPackage.getRouteId())
                            .build()
            );
            currentPackage.setId(tempPackage.getId());
        }
        orderProducer.sendOrderToClientService(orderDTO);
        orderProducer.sendOrderToRouteService(orderDTO);
    }


    /**
     * @apiNote Obtiene la tafira en base al valor booleano del parametro que se recibe.
     * @param prioritized
     * @return
     */
    public Fee getFee(boolean prioritized){
        if(prioritized)
            return feeRepository.findByIsActiveTrueAndPriorityTrue();
        else
            return feeRepository.findByIsActiveTrueAndPriorityFalse();
    }

    public Page<Package> getDeliveredPackages(String pattern,  int page, int size){
        if(pattern == null)
            return packageRepository.findByIDeliveryDateNull(PageRequest.of(page, size, Sort.by("id")));
        else
            return packageRepository.findByIdStartingWithAndDeliveryDateNull(pattern, PageRequest.of(page, size, Sort.by("id")));
    }

    public Page<Package> getPackages(String pattern,  int page, int size){
        if(pattern == null)
            return packageRepository.findAll(PageRequest.of(page, size, Sort.by("id")));
        else
            return packageRepository.findByIdLike(pattern, PageRequest.of(page, size, Sort.by("id")));
    }

    public void processPackage(ProcessPackageRequest processPackageRequest){
        Package packet = packageRepository.findById(processPackageRequest.getIdPackage()).get();

        //Lanzar evento
        processPackageProducer.sendProcessPackage(
            ProcessPackageDTO.builder()
                .idPackage(processPackageRequest.getIdPackage())
                .idCurrentCheckpoint(processPackageRequest.getIdCheckpoint())
                .idRoute(packet.getRouteId())
                .build()
        );
    }

    public void deliver(int id){
        Package packet =  packageRepository.findById(id).get();
        packet.setDeliveryDate(LocalDate.now());
        packageRepository.save(packet);
    }

}
