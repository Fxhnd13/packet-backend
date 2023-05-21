package com.example.routeservice.service;

import com.example.basedomains.dto.CheckpointDTO;
import com.example.basedomains.dto.RouteDTO;
import com.example.basedomains.exception.*;
import com.example.routeservice.model.Edge;
import com.example.routeservice.model.Path;
import com.example.routeservice.model.Route;
import com.example.routeservice.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                .packagesOnRoute(0)
                .build()
        );

        createPath(routeDTO.getCheckpoints(), route);
        return new ResponseEntity<Route>(route, HttpStatus.CREATED);
    }

    /**
     * @apiNote  Actualiza una ruta  Realiza un llamado para actualizar la estructura de la ruta..
     * @param routeDTO Datos de la ruta
     * @throws NameAlreadyRegisteredException
     * @throws RequiredFieldException
     * @throws NotANumberException
     */
    public void update(RouteDTO routeDTO) throws NameAlreadyRegisteredException, RequiredFieldException, NoEmptyRouteException, EmptyRouteException, ElementNoExistsException {
        validateData(routeDTO);
        validateRouteIsEmpty(routeDTO.getId());
        validateUpdatedNameIsUnique(routeDTO.getName(), routeDTO.getId());

        Route route =  routeRepository.save(
            Route.builder()
                .id(routeDTO.getId())
                .name(routeDTO.getName())
                .description(routeDTO.getDescription())
                .isDeleted(false)
                .isActive(routeDTO.isActive())
                .packagesOnRoute(routeDTO.getPackagesOnRoute())
                .build()
        );

        updatePath(routeDTO.getCheckpoints(), route);
    }

    /**
     * @apiNote Actualiza el estado deleted de una ruta
     * @param id Id de la ruta
     * @throws NoEmptyCheckpointException
     * @throws ElementNoExistsException
     */
    public void delete(int id) throws NoEmptyRouteException, ElementNoExistsException {
        validateRouteIsEmpty(id);
        Route route = routeRepository.findByIdAndIsDeletedFalse(id);
        if (route == null)
            throw  new ElementNoExistsException();
        route.setDeleted(true);
        routeRepository.save(route);
    }

    /**
     * @apiNote  Obtiene un listado paginado de todas las rutas no eliminadas sin importar su estado activo.
     * Permite obtener un listado en base a un patron de busqueda.
     * @param pattern Patron de busqueda
     * @param page Numero de pagina
     * @param size Tamaño de de pagina
     * @return Listado paginado de rutas
     */
    public Page<Route> getAll( String pattern,  int page, int size){
        if(pattern == null)
            return routeRepository.findByIsDeletedFalse(PageRequest.of(page, size, Sort.by("id")));
        if(pattern.matches("[0-9]+"))
            return routeRepository.findByIdStartingWith(Integer.parseInt(pattern), PageRequest.of(page, size, Sort.by("id")));
        else
            return routeRepository.findByIsDeletedFalseAndNameIgnoreCaseContaining(pattern, PageRequest.of(page, size, Sort.by("id")));
    }

    /**
     * @apiNote Obtiene un listado paginado de todas las rutas no eliminadas y cuyo estado active sea true
     * Permite obtener un listado en base a un patron de busqueda.
     * @param pattern Patron de busqueda
     * @param page Numero de pagina
     * @param size Tamaño de la pagina
     * @return Listado paginado de rutas activas
     */
    public Page<Route> getAllActive( String pattern,  int page, int size){
        if(pattern == null)
            return routeRepository.findByIsDeletedFalseAndIsActiveTrue(PageRequest.of(page, size, Sort.by("id")));
        if(pattern.matches("[0-9]+"))
            return routeRepository.findByIdStartingWithAndIsActiveTrue(Integer.parseInt(pattern), PageRequest.of(page, size, Sort.by("id")));
        else
            return routeRepository.findByIsDeletedFalseAndIsActiveTrueAndNameIgnoreCaseContaining(pattern, PageRequest.of(page, size, Sort.by("id")));
    }

   /* public ResponseEntity<RouteDTO> getRoute(int id) throws ElementNoExistsException {
        Route route = routeRepository.findByIdAndIsDeletedFalse(id);
        if (route == null)
            throw  new ElementNoExistsException();

        List<Path> paths = pathService.getEdgesByRouteId(route.getId());

        for(int i=0; i<paths.size(); i++){

        }

    }*/

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
     * @apiNote  Valida que el nombre de la ruta no  este previamente registrado en otra.
     * @param name Nombre de la ruta
     * @throws NameAlreadyRegisteredException
     */
    private void validateNameIsUnique(String name) throws NameAlreadyRegisteredException {
        if (routeRepository.findByNameAndIsDeletedFalse(name) != null)
            throw new NameAlreadyRegisteredException("Nombre de la ruta ya registrado en el sistema.");
    }

    /**
     * @apiNote  Valida que el nombre de la ruta  no este registrado en otra que no sea la misma que la del id
     * que se recibe como parametro..
     * @param name Nombre de la ruta
     * @param id id de la ruta
     * @throws NameAlreadyRegisteredException
     */
    private void validateUpdatedNameIsUnique(String name, int id) throws NameAlreadyRegisteredException {
        if (routeRepository.findByNameAndIdNotAndIsDeletedFalse(name, id) != null)
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

    /**
     * @apiNote Valida que la ruta no tenga paquetes por procesar.
      * @param idRoute Id de la ruta
     * @throws NoEmptyRouteException
     */
    private void validateRouteIsEmpty(int idRoute) throws NoEmptyRouteException, ElementNoExistsException {
        Optional<Route> optionalRoute = routeRepository.findById(idRoute);

        if(!optionalRoute.isPresent())
            throw  new ElementNoExistsException();

        if(optionalRoute.get().getPackagesOnRoute() > 0 )
                throw new NoEmptyRouteException();
    }

    /**
     * @apiNote Actualiza la trayectoria de la ruta. Obtiene un listado con todos los paths que etan relacionados con la ruta y posteriormente los elimina.
     * Despues elimina cada una de las edge relacionadas con los paths que se obtuvieron y llama a crear nuevamente la trayertoria de la ruta.
     * @param checkpoints
     * @param route
     */
    private void updatePath(List<CheckpointDTO> checkpoints, Route route){
        List<Path>  paths= pathService.getEdgesByRouteId(route.getId());
        pathService.deleteAllPathsByRoute(route.getId());
        for (Path path : paths) {
            edgeService.deleteEdge(path.getEdge().getId());
        }
        createPath(checkpoints, route);
    }
}
