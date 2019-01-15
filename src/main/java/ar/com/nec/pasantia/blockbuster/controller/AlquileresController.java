package ar.com.nec.pasantia.blockbuster.controller;

import ar.com.nec.pasantia.blockbuster.entities.AlquileresEntity;
import ar.com.nec.pasantia.blockbuster.entities.PeliculasEntity;
import ar.com.nec.pasantia.blockbuster.exception.AlquilerNotFoundException;
import ar.com.nec.pasantia.blockbuster.repository.AlquilerRepository;
import ar.com.nec.pasantia.blockbuster.repository.ClienteRepository;
import ar.com.nec.pasantia.blockbuster.repository.PeliculasRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

import static java.time.format.DateTimeFormatter.ofPattern;


@Controller
@RequestMapping("/alquileres")
public class AlquileresController {
    @Autowired
    private AlquilerRepository repoAlquileres;
    @Autowired
    private PeliculasRepository repoPeliculas;
    @Autowired
    private ClienteRepository repoClientes;


    static Logger log = Logger.getLogger(AlquileresController.class.getName());

    @GetMapping
    public String alquileresPage(Model model) {
        List<AlquileresEntity> alquiMostrar = repoAlquileres.findAlquileresEntityByDevueltoIsFalse();
        model.addAttribute("listaAlquileres", alquiMostrar);
        return "alquileres";
    }

    @GetMapping("crear")
    public String crearAlquiler(Model model) {
        List<PeliculasEntity> pelisPorNombre = repoPeliculas.findAllByActivoIsTrueOrderByNombreAsc();
        List<PeliculasEntity> listaDePelisConStock = new ArrayList<>();
        PeliculasEntity peliAnterior = null;

        for (PeliculasEntity pelicula : pelisPorNombre) {
            if((peliAnterior == null || !peliAnterior.getNombre().equals(pelicula.getNombre())) &&
                    !pelicula.verSiEstaAlquilada(repoAlquileres)) {
                listaDePelisConStock.add(pelicula);
                peliAnterior = pelicula;
            }
        }

        model.addAttribute("listaPeliculas", listaDePelisConStock);
        model.addAttribute("listaClientes", repoClientes.findAllByActivoIsTrue());

        return "alquileresCrear";
    }

    @GetMapping("logs")
    public String verLogs(Model model) {
        ArrayList<String> logs = leerLog();
        model.addAttribute("logs", logs);
        return "alquileresLogs";
    }

    @GetMapping("editar")
    public String editarAlquileresPage(@RequestParam("idalquiler") int idAlquiler, Model model) {
        model.addAttribute("alquileres", repoAlquileres.findById(idAlquiler).get());
        return "alquileresEditar";
    }

    @GetMapping("vencidos")
    public String vencidosAlquileresPage(Model model) {

        List<AlquileresEntity> alquiVencidos = repoAlquileres.findAlquileresEntityByDevueltoIsFalseAndFechadevueltoIsBefore(
                 Calendar.getInstance().getTime());
        model.addAttribute("listaAlquileres", alquiVencidos);
        return "alquileresVencidos";
    }

    @GetMapping("/{idalquileres}")
    public AlquileresEntity findOne(@PathVariable int id) {
        return repoAlquileres.findById(id)
                .orElseThrow(AlquilerNotFoundException::new);
    }

    @GetMapping("verCliente")
    public String verCliente(@RequestParam("idcliente") int idCliente, Model model) {
        model.addAttribute("cliente", repoClientes.findById(idCliente).get());
        return "verCliente";
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody List<AlquileresEntity> alquileres) throws IOException, SQLException {

        for (AlquileresEntity alquiler : alquileres) {
            repoAlquileres.save(alquiler);
            log.info("Alquilado - " + " Usuario: " +
                    repoClientes.findById(alquiler.getClienteByIdcliente().getIdcliente()).get().getNombre() +
                    " | Pelicula: " +
                    repoPeliculas.findById(alquiler.getPeliculasByIdpelicula().getIdpeliculas()).get().getNombre() +
                    " | Fecha de devolucion: " +
                    repoAlquileres.findById(alquiler.getIdalquileres()).get().getFechadevuelto());

        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int id) {
        AlquileresEntity alquiler = repoAlquileres.findById(id)
                .orElseThrow(AlquilerNotFoundException::new);
        alquiler.setDevuelto(true);
        log.info("Devuelto - " + " Usuario: " +
                repoClientes.findById(alquiler.getClienteByIdcliente().getIdcliente()).get().getNombre() +
                " | Pelicula: " +
                repoPeliculas.findById(alquiler.getPeliculasByIdpelicula().getIdpeliculas()).get().getNombre());
        repoAlquileres.save(alquiler);
    }

    @PutMapping
    public AlquileresEntity updateAlquiler(@RequestBody AlquileresEntity alquiler) {
        repoAlquileres.findById(alquiler.getIdalquileres())
                .orElseThrow(AlquilerNotFoundException::new);
        log.info("Editado - " + " Usuario: " +
                repoClientes.findById(alquiler.getClienteByIdcliente().getIdcliente()).get().getNombre() +
                " | Pelicula: " +
                repoPeliculas.findById(alquiler.getPeliculasByIdpelicula().getIdpeliculas()).get().getNombre() +
                " | Nueva fecha de devolucion: " +
                repoAlquileres.findById(alquiler.getIdalquileres()).get().getFechadevuelto());
        return repoAlquileres.save(alquiler);
    }

    private ArrayList<String> leerLog() {
        File fl = new File("MyLog/log.txt");
        ArrayList<String> logs = new ArrayList<>();
        Scanner input;
        try {
            String cadena = null;

            if (!fl.exists())
                fl.createNewFile();

            input = new Scanner(fl);
            while (input.hasNextLine()) {
                logs.add(input.nextLine());
            }
            input.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return logs;
    }
}

