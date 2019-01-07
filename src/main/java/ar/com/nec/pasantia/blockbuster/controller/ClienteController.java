package ar.com.nec.pasantia.blockbuster.controller;


import ar.com.nec.pasantia.blockbuster.entities.ClienteEntity;
import ar.com.nec.pasantia.blockbuster.exception.ClienteIdMismatchException;
import ar.com.nec.pasantia.blockbuster.exception.ClienteNotFoundException;
import ar.com.nec.pasantia.blockbuster.repository.AlquilerRepository;
import ar.com.nec.pasantia.blockbuster.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
@RequestMapping("/usuarios")
public class ClienteController {
    @Autowired
    private ClienteRepository repoClientes;

    @Autowired
    private AlquilerRepository repoAlquileres;

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
    public ResponseEntity<?> create(@RequestBody ClienteEntity cliente) {
        if(repoClientes.existsClienteEntityByDni(cliente.getDni())){
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
        }else{
            repoClientes.save(cliente);
            return new ResponseEntity(HttpStatus.CREATED);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        if(repoAlquileres.existsAlquileresEntityByClienteByIdclienteAndDevueltoIsFalse(repoClientes.findById(id).get())){
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
        }else{
            repoClientes.findById(id)
                    .orElseThrow(ClienteNotFoundException::new);
            repoClientes.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        }

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
