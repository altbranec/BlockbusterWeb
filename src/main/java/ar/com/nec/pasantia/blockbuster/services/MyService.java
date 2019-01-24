package ar.com.nec.pasantia.blockbuster.services;

import ar.com.nec.pasantia.blockbuster.entities.AlquileresEntity;
import ar.com.nec.pasantia.blockbuster.entities.PeliculasEntity;
import ar.com.nec.pasantia.blockbuster.repository.AlquileresRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MyService {

    protected AlquileresRepository repoAlquileres;

    @Autowired
    public MyService(AlquileresRepository repoAlquileres) {
        this.repoAlquileres = repoAlquileres;
    }

    public boolean equalsSinId(PeliculasEntity peliAnterior, PeliculasEntity pelicula){
        return peliAnterior.getNombre().equals(pelicula.getNombre()) &&
                peliAnterior.getGenerosByIdgenero().equals(pelicula.getGenerosByIdgenero());
    }

    public boolean verSiEstaAlquilada(PeliculasEntity peliABuscar) {
        List<AlquileresEntity> alquileresSinDevolver = repoAlquileres.findAlquileresEntityByDevueltoIsFalse();
        List<PeliculasEntity> pelisSinDevolver = new ArrayList<>();
        for (AlquileresEntity alqui : alquileresSinDevolver) {
            pelisSinDevolver.add(alqui.getPeliculasByIdpelicula());
        }

        return pelisSinDevolver.contains(peliABuscar);
    }
}
