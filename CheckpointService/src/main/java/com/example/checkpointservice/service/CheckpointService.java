package com.example.checkpointservice.service;

import com.example.checkpointservice.dto.CheckpointDTO;
import com.example.checkpointservice.model.Checkpoint;
import com.example.checkpointservice.repository.CheckpointRepository;
import com.example.checkpointservice.source.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class CheckpointService {

    @Autowired
    private CheckpointRepository checkpointRepository;

    @Autowired
    private FeeService feeService;

    @Autowired
    private PackageInformationService packageInformationService;

    /**
     * Metodo que guarda un nuevo punto de control. Hace un llamado para almacenar un nuevo regsitro en la tabla fee
     * correspondiente al punto de control que se esta creando.
     * @param checkpointDTO
     * @return
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
     * Metodo que actualiza un punto de control. Realiza un llamado para actualizar la
     * tarifa correspondiente al punto de control actualizado.
     * @param checkpointDTO
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

    public void delete(int id) throws NoEmptyCheckpointException, ElementNoExistsException {
        validateCheckpointIsEmpty(id, null);
        Checkpoint checkpoint = get(id);
        checkpoint.setDeleted(true);
        checkpointRepository.save(checkpoint);
    }

    public Checkpoint get(int id) throws ElementNoExistsException {
        Optional<Checkpoint> object =  checkpointRepository.findByIdAndIsDeleted(id, false);
        if(object.isEmpty())
            throw new ElementNoExistsException();
        return object.get();
    }

    public Page<Checkpoint> getAll( String pattern,  int page, int size){
        if(pattern == null)
            return checkpointRepository.findByIsDeletedFalse(PageRequest.of(page, size, Sort.by("id")));

       if(pattern.matches("[0-9]+"))
            return checkpointRepository.findByIdStartingWith(Integer.parseInt(pattern), PageRequest.of(page, size, Sort.by("id")));

       else
           return checkpointRepository.findByIsDeletedFalseAndNameIgnoreCaseContaining(pattern, PageRequest.of(page, size, Sort.by("id")));
    }

    public Page<Checkpoint> getAllActive( String pattern,  int page, int size){
        if(pattern == null)
            return checkpointRepository.findByIsDeletedFalseAndIsActiveTrue(PageRequest.of(page, size, Sort.by("id")));

        if(pattern.matches("[0-9]+"))
            return checkpointRepository.findByIdStartingWithAndIsActiveTrue(Integer.parseInt(pattern), PageRequest.of(page, size, Sort.by("id")));

        else
            return checkpointRepository.findByIsDeletedFalseAndIsActiveTrueAndNameIgnoreCaseContaining(pattern, PageRequest.of(page, size, Sort.by("id")));
    }

    /**
     * Metodo que llama a la ejecucion de validaciones de campos obligatorios
     * y a validacion de tarifa.
     * @param checkpointDTO
     * @throws RequiredFieldException
     * @throws NotANumberException
     * @throws NameAlreadyRegisteredException
     */
    private void validateData(CheckpointDTO checkpointDTO) throws RequiredFieldException, NotANumberException, NameAlreadyRegisteredException {
        validateRequiredFields(checkpointDTO);
        validateFee(checkpointDTO.getFee());
    }

    /**
     * Metodo que valida que los atributos del punto de control no esten vacios y que no
     * esten en blanco.
     * @param checkpointDTO
     * @throws RequiredFieldException
     */
    private void validateRequiredFields(CheckpointDTO checkpointDTO) throws RequiredFieldException {
        if(
            (checkpointDTO.getLength().isBlank() || checkpointDTO.getLength().isEmpty()) ||
            (checkpointDTO.getLatitude().isBlank() || checkpointDTO.getLatitude().isEmpty()) ||
            (checkpointDTO.getName().isBlank() || checkpointDTO.getName().isEmpty())
        ) throw new RequiredFieldException();
    }

    /**
     * Metodo que valida que la tarifa no sea vacia y que sea un numero valido.
     * @param fee
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
     * Metodo que valida que el nombre del punto de control no  este previamente registrado en
     * otro punto de control.
     * @param name
     * @throws NameAlreadyRegisteredException
     */
    private void validateNameIsUnique(String name) throws NameAlreadyRegisteredException {
        if (checkpointRepository.findByName(name) != null)
             throw new NameAlreadyRegisteredException("Nombre de punto de control ya registrado en el sistema.");
    }

    /**
     * Metodo que valida que el nombre del punto de control no este registrado en algun punto de control
     * que no sea el que esta identificado por el id recibido.
     * @param name
     * @param id
     * @throws NameAlreadyRegisteredException
     */
    private void validateUpdatedNameIsUnique(String name, int id) throws NameAlreadyRegisteredException{
        if (checkpointRepository.findByNameAndIdNot(name, id) != null)
             throw new NameAlreadyRegisteredException("Nombre de punto de control ya registrado en el sistema.");
    }

    /**
     * Metodo que valida que no exista ningun paquete por procesar en el punto de control
     * cuyo id se recibe como parametro.
     * @param id
     * @param date
     * @throws NoEmptyCheckpointException
     */
    private void validateCheckpointIsEmpty(int id, Date date) throws NoEmptyCheckpointException {
        if (!packageInformationService.isEmptyCheckpoint(id, date))
            throw new NoEmptyCheckpointException();
    }

}
