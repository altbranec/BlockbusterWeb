package ar.com.nec.pasantia.blockbuster.controller;

import ar.com.nec.pasantia.blockbuster.entities.AlquileresEntity;
import ar.com.nec.pasantia.blockbuster.exception.RestExceptionHandler;
import ar.com.nec.pasantia.blockbuster.services.AlquileresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@Controller
@RequestMapping("/alquileres")
public class AlquileresController {

    @Autowired
    private AlquileresService alquileresService;

    @Autowired
    private RestExceptionHandler exceptionHandler;

    @GetMapping
    public String alquileresPage(Model model) {
        model.addAttribute("listaAlquileres", alquileresService.encontrarAlquileresSinDevolver());
        return "alquileres";
    }

    @GetMapping("crear")
    public String crearAlquiler(Model model) {
        model.addAttribute("listaPeliculas", alquileresService.encontrarPeliculasConStock());
        model.addAttribute("listaClientes", alquileresService.encontrarClientesActivos());
        return "alquileresCrear";
    }

    @GetMapping("logs")
    public String verLogs(Model model) {
        model.addAttribute("logs", alquileresService.leerLog());
        return "alquileresLogs";
    }

    @GetMapping("editar")
    public String editarAlquileresPage(@RequestParam("idalquiler") int idAlquiler, Model model) {
         try{
            model.addAttribute("alquileres", alquileresService.encontrarAlquilerPorId(idAlquiler));
            return "alquileresEditar";
        }catch (RuntimeException e){
             exceptionHandler.errorPager(e,model);
            return "error";
        }
    }

    @GetMapping("vencidos")
    public String vencidosAlquileresPage(Model model) {
        model.addAttribute("listaAlquileres", alquileresService.encontrarAlquileresVencidos());
        return "alquileresVencidos";
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody List<AlquileresEntity> alquileres)  {
        alquileresService.crearAlquileres(alquileres);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try{
            AlquileresEntity alquiler = alquileresService.encontrarAlquilerPorId(id);
            alquileresService.borrarAlquiler(alquiler);
            return new ResponseEntity(HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateAlquiler(@RequestBody AlquileresEntity alquiler) {
        try{
            alquileresService.editarAlquiler(alquiler);
            return new ResponseEntity(HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.CONFLICT);
        }
    }
}

