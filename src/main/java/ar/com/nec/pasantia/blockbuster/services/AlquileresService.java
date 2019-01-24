package ar.com.nec.pasantia.blockbuster.services;

import ar.com.nec.pasantia.blockbuster.entities.AlquileresEntity;
import ar.com.nec.pasantia.blockbuster.entities.ClienteEntity;
import ar.com.nec.pasantia.blockbuster.entities.PeliculasEntity;
import ar.com.nec.pasantia.blockbuster.exception.AlquilerNotFoundException;
import ar.com.nec.pasantia.blockbuster.repository.AlquileresRepository;
import ar.com.nec.pasantia.blockbuster.repository.ClienteRepository;
import ar.com.nec.pasantia.blockbuster.repository.PeliculasRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

@Service("alquileresService")
public class AlquileresService extends MyService {

    private PeliculasRepository repoPeliculas;
    private ClienteRepository repoClientes;

    static Logger log = Logger.getLogger(AlquileresService.class.getName());

    @Autowired
    public AlquileresService(AlquileresRepository repoAlquileres,
                             PeliculasRepository repoPeliculas,
                             ClienteRepository repoClientes) {
        super(repoAlquileres);
        this.repoPeliculas = repoPeliculas;
        this.repoClientes = repoClientes;
    }

    public List<AlquileresEntity> encontrarAlquileresSinDevolver() {
        return repoAlquileres.findAlquileresEntityByDevueltoIsFalse();
    }

    public AlquileresEntity encontrarAlquilerPorId(int idAlquiler) {
        return repoAlquileres.findById(idAlquiler)
                .orElseThrow(AlquilerNotFoundException::new);
    }

    public List<AlquileresEntity> encontrarAlquileresVencidos() {
        return repoAlquileres.findAlquileresEntityByDevueltoIsFalseAndFechadevueltoIsBefore(
                Calendar.getInstance().getTime());
    }

    public List<PeliculasEntity> encontrarPeliculasConStock() {
        List<PeliculasEntity> listaDePelisConStock = new ArrayList<>();
        PeliculasEntity peliAnterior = null;

        for (PeliculasEntity pelicula : encontrarPeliculasActivas()) {
            if ((peliAnterior == null || !equalsSinId(peliAnterior, pelicula)) &&
                    !verSiEstaAlquilada(pelicula)) {
                listaDePelisConStock.add(pelicula);
                peliAnterior = pelicula;
            }
        }
        return listaDePelisConStock;
    }

    public List<PeliculasEntity> encontrarPeliculasActivas() {
        return repoPeliculas.findAllByActivoIsTrueOrderByNombreAsc();
    }

    public List<ClienteEntity> encontrarClientesActivos() {
        return repoClientes.findAllByActivoIsTrue();
    }


    public void crearAlquileres(List<AlquileresEntity> alquileres) {
        for (AlquileresEntity alquiler : alquileres) {
            repoAlquileres.save(alquiler);
            logCrearAlquiler(alquiler);
        }
    }

    public void borrarAlquiler(AlquileresEntity alquiler) {
        repoAlquileres.findById(alquiler.getIdalquileres())
                .orElseThrow(AlquilerNotFoundException::new);
        alquiler.setDevuelto(true);
        repoAlquileres.save(alquiler);
        logBorrarAlquiler(alquiler);
    }

    public AlquileresEntity editarAlquiler(AlquileresEntity alquiler) {
        repoAlquileres.findById(alquiler.getIdalquileres())
                .orElseThrow(AlquilerNotFoundException::new);
        logEditarAlquiler(alquiler);
        return repoAlquileres.save(alquiler);
    }

    private void logCrearAlquiler(AlquileresEntity alquiler) {
        log.info("Alquilado - " + " Usuario: " +
                repoClientes.findById(alquiler.getClienteByIdcliente().getIdcliente()).get().getNombre() +
                " | Pelicula: " +
                repoPeliculas.findById(alquiler.getPeliculasByIdpelicula().getIdpeliculas()).get().getNombre() +
                " | Fecha de devolucion: " +
                repoAlquileres.findById(alquiler.getIdalquileres()).get().getFechadevuelto());
    }

    private void logBorrarAlquiler(AlquileresEntity alquiler) {
        log.info("Devuelto - " + " Usuario: " +
                repoClientes.findById(alquiler.getClienteByIdcliente().getIdcliente()).get().getNombre() +
                " | Pelicula: " +
                repoPeliculas.findById(alquiler.getPeliculasByIdpelicula().getIdpeliculas()).get().getNombre());
    }

    private void logEditarAlquiler(AlquileresEntity alquiler) {
        log.info("Editado - " + " Usuario: " +
                repoClientes.findById(alquiler.getClienteByIdcliente().getIdcliente()).get().getNombre() +
                " | Pelicula: " +
                repoPeliculas.findById(alquiler.getPeliculasByIdpelicula().getIdpeliculas()).get().getNombre() +
                " | Nueva fecha de devolucion: " +
                repoAlquileres.findById(alquiler.getIdalquileres()).get().getFechadevuelto());
    }


    public ArrayList<String> leerLog() {
        File fl = new File("MyLog/log.txt");
        ArrayList<String> logs = new ArrayList<>();
        Scanner input;
        try {
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
