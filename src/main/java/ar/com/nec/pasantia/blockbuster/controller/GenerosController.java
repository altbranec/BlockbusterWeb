package ar.com.nec.pasantia.blockbuster.controller;


import ar.com.nec.pasantia.blockbuster.entities.GenerosEntity;
import ar.com.nec.pasantia.blockbuster.exception.GenerosNotFoundException;
import ar.com.nec.pasantia.blockbuster.repository.GenerosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/generos")
public class GenerosController {

    @Autowired
    private GenerosRepository repoGeneros;

    @GetMapping
    public String GenerosPage(Model model) {
        model.addAttribute("listaGeneros", repoGeneros.findAll());
        return "generos";
    }
    @GetMapping("crear")
    public String crearGenero(Model model) {
        return "generosCrear";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenerosEntity create(@RequestBody GenerosEntity genero) {
        return repoGeneros.save(genero);
    }

}
