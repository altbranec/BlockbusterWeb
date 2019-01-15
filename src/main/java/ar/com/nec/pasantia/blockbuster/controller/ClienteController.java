package ar.com.nec.pasantia.blockbuster.controller;


import ar.com.nec.pasantia.blockbuster.entities.AlquileresEntity;
import ar.com.nec.pasantia.blockbuster.entities.ClienteEntity;
import ar.com.nec.pasantia.blockbuster.entities.PeliculasEntity;
import ar.com.nec.pasantia.blockbuster.exception.ClienteNotFoundException;
import ar.com.nec.pasantia.blockbuster.repository.AlquilerRepository;
import ar.com.nec.pasantia.blockbuster.repository.ClienteRepository;
import ar.com.nec.pasantia.blockbuster.repository.PeliculasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private AlquilerRepository repoAlquileres;

    @GetMapping
    public String usuariosPage(Model model) {

        model.addAttribute("listaUsuarios", repoClientes.findAllByActivoIsTrue());
        return "usuarios";
    }

    @GetMapping("crear")
    public String crearUsuario(Model model) {
        return "usuarioCrear";
    }

    @GetMapping("editar")
    public String editarUsuariosPage(@RequestParam("idcliente") int idcliente, Model model) {
        model.addAttribute("usuario", repoClientes.findById(idcliente).get());
        return "usuarioEditar";
    }

    @GetMapping("verpeliculas")
    public String usuariosPeliculasPage(@RequestParam("idcliente") int idcliente, Model model) {
        Optional<ClienteEntity> cli = repoClientes.findById(idcliente);
        List<AlquileresEntity> alquileresCliente = repoAlquileres.findAlquileresEntityByClienteByIdclienteIsAndDevueltoIsFalse(cli);
        List<PeliculasEntity> pelisMostrar = new ArrayList<PeliculasEntity>();
        for (AlquileresEntity alqui : alquileresCliente) {
            pelisMostrar.add(alqui.getPeliculasByIdpelicula());
        }
        model.addAttribute("peliculasByUsuarioList", pelisMostrar);
        return "usuarioPeliculas";
    }

    @GetMapping("/{id}")
    public ClienteEntity findOne(@PathVariable int id) {
        return repoClientes.findById(id)
                .orElseThrow(ClienteNotFoundException::new);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ClienteEntity cliente) {
        Optional<ClienteEntity> cliActual = Optional.ofNullable(repoClientes.findClienteEntityByDni(cliente.getDni()));
        if (cliActual.isPresent()) {
            if (cliActual.get().getActivo()) {
                return new ResponseEntity<String>(HttpStatus.CONFLICT);
            } else {
                cliActual.get().setActivo(true);
                repoClientes.save(cliActual.get());
            }
        } else {
            repoClientes.save(cliente);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        if (repoAlquileres.existsAlquileresEntityByClienteByIdclienteAndDevueltoIsFalse(repoClientes.findById(id).get())) {
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
        } else {
            ClienteEntity cliActual = repoClientes.findById(id)
                    .orElseThrow(ClienteNotFoundException::new);
            cliActual.setActivo(false);
            repoClientes.save(cliActual);
            return new ResponseEntity(HttpStatus.OK);
        }

    }

    @PutMapping
    public ClienteEntity updateCliente(@RequestBody ClienteEntity cliente) {
        if (!repoClientes.findById(cliente.getIdcliente()).isPresent()) {
            throw new ClienteNotFoundException();
        } else {
            return repoClientes.save(cliente);
        }

    }
}
