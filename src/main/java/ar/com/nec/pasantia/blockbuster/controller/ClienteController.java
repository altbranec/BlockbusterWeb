package ar.com.nec.pasantia.blockbuster.controller;

import ar.com.nec.pasantia.blockbuster.entities.ClienteEntity;
import ar.com.nec.pasantia.blockbuster.exception.RestExceptionHandler;
import ar.com.nec.pasantia.blockbuster.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/usuarios")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private RestExceptionHandler exceptionHandler;

    @GetMapping
    public String usuariosPage(Model model) {
        model.addAttribute("listaUsuarios", clienteService.encontrarTodosLosActivos());
        return "usuarios";
    }

    @GetMapping("crear")
    public String crearUsuario(Model model) {
        return "usuarioCrear";
    }

    @GetMapping("editar")
    public String editarUsuariosPage(@RequestParam("idcliente") int idcliente, Model model) {
        try{
            model.addAttribute("usuario", clienteService.encontrarPorId(idcliente));
            return "usuarioEditar";
        }catch (RuntimeException e){
            exceptionHandler.errorPager(e,model);
            return "error";
        }
    }

    @GetMapping("verpeliculas")
    public String usuariosPeliculasPage(@RequestParam("idcliente") int idcliente, Model model) {
        try{
            model.addAttribute("peliculasByUsuarioList", clienteService.encontrarPeliculasPorIdcliente(idcliente));
            return "usuarioPeliculas";
        }catch (RuntimeException e){
            exceptionHandler.errorPager(e,model);
            return "error";
        }
    }

    @GetMapping("verCliente")
    public String findOne(@RequestParam("idcliente") int idcliente, Model model) {
        try{
            model.addAttribute("cliente", clienteService.encontrarPorId(idcliente));
            return "verCliente";
        }catch (RuntimeException e){
            exceptionHandler.errorPager(e,model);
            return "error";
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ClienteEntity cliente) {
        try {
            clienteService.crearCliente(cliente);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            ClienteEntity cliActual = clienteService.encontrarPorId(id);
            clienteService.borrarCliente(cliActual);
            return new ResponseEntity(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateCliente(@RequestBody ClienteEntity cliente){
        try{
            clienteService.editarCliente(cliente);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.CONFLICT);
        }

    }

}
