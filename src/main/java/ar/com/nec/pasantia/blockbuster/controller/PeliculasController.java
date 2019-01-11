package ar.com.nec.pasantia.blockbuster.controller;

import ar.com.nec.pasantia.blockbuster.DTO.PeliculaCrearDTO;
import ar.com.nec.pasantia.blockbuster.entities.AlquileresEntity;
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
        List<PeliculasEntity> pelisPorNombre = repoPeliculas.findAllByOrderByNombreAsc();
        PeliculasEntity peliAnterior = null;
        List<List<String>> listaDePelisConCantidad = new ArrayList<>();

        for (PeliculasEntity pelicula : pelisPorNombre) {
            if (peliAnterior == null) {
                peliAnterior = crearListaNueva(listaDePelisConCantidad, pelicula);
            } else {
                if (peliAnterior.getNombre().equals(pelicula.getNombre())) {
                    List<String> peli = listaDePelisConCantidad.get(listaDePelisConCantidad.size() - 1);
                    int nuevaCantidad = Integer.parseInt(peli.get(2)) + 1;
                    listaDePelisConCantidad.get(listaDePelisConCantidad.size() - 1).set(2, String.valueOf(nuevaCantidad));
                    if (verSiEstaAlquilada(pelicula)) {
                        int nuevaStock = Integer.parseInt(peli.get(3)) + 1;
                        listaDePelisConCantidad.get(listaDePelisConCantidad.size() - 1).set(3, String.valueOf(nuevaStock));
                    }


                } else {
                    peliAnterior = crearListaNueva(listaDePelisConCantidad, pelicula);
                }
            }

        }

        model.addAttribute("listaDePelisConCantidad", listaDePelisConCantidad);
        return "peliculas";
    }

    private PeliculasEntity crearListaNueva(List<List<String>> listaDePelisConCantidad, PeliculasEntity pelicula) {
        PeliculasEntity peliAnterior;
        List<String> peliculaCompleta;
        peliAnterior = pelicula;
        peliculaCompleta = pelicula.toList();
        peliculaCompleta.add("1");
        if (verSiEstaAlquilada(peliAnterior)) {
            peliculaCompleta.add("1");
        } else peliculaCompleta.add("0");
        listaDePelisConCantidad.add(peliculaCompleta);
        return peliAnterior;
    }

    public boolean verSiEstaAlquilada(PeliculasEntity pelicula) {
        List<AlquileresEntity> alquileresSinDevolver = repoAlquileres.findAlquileresEntityByDevueltoIsFalse();
        List<PeliculasEntity> pelisSinDevolver = new ArrayList<>();
        for (AlquileresEntity alqui : alquileresSinDevolver) {
            pelisSinDevolver.add(alqui.getPeliculasByIdpelicula());
        }
        if (!pelisSinDevolver.contains(pelicula)) return true;
        else return false;
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


    @PutMapping
    public PeliculasEntity updatePelicula(@RequestBody PeliculasEntity film) {
        //TODO arreglar genero para pelicula
        if (!repoPeliculas.findById(film.getIdpeliculas()).isPresent()) {
            throw new PeliculaNotFoundException();
        } else {
            return repoPeliculas.save(film);
        }
    }
}
