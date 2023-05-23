package com.example.routeservice.service;

import com.example.basedomains.dto.ProcessPackageDTO;
import com.example.routeservice.kafka.producer.ProcessPackageProducer;
import com.example.routeservice.model.Edge;
import com.example.routeservice.model.Path;
import com.example.routeservice.model.Route;
import com.example.routeservice.repository.PathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PathService {

    @Autowired
    private PathRepository pathRepository;

    @Autowired
    private ProcessPackageProducer processPackageProducer;


    /**
     * @apiNote Registra un nuevo Path
     * @param route Ruta a la que pertenece el path
     * @param edge Arista que conforma el path
     */
    public void addPath(Route route, Edge edge){
        pathRepository.save(
            Path.builder()
              .edge(edge)
              .route(route)
              .build()
        );
    }

    public void deleteAllPathsByRoute(int id){
        pathRepository.deleteAllByRouteId(id);
    }

    /**
     * @apiNote Obtiene un listado con los id's de los edge que estan registrados con un id de ruta
     * igual al parametro que se recibe.
     * @param id
     * @return
     */
    public List<Path> getEdgesByRouteId(int id){
        return pathRepository.findByRouteId(id);
    }

    public Path getFirstPathByRoute(int id){
        return  pathRepository.findFirstByRouteIdOrderByIdAsc(id);
    }

    public void processPackage(ProcessPackageDTO processPackageDTO){
       //Obtener todos los paths que conforman la ruta
        List<Path> paths = pathRepository.findAllByRouteIdOrderByIdAsc(processPackageDTO.getIdRoute());

        //Buscar cual es el path actual, obtener el id del siguiente checkpoint.
        for (Path path : paths) {
            if(path.getEdge().getInitialCheckpointId() == processPackageDTO.getIdCurrentCheckpoint()) {

                //Identificar si es el ultimpo punto de control
                if (path.getEdge().getInitialCheckpointId() == path.getEdge().getFinalCheckpointId()){
                   processPackageDTO.setDelivered(true);
                } else{
                    processPackageDTO.setIdNextCheckpoint(path.getEdge().getFinalCheckpointId());
                    processPackageDTO.setDelivered(false);
                }
                break;
            }
        }

        //Lanzar evento para registar el movimiento
        processPackageProducer.sendProcessPackage(processPackageDTO);
    }

}
