package ar.com.nec.pasantia.blockbuster.controller;

import ar.com.nec.pasantia.blockbuster.DTO.PeliculaCrearDTO;
import ar.com.nec.pasantia.blockbuster.exception.RestExceptionHandler;
import ar.com.nec.pasantia.blockbuster.services.GeneroService;
import ar.com.nec.pasantia.blockbuster.services.PeliculasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/peliculas")
public class PeliculasController {

    @Autowired
    private PeliculasService peliculasService;

    @Autowired
    private RestExceptionHandler exceptionHandler;

    @Autowired
    private GeneroService generoService;

    @GetMapping
    public String peliculasPage(Model model) {
        model.addAttribute("listaDePelisConCantidad", peliculasService.listadoPeliculasConCantidadYStock());
        return "peliculas";
    }

    @GetMapping("crear")
    public String crearPelicula(Model model) {
        model.addAttribute("generos", generoService.encontrarTodos());
        return "peliculaCrear";
    }

    @GetMapping("verPelicula")
    public String verPelicula(@RequestParam("idpeliculas") int idPelicula, Model model) {
        try {
            model.addAttribute("pelicula", peliculasService.buscarPelicula(idPelicula));
            return "verPelicula";
        }catch (RuntimeException e){
            exceptionHandler.errorPager(e,model);
            return "error";
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PeliculaCrearDTO film) {
        peliculasService.crearPelicula(film);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("{nombre}")
    public ResponseEntity<?> delete(@PathVariable String nombre) {
        try{
            peliculasService.eliminarPelicula(nombre);
            return new ResponseEntity(HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.CONFLICT);
        }
    }

}
