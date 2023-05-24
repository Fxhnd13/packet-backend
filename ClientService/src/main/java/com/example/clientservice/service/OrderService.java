package com.example.clientservice.service;

import com.example.basedomains.dto.OrderDTO;
import com.example.basedomains.dto.PackageDTO;
import com.example.basedomains.exception.ElementNoExistsException;
import com.example.basedomains.exception.EmptyOrderException;
import com.example.clientservice.kafka.producer.OrderProducer;
import com.example.clientservice.model.Client;
import com.example.clientservice.model.Order;
import com.example.clientservice.repository.ClientRepository;
import com.example.clientservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderProducer orderProducer;

    /**
     * @apiNote Crear una nueva orden en la base de datos si esta tiene por lo menos un paquete y si el nit del cliente proporcionado si
     * existen. Llama al lanzanmiento de un evento para registrar los paquetes de la orden.
     * @param orderDTO
     * @return
     * @throws ElementNoExistsException
     * @throws EmptyOrderException
     */
    public Order add(OrderDTO orderDTO) throws ElementNoExistsException, EmptyOrderException {
        Client client = clientRepository.findByNit(orderDTO.getNit());
        if(client == null)
            throw  new ElementNoExistsException("No existe ningun cliente registrado con ese NIT.");

        if(orderDTO.getPackages().size() == 0)
            throw  new EmptyOrderException();

        Order order = orderRepository.save(
            Order.builder()
                    .client(client)
                    .creationDate(LocalDate.now())
                    .total(getTotal(orderDTO.getPackages()))
                    .build()
        );

        orderDTO.setId(order.getId());
        orderProducer.sendOrder(orderDTO);
        return order;
    }

    /**
     * @apiNote Calcula el valor total de la orden en base a las tarifas normal y priorizada y al peso de cada paquete.
     * @param packages
     * @return
     */
    private double getTotal(List<PackageDTO> packages){
        double total = 0;
        for (PackageDTO currentPackage : packages) {
            if (currentPackage.isPrioritized())
                total = total + currentPackage.getWeight() * currentPackage.getFee();
            else
                total = total + currentPackage.getWeight() * currentPackage.getPrioritizedFee();
        }
        return total;
    }

    public Order getOrder(int id){
        return orderRepository.findById(id).get();
    }
}
