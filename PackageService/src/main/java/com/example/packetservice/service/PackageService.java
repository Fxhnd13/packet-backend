package com.example.packetservice.service;

import com.example.packetservice.kafka.producer.ProcessPackageProducer;
import com.example.packetservice.kafka.producer.OrderProducer;
import com.example.packetservice.kafka.producer.PayProducer;
import com.example.basedomains.dto.OrderDTO;
import com.example.basedomains.dto.PackageDTO;
import com.example.basedomains.dto.PayDTO;
import com.example.basedomains.dto.ProcessPackageDTO;
import com.example.basedomains.dto.ProcessPackageRequest;
import com.example.packetservice.model.Fee;
import com.example.packetservice.model.Package;
import com.example.packetservice.respository.FeeRepository;
import com.example.packetservice.respository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private PayProducer payProducer;

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

    public ResponseEntity<String> requestBankPayment(PayDTO payDTO){
        try{
            payProducer.sendPayToPaymentService(payDTO);
            return ResponseEntity.ok().body("Se ha realizado la petición de pago al banco con tarjeta de crédito");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ha ocurrido un error al realizar la petición de pago al banco con tarjeta de crédito");
        }
    }

    public ResponseEntity<String> requestCashPayment(int packageId) {
        try{
            Package packet = packageRepository.findPackageById(packageId);
            packet.setPaid(true);
            packageRepository.save(packet);
            return ResponseEntity.ok().body("Se ha registrado el pago correctamente");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se ha podido registrar el pago correctamente inténtelo de nuevo");
        }
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
