package ar.com.nec.pasantia.blockbuster.repository;

import ar.com.nec.pasantia.blockbuster.entities.AlquileresEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlquilerRepository extends CrudRepository<AlquileresEntity, Integer> {
    List<AlquileresEntity> findAlquileresEntitiesByIdalquileres(int idAlquiler);
}
