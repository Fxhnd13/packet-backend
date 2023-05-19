package com.example.routeservice.service;

import com.example.routeservice.model.Edge;
import com.example.routeservice.repository.EdgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EdgeService {

    @Autowired
    private EdgeRepository edgeRepository;

    /**
     * @apiNote Registra una nueva arista en la base de datos.
     * @param initialCheckpointId Punto de control inicial
     * @param finalCheckpointId Punto de control final
     * @return Arista creada
     */
    public Edge addEdge(int initialCheckpointId, int finalCheckpointId){
        Edge edge = edgeRepository.save(
            Edge.builder()
                .initialCheckpointId(initialCheckpointId)
                .finalCheckpointId(finalCheckpointId)
                .build()
        );
        return edge;
    }

}
