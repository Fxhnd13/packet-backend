package com.example.clientservice.service;

import com.example.basedomains.dto.NotificationDTO;
import com.example.basedomains.dto.OrderDTO;
import com.example.clientservice.kafka.producer.NotificationProducer;
import com.example.clientservice.model.Order;
import com.example.clientservice.model.PackageOnOrder;
import com.example.clientservice.repository.PackageOnOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageOnOrderService {

    @Autowired
    private PackageOnOrderRepository packageOnOrderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private NotificationProducer notificationProducer;

    @Autowired
    private ClientService clientService;

    public void addPackagesOnOrder(OrderDTO orderDTO){
        Order order = orderService.getOrder(orderDTO.getId());

        for(int i=0; i<orderDTO.getPackages().size(); i++){
            packageOnOrderRepository.save(
                    PackageOnOrder.builder()
                            .order(order)
                            .packageId(orderDTO.getPackages().get(i).getId())
                            .build()
            );
        }

        notificationProducer.sendNotification(new NotificationDTO("Orden Procesada Exitosamente No." +orderDTO.getId(), clientService.getEmail(orderDTO.getNit())));
    }
}
