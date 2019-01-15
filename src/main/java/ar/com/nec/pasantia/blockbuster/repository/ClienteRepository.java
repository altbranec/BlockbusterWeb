package ar.com.nec.pasantia.blockbuster.repository;

import ar.com.nec.pasantia.blockbuster.entities.ClienteEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClienteRepository extends CrudRepository<ClienteEntity, Integer> {
    List<ClienteEntity> findAllByActivoIsTrue();
    ClienteEntity findClienteEntityByDni(String dni);
}
