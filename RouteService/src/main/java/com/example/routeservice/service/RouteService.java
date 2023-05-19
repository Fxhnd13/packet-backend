package com.example.routeservice.service;

import com.example.basedomains.dto.CheckpointDTO;
import com.example.basedomains.dto.RouteDTO;
import com.example.basedomains.exception.*;
import com.example.routeservice.model.Edge;
import com.example.routeservice.model.Route;
import com.example.routeservice.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private EdgeService edgeService;

    @Autowired
    private PathService pathService;

    /**
     * @apiNote Registra en la base de datos una nueva ruta. Realiza un llamado para estructurar el camino de la ruta
     * @param routeDTO
     * @return El objeto Route registrado sin su camino.
     * @throws NameAlreadyRegisteredException
     * @throws RequiredFieldException
     * @throws EmptyRouteException
     */
    public ResponseEntity<Route> add(RouteDTO routeDTO) throws NameAlreadyRegisteredException, RequiredFieldException, EmptyRouteException{
        validateData(routeDTO);
        validateNameIsUnique(routeDTO.getName());

        Route route =  routeRepository.save(
            Route.builder()
                .name(routeDTO.getName())
                .description(routeDTO.getDescription())
                .isActive(true)
                .isDeleted(false)
                .build()
        );

        createPath(routeDTO.getCheckpoints(), route);
        return new ResponseEntity<Route>(route, HttpStatus.CREATED);
    }

    /**
     * @apiNote Llama a metodos para validar que los datos no esten vacios y que existen puntos de control
     * @param routeDTO Datos a validar
     * @throws EmptyRouteException
     * @throws RequiredFieldException
     */
    public void validateData(RouteDTO routeDTO) throws EmptyRouteException, RequiredFieldException {
        validateRequiredFields(routeDTO);
        validateCheckpointsAmount(routeDTO.getCheckpoints());
    }
    /**
     * @apiNote Valida que los atributos de la ruta no esten vacios y que no esten en blanco
     * @param routeDTO Datos de la ruta
     * @throws RequiredFieldException
     */
    private void validateRequiredFields(RouteDTO routeDTO) throws RequiredFieldException {
        if((routeDTO.getName() == null || routeDTO.getName().isBlank() || routeDTO.getName().isEmpty()) ||
            (routeDTO.getDescription() == null || routeDTO.getDescription().isBlank() || routeDTO.getDescription().isEmpty())
        ) throw new RequiredFieldException();
    }

    /**
     * @apiNote Valida que el listado de puntos de control sea mayor a 0
     * @param checkpoints Listado de puntos de control
     * @throws EmptyRouteException
     */
    private void validateCheckpointsAmount(List<CheckpointDTO> checkpoints) throws EmptyRouteException {
        if(checkpoints == null || !(checkpoints.size() > 0))
            throw new EmptyRouteException();
    }

    /**
     * @apiNote  Valida que el nombre de la rutal no  este previamente registrado en otral.
     * @param name Nombre de la ruta
     * @throws NameAlreadyRegisteredException
     */
    private void validateNameIsUnique(String name) throws NameAlreadyRegisteredException {
        if (routeRepository.findByNameAndIsDeletedFalse(name) != null)
            throw new NameAlreadyRegisteredException("Nombre de la ruta ya registrado en el sistema.");
    }

    /**
     * @apiNote Crea el camino que conforma una ruta. Hace un llamado para registrar los puntos de control de inicio y fin de
     * cada arista, en el caso del ultimo punto de control este es tanto el inicial como el final. Adicionalmente registra la arista
     * y la ruta a la que pertenece.
     * @param checkpoints Listado de puntos de control que conformaran las ruta
     * @param route Ruta
     */
    private void createPath(List<CheckpointDTO> checkpoints, Route route){
        Edge edge = null;
        for(int i=0; i < checkpoints.size(); i++){
            if(i+1 == checkpoints.size())
                edge = edgeService.addEdge(checkpoints.get(i).getId(), checkpoints.get(i).getId());
            else
                edge = edgeService.addEdge(checkpoints.get(i).getId(), checkpoints.get(i+1).getId());

            pathService.addPath(route, edge);
        }
    }

}
