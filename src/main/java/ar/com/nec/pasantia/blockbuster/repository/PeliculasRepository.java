package ar.com.nec.pasantia.blockbuster.repository;

import ar.com.nec.pasantia.blockbuster.entities.AlquileresEntity;
import ar.com.nec.pasantia.blockbuster.entities.PeliculasEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PeliculasRepository extends CrudRepository<PeliculasEntity, Integer> {

    List<PeliculasEntity> findPeliculasEntityByIdpeliculas(int Id);
    List<PeliculasEntity> findAllByOrderByNombreAsc();
    List<PeliculasEntity> findPeliculasEntityOrOrderByNombre(List<AlquileresEntity> pelisSinAlquilar);

}
