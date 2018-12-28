package ar.com.nec.pasantia.blockbuster.controller;

import ar.com.nec.pasantia.blockbuster.entities.PeliculasEntity;
import ar.com.nec.pasantia.blockbuster.exception.PeliculaIdMismatchException;
import ar.com.nec.pasantia.blockbuster.exception.PeliculaNotFoundException;
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

    @GetMapping
    public String peliculasPage(Model model) {
        model.addAttribute("listaPeliculas", repoPeliculas.findAll());
        return "peliculas";
    }

    @GetMapping("editar")
    public String editarPeliculasPage(@RequestParam("idpeliculas") int idpeliculas, Model model) {
        List<PeliculasEntity> peliculas = new ArrayList<PeliculasEntity>();
        Optional<PeliculasEntity> film = repoPeliculas.findById(idpeliculas);

        peliculas.add(film.get());
        model.addAttribute("peliculas", peliculas);
        return "peliculasEditar";
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

    @PutMapping("/{id}")
    public PeliculasEntity updatePelicula(@RequestBody PeliculasEntity film, @PathVariable int id) throws PeliculaIdMismatchException {
        if (film.getIdpeliculas() != id) {
            throw new PeliculaIdMismatchException();
        }
        repoPeliculas.findById(id)
                .orElseThrow(PeliculaNotFoundException::new);
        return repoPeliculas.save(film);
    }
}
