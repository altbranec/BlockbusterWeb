package ar.com.nec.pasantia.blockbuster.controller;

import ar.com.nec.pasantia.blockbuster.entities.AlquileresEntity;
import ar.com.nec.pasantia.blockbuster.exception.AlquilerNotFoundException;
import ar.com.nec.pasantia.blockbuster.repository.AlquilerRepository;
import ar.com.nec.pasantia.blockbuster.repository.ClienteRepository;
import ar.com.nec.pasantia.blockbuster.repository.PeliculasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/alquileres")
public class AlquileresController {
    @Autowired
    private AlquilerRepository repoAlquileres;
    @Autowired
    private PeliculasRepository repoPeliculas;
    @Autowired
    private ClienteRepository repoClientes;
    private Date releaseDate;

    @GetMapping
    public String alquileresPage(Model model) {
        List<AlquileresEntity> alquiMostrar = repoAlquileres.findAlquileresEntityByDevueltoIsFalse();
        model.addAttribute("listaAlquileres", alquiMostrar);
        return "alquileres";
    }

    @GetMapping("crear")
    public String crearAlquiler(Model model) {
        model.addAttribute("listaPeliculas", repoPeliculas.findAll());
        model.addAttribute("listaClientes", repoClientes.findAll());
        return "alquileresCrear";
    }

    @GetMapping("logs")
    public String verLogs(Model model) {
        model.addAttribute("logs", ""); //agregar los logs
        return "alquileresLogs";
    }

    @GetMapping("editar")
    public String editarAlquileresPage(@RequestParam("idalquiler") int idAlquiler, Model model) {
        model.addAttribute("alquileres", repoAlquileres.findById(idAlquiler).get());
        return "alquileresEditar";
    }

    @GetMapping("/{idalquileres}")
    public AlquileresEntity findOne(@PathVariable int id) {
        return repoAlquileres.findById(id)
                .orElseThrow(AlquilerNotFoundException::new);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody List<AlquileresEntity> alquileres) {
        for(AlquileresEntity alquiler : alquileres){
            repoAlquileres.save(alquiler);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int id) {
        AlquileresEntity alquiler = repoAlquileres.findById(id)
                .orElseThrow(AlquilerNotFoundException::new);
        alquiler.setDevuelto(true);
        repoAlquileres.save(alquiler);
    }


    @PutMapping
    public AlquileresEntity updateAlquiler(@RequestBody AlquileresEntity alquiler) {
        repoAlquileres.findById(alquiler.getIdalquileres())
                .orElseThrow(AlquilerNotFoundException::new);
        return repoAlquileres.save(alquiler);
    }
}

