package ar.com.nec.pasantia.blockbuster.controller;


import ar.com.nec.pasantia.blockbuster.entities.ClienteEntity;
import ar.com.nec.pasantia.blockbuster.exception.ClienteIdMismatchException;
import ar.com.nec.pasantia.blockbuster.exception.ClienteNotFoundException;
import ar.com.nec.pasantia.blockbuster.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
@RequestMapping("/usuarios")
public class ClienteController {
    @Autowired
    private ClienteRepository repoClientes;

    @GetMapping
    public String usuariosPage(Model model) {
        model.addAttribute("listaUsuarios", repoClientes.findAll());
        return "usuarios";
    }
    @GetMapping("crear")
    public String crearUsuario(Model model) {
        return "usuarioCrear";
    }

    @GetMapping("editar")
    public String editarUsuariosPage(@RequestParam("idcliente") int idcliente, Model model ) {
        model.addAttribute("usuario", repoClientes.findById(idcliente).get());
        return "usuarioEditar";
    }

    @GetMapping("/{id}")
    public ClienteEntity findOne(@PathVariable int id) {
        return repoClientes.findById(id)
                .orElseThrow(ClienteNotFoundException::new); 
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteEntity create(@RequestBody ClienteEntity cliente) {
        return repoClientes.save(cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int id) {
        repoClientes.findById(id)
                .orElseThrow(ClienteNotFoundException::new);
        repoClientes.deleteById(id);
    }

    @PutMapping
    public ClienteEntity updateCliente(@RequestBody ClienteEntity cliente) {
        if(!repoClientes.findById(cliente.getIdcliente()).isPresent()){
            throw new ClienteNotFoundException();
        }else{
            return repoClientes.save(cliente);
        }

    }
}
