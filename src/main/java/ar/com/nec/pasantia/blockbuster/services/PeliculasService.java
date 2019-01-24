package ar.com.nec.pasantia.blockbuster.services;

import ar.com.nec.pasantia.blockbuster.DTO.PeliculaCrearDTO;
import ar.com.nec.pasantia.blockbuster.DTO.PeliculasStockCantidadDTO;
import ar.com.nec.pasantia.blockbuster.entities.AlquileresEntity;
import ar.com.nec.pasantia.blockbuster.entities.PeliculasEntity;
import ar.com.nec.pasantia.blockbuster.exception.PeliculaNotFoundException;
import ar.com.nec.pasantia.blockbuster.repository.AlquileresRepository;
import ar.com.nec.pasantia.blockbuster.repository.PeliculasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("peliculasService")
public class PeliculasService extends MyService {

    private PeliculasRepository repoPeliculas;

    @Autowired
    public PeliculasService(PeliculasRepository repoPeliculas,
                          AlquileresRepository repoAlquileres) {
        super(repoAlquileres);
        this.repoPeliculas = repoPeliculas;
    }

    public List<PeliculasStockCantidadDTO> listadoPeliculasConCantidadYStock(){
        List<PeliculasEntity> pelisPorNombre = repoPeliculas.findAllByActivoIsTrueOrderByNombreAsc();
        List<PeliculasStockCantidadDTO> listaDePelisConValores = new ArrayList<>();
        PeliculasStockCantidadDTO peliculaConValores;
        PeliculasEntity peliAnterior = null;

        for (PeliculasEntity pelicula : pelisPorNombre) {
            if (peliAnterior == null || !equalsSinId(peliAnterior, pelicula)) {
                peliculaConValores = new PeliculasStockCantidadDTO();
                peliculaConValores.setPelicula(pelicula);
                peliAnterior = pelicula;
                listaDePelisConValores.add(peliculaConValores);
            }else{
                peliculaConValores = listaDePelisConValores.get(listaDePelisConValores.size() - 1);
            }

            peliculaConValores.incrementarCantidad();
            if (!verSiEstaAlquilada(pelicula))
                peliculaConValores.incrementarStock();

        }
        return listaDePelisConValores;
    }

    public PeliculasEntity buscarPelicula (int idpelicula){
        Optional<PeliculasEntity> peli =repoPeliculas.findById(idpelicula);
        if(peli.isPresent()){
            return peli.get();
        }else {
            throw new PeliculaNotFoundException("No se encuentra la pelicula con id: "+idpelicula);
        }
    }

    public boolean eliminarPelicula (String nombre)throws RuntimeException{
        List<PeliculasEntity> peliculas = repoPeliculas.findByActivoIsTrueAndNombre(nombre);
        for(PeliculasEntity pelicula : peliculas){
            if(!verSiEstaAlquilada(pelicula)){
                pelicula.setActivo(false);
                repoPeliculas.save(pelicula);
                return true;
            }
        }
        throw new PeliculaNotFoundException("Todas las peliculas de nombre: " + nombre + " estan alquiladas");
    }

    public void crearPelicula(PeliculaCrearDTO film){
        PeliculasEntity peli;
        for (int i = 0; i < film.getStock(); i++) {
            peli = new PeliculasEntity();
            peli.setNombre(film.getNombre());
            peli.setGenerosByIdgenero(film.getGenerosByIdgenero());
            repoPeliculas.save(peli);
        }
    }

}
