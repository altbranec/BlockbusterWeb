package ar.com.nec.pasantia.blockbuster.controller;


import ar.com.nec.pasantia.blockbuster.entities.GenerosEntity;
import ar.com.nec.pasantia.blockbuster.exception.GenerosFoundException;
import ar.com.nec.pasantia.blockbuster.exception.GenerosNotFoundException;
import ar.com.nec.pasantia.blockbuster.repository.GenerosRepository;
import ar.com.nec.pasantia.blockbuster.services.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/generos")
public class GenerosController {

    @Autowired
    private GeneroService generosService;

    @GetMapping
    public String GenerosPage(Model model) {
        model.addAttribute("listaGeneros", generosService.encontrarTodos());
        return "generos";
    }

    @GetMapping("crear")
    public String crearGenero(Model model) {
        return "generosCrear";
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody GenerosEntity genero) {
        try {
            generosService.crearGenero(genero);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (GenerosFoundException e) {
            return new ResponseEntity(HttpStatus.FOUND);
        }
    }
}