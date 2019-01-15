package ar.com.nec.pasantia.blockbuster.controller;

import ar.com.nec.pasantia.blockbuster.DTO.PeliculaCrearDTO;
import ar.com.nec.pasantia.blockbuster.entities.PeliculasEntity;
import ar.com.nec.pasantia.blockbuster.exception.PeliculaNotFoundException;
import ar.com.nec.pasantia.blockbuster.repository.AlquilerRepository;
import ar.com.nec.pasantia.blockbuster.repository.GenerosRepository;
import ar.com.nec.pasantia.blockbuster.repository.PeliculasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/peliculas")
public class PeliculasController {

    @Autowired
    private PeliculasRepository repoPeliculas;
    @Autowired
    private GenerosRepository generosRepository;
    @Autowired
    private AlquilerRepository repoAlquileres;


    @GetMapping
    public String peliculasPage(Model model) {
        List<PeliculasEntity> pelisPorNombre = repoPeliculas.findAllByActivoIsTrueOrderByNombreAsc();
        List<List<String>> listaDePelisConCantidad = new ArrayList<>();
        List<String> peli;
        int nuevaCantidad;
        int nuevaStock;

        PeliculasEntity peliAnterior = null;

        for (PeliculasEntity pelicula : pelisPorNombre) {
            if (peliAnterior == null || !peliAnterior.getNombre().equals(pelicula.getNombre()))
                peliAnterior = crearListaNueva(listaDePelisConCantidad, pelicula);
            else{
                peli = listaDePelisConCantidad.get(listaDePelisConCantidad.size() - 1);
                nuevaCantidad = Integer.parseInt(peli.get(2)) + 1;
                listaDePelisConCantidad.get(listaDePelisConCantidad.size() - 1).set(2, String.valueOf(nuevaCantidad));
                if (!pelicula.verSiEstaAlquilada(repoAlquileres)) {
                    nuevaStock = Integer.parseInt(peli.get(3)) + 1;
                    listaDePelisConCantidad.get(listaDePelisConCantidad.size() - 1).set(3, String.valueOf(nuevaStock));
                }
            }
        }

        model.addAttribute("listaDePelisConCantidad", listaDePelisConCantidad);
        return "peliculas";
    }

    private PeliculasEntity crearListaNueva(List<List<String>> listaDePelisConCantidad, PeliculasEntity pelicula) {
        List<String> peliculaCompleta;
        peliculaCompleta = pelicula.toList();
        peliculaCompleta.add("1");
        peliculaCompleta.add(pelicula.verSiEstaAlquilada(repoAlquileres)?"0":"1");
        listaDePelisConCantidad.add(peliculaCompleta);
        return pelicula;
    }
    
    @GetMapping("crear")
    public String crearPelicula(Model model) {
        model.addAttribute("generos", generosRepository.findAll());
        return "peliculaCrear";
    }

    @GetMapping("editar")
    public String editarPeliculasPage(@RequestParam("idpeliculas") int idpeliculas, Model model) {
        model.addAttribute("pelicula", repoPeliculas.findById(idpeliculas).get());
        model.addAttribute("generos", generosRepository.findAll());
        return "peliculaEditar";
    }

    @GetMapping("/idpeliculas/{bookTitle}")
    public List findByIdPeliculas(@PathVariable int idpeliculas) {
        return repoPeliculas.findPeliculasEntityByIdpeliculas(idpeliculas);
    }

    @GetMapping("/{id}")
    public PeliculasEntity findOne(@PathVariable int id) {
        return repoPeliculas.findById(id)
                .orElseThrow(PeliculaNotFoundException::new); //crear la exception
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody PeliculaCrearDTO film) {
        PeliculasEntity peli = new PeliculasEntity();
        peli.setNombre(film.getNombre());
        peli.setGenerosByIdgenero(film.getGenerosByIdgenero());
        int n = film.getStock();
        for (int i = 0; i < n; i++) {
            repoPeliculas.save(peli);
            peli = new PeliculasEntity();
            peli.setNombre(film.getNombre());
            peli.setGenerosByIdgenero(film.getGenerosByIdgenero());
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("{nombre}")
    public ResponseEntity<?> delete(@PathVariable String nombre) {
        List<PeliculasEntity> peliculas = repoPeliculas.findByActivoIsTrueAndNombre(nombre);
        for(PeliculasEntity pelicula : peliculas){
            if(!pelicula.verSiEstaAlquilada(repoAlquileres)){
                pelicula.setActivo(false);
                repoPeliculas.save(pelicula);
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        return new ResponseEntity<String>(HttpStatus.CONFLICT);
    }

    @PutMapping
    public PeliculasEntity updatePelicula(@RequestBody PeliculasEntity film) {
        if (!repoPeliculas.findById(film.getIdpeliculas()).isPresent()) {
            throw new PeliculaNotFoundException();
        } else {
            return repoPeliculas.save(film);
        }
    }
}
