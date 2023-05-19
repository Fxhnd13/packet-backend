package com.example.checkpointservice.service;

import com.example.basedomains.exception.*;
import com.example.basedomains.dto.CheckpointDTO;
import com.example.checkpointservice.model.Checkpoint;
import com.example.checkpointservice.model.Fee;
import com.example.checkpointservice.repository.CheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class CheckpointService {

    @Autowired
    private CheckpointRepository checkpointRepository;

    @Autowired
    private FeeService feeService;

    @Autowired
    private PackageInformationService packageInformationService;


    /**
     * @apiNote Registra un nuevo punto de control. Hace un llamado para almacenar un nuevo registro en la tabla fee
     * correspondiente al punto de control que se esta creando.
     * @param checkpointDTO Datos del punto de control
     * @return El punto de control registrado dentro de un objeto ResponseEntity
     * @throws NameAlreadyRegisteredException
     * @throws RequiredFieldException
     * @throws NotANumberException
     */
    public ResponseEntity<Checkpoint> add(CheckpointDTO checkpointDTO) throws NameAlreadyRegisteredException, RequiredFieldException, NotANumberException {
        validateData(checkpointDTO);
        validateNameIsUnique(checkpointDTO.getName());

        Checkpoint checkpoint = null;
        checkpoint = checkpointRepository.save(
             new Checkpoint(
             0,
                 checkpointDTO.getLatitude(),
                 checkpointDTO.getLength(),
                 checkpointDTO.getName(),
         true,
        false
             )
        );

        feeService.add(checkpoint, checkpointDTO.getFee());
        return new ResponseEntity<Checkpoint>(checkpoint, HttpStatus.CREATED);
    }

    /**
     * @apiNote  Actualiza un punto de control. Realiza un llamado para actualizar la tarifa correspondiente al punto de control.
     * @param checkpointDTO Datos del punto de control
     * @throws NameAlreadyRegisteredException
     * @throws RequiredFieldException
     * @throws NotANumberException
     */
    public void update(CheckpointDTO checkpointDTO) throws NameAlreadyRegisteredException, RequiredFieldException, NotANumberException, NoEmptyCheckpointException {
        validateData(checkpointDTO);
        validateUpdatedNameIsUnique(checkpointDTO.getName(), checkpointDTO.getId());
        validateCheckpointIsEmpty(checkpointDTO.getId(), null);

        Checkpoint checkpoint = null;
        checkpoint = checkpointRepository.save(
            new Checkpoint(
                checkpointDTO.getId(),
                checkpointDTO.getLatitude(),
                checkpointDTO.getLength(),
                checkpointDTO.getName(),
                checkpointDTO.isActive(),
                checkpointDTO.isDeleted()
            )
        );
        feeService.update(checkpoint, checkpointDTO.getFee());
    }

    /**
     * @apiNote Actualiza el estado deleted de un punto de control.
     * @param id Id del punto de control
     * @throws NoEmptyCheckpointException
     * @throws ElementNoExistsException
     */
    public void delete(int id) throws NoEmptyCheckpointException, ElementNoExistsException {
        validateCheckpointIsEmpty(id, null);
        Checkpoint checkpoint = checkpointRepository.findByIdAndIsDeletedFalse(id);
        if (checkpoint == null)
            throw  new ElementNoExistsException();
        checkpoint.setDeleted(true);
        checkpointRepository.save(checkpoint);
    }

    /**
     * @apiNote  Obtiene un listado paginado de todos los puntos de control no eliminados sin importar su estado activo.
     * Permite obtener un listado en base a un patron de busqueda.
     * @param pattern Patron de busqueda
     * @param page Numero de pagina
     * @param size Tamaño de de pagina
     * @return Listado paginado de puntos de control
     */
    public Page<Checkpoint> getAll( String pattern,  int page, int size){
        if(pattern == null)
            return checkpointRepository.findByIsDeletedFalse(PageRequest.of(page, size, Sort.by("id")));
       if(pattern.matches("[0-9]+"))
            return checkpointRepository.findByIdStartingWith(Integer.parseInt(pattern), PageRequest.of(page, size, Sort.by("id")));
       else
           return checkpointRepository.findByIsDeletedFalseAndNameIgnoreCaseContaining(pattern, PageRequest.of(page, size, Sort.by("id")));
    }

    /**
     * @apiNote Obtiene un listado paginado de todos los puntos de control no eliminados y cuyo estado active sea true
     * Permite obtener un listado en base a un patron de busqueda.
     * @param pattern Patron de busqueda
     * @param page Numero de pagina
     * @param size Tamaño de la pagina
     * @return Listado paginado de puntos de control activos
     */
    public Page<Checkpoint> getAllActive( String pattern,  int page, int size){
        if(pattern == null)
            return checkpointRepository.findByIsDeletedFalseAndIsActiveTrue(PageRequest.of(page, size, Sort.by("id")));
        if(pattern.matches("[0-9]+"))
            return checkpointRepository.findByIdStartingWithAndIsActiveTrue(Integer.parseInt(pattern), PageRequest.of(page, size, Sort.by("id")));
        else
            return checkpointRepository.findByIsDeletedFalseAndIsActiveTrueAndNameIgnoreCaseContaining(pattern, PageRequest.of(page, size, Sort.by("id")));
    }

    public ResponseEntity<CheckpointDTO> getCheckpoint(int id) throws ElementNoExistsException {
        Checkpoint checkpoint = checkpointRepository.findByIdAndIsDeletedFalse(id);
        Fee fee = feeService.getCurrentOperativeFee(id);
        if (checkpoint == null || fee == null)
            throw  new ElementNoExistsException();

        return new ResponseEntity<CheckpointDTO>(
            CheckpointDTO.builder()
                .id(checkpoint.getId())
                .name(checkpoint.getName())
                .latitude(checkpoint.getLatitude())
                .length(checkpoint.getLength())
                .isActive(checkpoint.isActive())
                .isDeleted(checkpoint.isDeleted())
                .fee(fee.getAmount())
                .build(),
            HttpStatus.OK
        );
    }


    /**
     * @apiNote  Llama a la ejecucion de validaciones de campos obligatorios y a validacion de tarifa.
     * @param checkpointDTO Campos a validar
     * @throws RequiredFieldException
     * @throws NotANumberException
     * @throws NameAlreadyRegisteredException
     */
    private void validateData(CheckpointDTO checkpointDTO) throws RequiredFieldException, NotANumberException, NameAlreadyRegisteredException {
        validateRequiredFields(checkpointDTO);
        validateFee(checkpointDTO.getFee());
    }

    /**
     * @apiNote Valida que los atributos del punto de control no esten vacios y que no esten en blanco.
     * @param checkpointDTO Datos a validar
     * @throws RequiredFieldException
     */
    private void validateRequiredFields(CheckpointDTO checkpointDTO) throws RequiredFieldException {
        if((checkpointDTO.getLength() == null || checkpointDTO.getLength().isBlank() || checkpointDTO.getLength().isEmpty()) ||
            (checkpointDTO.getLatitude() == null || checkpointDTO.getLatitude().isBlank() || checkpointDTO.getLatitude().isEmpty()) ||
            (checkpointDTO.getName() == null || checkpointDTO.getName().isBlank() || checkpointDTO.getName().isEmpty())
        ) throw new RequiredFieldException();
    }

    /**
     * @apiNote Valida que la tarifa no sea vacia y que sea un numero valido.
     * @param fee Tarifa
     * @throws RequiredFieldException
     * @throws NotANumberException
     */
    private void validateFee(Double fee) throws RequiredFieldException, NotANumberException {
        if(fee == null)
            throw new RequiredFieldException();

        if(fee.isNaN())
            throw new NotANumberException();
    }

    /**
     * @apiNote  Valida que el nombre del punto de control no  este previamente registrado en otro punto de control.
     * @param name Nombre del punto de control
     * @throws NameAlreadyRegisteredException
     */
    private void validateNameIsUnique(String name) throws NameAlreadyRegisteredException {
        if (checkpointRepository.findByNameAndIsDeletedFalse(name) != null)
             throw new NameAlreadyRegisteredException("Nombre de punto de control ya registrado en el sistema.");
    }

    /**
     * @apiNote  Valida que el nombre del punto de control no este registrado en algun punto de control
     * que no sea el que esta identificado por el id recibido.
     * @param name Nombre del punto de control
     * @param id Id del punto de control
     * @throws NameAlreadyRegisteredException
     */
    private void validateUpdatedNameIsUnique(String name, int id) throws NameAlreadyRegisteredException{
        if (checkpointRepository.findByNameAndIdNotAndIsDeletedFalse(name, id) != null)
             throw new NameAlreadyRegisteredException("Nombre de punto de control ya registrado en el sistema.");
    }

    /**
     * @apiNote  Valida que no exista ningun paquete por procesar en el punto de control
     * cuyo id se recibe como parametro.
     * @param id Id del punto de control
     * @param date Fecha
     * @throws NoEmptyCheckpointException
     */
    private void validateCheckpointIsEmpty(int id, Date date) throws NoEmptyCheckpointException {
        if (!packageInformationService.isEmptyCheckpoint(id, date))
            throw new NoEmptyCheckpointException();
    }

}
