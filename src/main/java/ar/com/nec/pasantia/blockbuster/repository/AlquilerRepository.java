package ar.com.nec.pasantia.blockbuster.repository;

import ar.com.nec.pasantia.blockbuster.entities.AlquileresEntity;
import ar.com.nec.pasantia.blockbuster.entities.ClienteEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AlquilerRepository extends CrudRepository<AlquileresEntity, Integer> {
    List<AlquileresEntity> findAlquileresEntityByDevueltoIsFalseAndFechadevueltoIsBefore(Date hoy);
    List<AlquileresEntity> findAlquileresEntityByDevueltoIsFalse();
    List<AlquileresEntity> findAlquileresEntityByClienteByIdclienteIsAndDevueltoIsFalse(Optional<ClienteEntity> client);
    boolean existsAlquileresEntityByClienteByIdclienteAndDevueltoIsFalse(ClienteEntity clienteEntity);

}
