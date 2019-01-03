package ar.com.nec.pasantia.blockbuster.controller;

import ar.com.nec.pasantia.blockbuster.entities.PeliculasEntity;
import ar.com.nec.pasantia.blockbuster.exception.PeliculaIdMismatchException;
import ar.com.nec.pasantia.blockbuster.exception.PeliculaNotFoundException;
import ar.com.nec.pasantia.blockbuster.repository.GenerosRepository;
import ar.com.nec.pasantia.blockbuster.repository.PeliculasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/peliculas")
public class PeliculasController {

    @Autowired
    private PeliculasRepository repoPeliculas;
    @Autowired
    private GenerosRepository generosRepository;

    @GetMapping
    public String peliculasPage(Model model) {
        model.addAttribute("listaPeliculas", repoPeliculas.findAll());
        return "peliculas";
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
    public PeliculasEntity create(@RequestBody PeliculasEntity film) {
        return repoPeliculas.save(film);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        repoPeliculas.findById(id)
                .orElseThrow(PeliculaNotFoundException::new);
        repoPeliculas.deleteById(id);
    }

    @PutMapping
    public PeliculasEntity updatePelicula(@RequestBody PeliculasEntity film) {
        //TODO arreglar genero para pelicula
        if(!repoPeliculas.findById(film.getIdpeliculas()).isPresent()){
            throw new PeliculaNotFoundException();
        }else{
            return repoPeliculas.save(film);
        }
    }
}
