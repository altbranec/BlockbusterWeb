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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @GetMapping("editar")
    public String editarUsuariosPage(@RequestParam("idcliente") int idcliente, Model model ) {
        List<ClienteEntity> usuarios = new ArrayList<ClienteEntity>();
        Optional<ClienteEntity> cliente = repoClientes.findById(idcliente);

        usuarios.add(cliente.get());
        model.addAttribute("usuarios", usuarios);
        return "usuarioEditar";
    }

    @GetMapping("/dni/{bookTitle}")
    public List findByDni(@PathVariable int dni) {
        return repoClientes.findClienteEntityByDni(dni);
    }

    @GetMapping("/{id}")
    public ClienteEntity findOne(@PathVariable int id) {
        return repoClientes.findById(id)
                .orElseThrow(ClienteNotFoundException::new); //crear la exception
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteEntity create(@RequestBody ClienteEntity cliente) {
        return repoClientes.save(cliente);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        repoClientes.findById(id)
                .orElseThrow(ClienteNotFoundException::new);
        repoClientes.deleteById(id);
    }

    @PutMapping("/{id}")
    public ClienteEntity updateCliente(@RequestBody ClienteEntity cliente, @PathVariable int id) {
        if (cliente.getIdcliente() != id) {
            throw new ClienteIdMismatchException();
        }
        repoClientes.findById(id)
                .orElseThrow(ClienteNotFoundException::new);
        return repoClientes.save(cliente);
    }
}
