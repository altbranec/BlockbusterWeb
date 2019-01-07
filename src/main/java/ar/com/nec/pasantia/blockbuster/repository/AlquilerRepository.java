package ar.com.nec.pasantia.blockbuster.repository;

import ar.com.nec.pasantia.blockbuster.entities.AlquileresEntity;
import ar.com.nec.pasantia.blockbuster.entities.ClienteEntity;
import ar.com.nec.pasantia.blockbuster.entities.PeliculasEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlquilerRepository extends CrudRepository<AlquileresEntity, Integer> {
    List<AlquileresEntity> findAlquileresEntityByDevueltoIsFalse();
   boolean existsAlquileresEntityByClienteByIdclienteAndDevueltoIsFalse(ClienteEntity clienteEntity);
}
