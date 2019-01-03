package ar.com.nec.pasantia.blockbuster.controller;

import ar.com.nec.pasantia.blockbuster.entities.AlquileresEntity;
import ar.com.nec.pasantia.blockbuster.exception.AlquilerIdMismatchException;
import ar.com.nec.pasantia.blockbuster.exception.AlquilerNotFoundException;
import ar.com.nec.pasantia.blockbuster.repository.AlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/alquileres")
public class AlquileresController {
    @Autowired
    private AlquilerRepository repoAlquileres;

    @GetMapping
    public String alquileresPage(Model model) {
        List<AlquileresEntity> listAlqui = (List<AlquileresEntity>) repoAlquileres.findAll();
        List<AlquileresEntity> alquiMostrar = new ArrayList<AlquileresEntity>();
        for (AlquileresEntity alquiler : listAlqui) {
            if (alquiler.getDevuelto()) {
                alquiMostrar.add(alquiler);
            }
        }
        model.addAttribute("listaAlquileres", alquiMostrar);
        return "alquileres";
    }

    @GetMapping("editar")
    public String editarAlquileresPage(@RequestParam("idalquiler") int idAlquiler, Model model) {
        model.addAttribute("alquileres", repoAlquileres.findById(idAlquiler).get());
        return "alquileresEditar";
    }

    @GetMapping("/idalquileres/{idalquileres}")
    public List findByid(@PathVariable int idalquileres) {
        return repoAlquileres.findAlquileresEntitiesByIdalquileres(idalquileres);
    }

    @GetMapping("/{idalquileres}")
    public AlquileresEntity findOne(@PathVariable int id) {
        return repoAlquileres.findById(id)
                .orElseThrow(AlquilerNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlquileresEntity create(@RequestBody AlquileresEntity alquiler) {
        return repoAlquileres.save(alquiler);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        AlquileresEntity alquiler = repoAlquileres.findById(id)
                .orElseThrow(AlquilerNotFoundException::new);
        alquiler.setDevuelto(false);
        repoAlquileres.save(alquiler);
    }

    @PutMapping("/{id}")
    public AlquileresEntity updateAlquiler(@RequestBody AlquileresEntity alquiler, @PathVariable int id) {
        if (alquiler.getIdalquileres() != id) {
            throw new AlquilerIdMismatchException();
        }
        repoAlquileres.findById(id)
                .orElseThrow(AlquilerNotFoundException::new);
        return repoAlquileres.save(alquiler);
    }
}

