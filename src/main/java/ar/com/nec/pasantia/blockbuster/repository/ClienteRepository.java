package ar.com.nec.pasantia.blockbuster.repository;

import ar.com.nec.pasantia.blockbuster.entities.ClienteEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends CrudRepository<ClienteEntity, Integer> {
    List<ClienteEntity> findAllByActivoIsTrue();
    Optional<ClienteEntity> findClienteEntityByDni(String dni);
    boolean existsClienteEntityByDni(String dni);
}
