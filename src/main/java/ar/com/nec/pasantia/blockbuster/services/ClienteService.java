package ar.com.nec.pasantia.blockbuster.services;


import ar.com.nec.pasantia.blockbuster.entities.AlquileresEntity;
import ar.com.nec.pasantia.blockbuster.entities.ClienteEntity;
import ar.com.nec.pasantia.blockbuster.entities.PeliculasEntity;
import ar.com.nec.pasantia.blockbuster.exception.AlquilerFoundException;
import ar.com.nec.pasantia.blockbuster.exception.ClienteFoundException;
import ar.com.nec.pasantia.blockbuster.exception.ClienteNotFoundException;
import ar.com.nec.pasantia.blockbuster.repository.AlquileresRepository;
import ar.com.nec.pasantia.blockbuster.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("clienteService")
public class ClienteService {

    private ClienteRepository repoClientes;
    private AlquileresRepository repoAlquileres;

    @Autowired
    public ClienteService(ClienteRepository repoClientes,
                          AlquileresRepository repoAlquileres) {
        this.repoClientes = repoClientes;
        this.repoAlquileres = repoAlquileres;
    }

    public List<ClienteEntity> encontrarTodosLosActivos() {
        return repoClientes.findAllByActivoIsTrue();
    }

    public ClienteEntity encontrarPorId(int id) throws ClienteNotFoundException {
        Optional<ClienteEntity> cli = repoClientes.findById(id);
        if (cli.isPresent()) return cli.get();
        else throw new ClienteNotFoundException("cliente no encontrado id: " + id);
    }

    public ClienteEntity encontrarClientePorDni(String dni) throws ClienteNotFoundException {
        Optional<ClienteEntity> cli = repoClientes.findClienteEntityByDni(dni);
        if (cli.isPresent()) return cli.get();
        else throw new ClienteNotFoundException("cliente no encontrado dni: " + dni);
    }

    public void crearCliente(ClienteEntity cliente) throws RuntimeException {

        if (!repoClientes.existsClienteEntityByDni(cliente.getDni())) {
            repoClientes.save(cliente);
        } else {
            ClienteEntity cliActual = encontrarClientePorDni(cliente.getDni());
            if (cliActual.getActivo()) {
                throw new ClienteFoundException("El cliente con dni: " + cliActual.getDni() + " ya existe");
            } else {
                cliActual.setActivo(true);
                repoClientes.save(cliActual);
            }
        }
    }

    public void editarCliente(ClienteEntity cliente) {
        ClienteEntity cliActual = encontrarPorId(cliente.getIdcliente());
        cliActual.setNombre(cliente.getNombre());
        cliActual.setDni(cliente.getDni());
        repoClientes.save(cliActual);
    }

    public void borrarCliente(ClienteEntity cliente) {
        if (existeElAlquilerSinDevolverPorIdcliente(cliente.getIdcliente()))
            throw new AlquilerFoundException("El cliente con dni: " + cliente.getDni() + " tiene alquileres sin devolver");

        cliente.setActivo(false);
        repoClientes.save(cliente);
    }

    public List<AlquileresEntity> encontrarAlquileresPorClienteSinDevolver(ClienteEntity cli) {
        return repoAlquileres.findAlquileresEntityByClienteByIdclienteIsAndDevueltoIsFalse(cli);
    }

    public boolean existeElAlquilerSinDevolverPorIdcliente(int id) {
        return repoAlquileres.existsAlquileresEntityByClienteByIdclienteAndDevueltoIsFalse(this.encontrarPorId(id));
    }

    public List<PeliculasEntity> encontrarPeliculasPorIdcliente(int idcliente) {
        ClienteEntity cli = encontrarPorId(idcliente);
        List<AlquileresEntity> alquileresCliente = encontrarAlquileresPorClienteSinDevolver(cli);
        List<PeliculasEntity> pelisMostrar = new ArrayList<PeliculasEntity>();
        for (AlquileresEntity alqui : alquileresCliente) {
            pelisMostrar.add(alqui.getPeliculasByIdpelicula());
        }
        return pelisMostrar;
    }
}
