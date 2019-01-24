package ar.com.nec.pasantia.blockbuster.services;

import ar.com.nec.pasantia.blockbuster.entities.AlquileresEntity;
import ar.com.nec.pasantia.blockbuster.entities.ClienteEntity;
import ar.com.nec.pasantia.blockbuster.entities.GenerosEntity;
import ar.com.nec.pasantia.blockbuster.entities.PeliculasEntity;
import ar.com.nec.pasantia.blockbuster.exception.AlquilerFoundException;
import ar.com.nec.pasantia.blockbuster.exception.ClienteFoundException;
import ar.com.nec.pasantia.blockbuster.repository.AlquileresRepository;
import ar.com.nec.pasantia.blockbuster.repository.ClienteRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;


public class ClienteServiceTest {

    private ClienteEntity cli1, cli2, cli3;
    private PeliculasEntity peli1, peli2, peli3;
    private AlquileresEntity alqui1, alqui2, alqui3;
    private List<ClienteEntity> clientes;
    private List<PeliculasEntity> peliculas;
    private List<AlquileresEntity> alquileres;
    private ArgumentCaptor<ClienteEntity> captor;

    @Mock
    private ClienteRepository repoClientes;

    @Mock
    private AlquileresRepository repoAlquileres;

    @InjectMocks
    private ClienteService clienteService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        cli1 = new ClienteEntity(1,"emilio","12121212",false);
        cli2 = new ClienteEntity(2,"raquel","123456789",true);
        cli3 = new ClienteEntity(3,"July3p","32165497",true);
        clientes = Arrays.asList(cli1, cli2, cli3);

        peli1 = new PeliculasEntity(1, "batman", true, new GenerosEntity("accion"));
        peli2 = new PeliculasEntity(2, "mrBean", true, new GenerosEntity("comedia"));
        peli3 = new PeliculasEntity(3, "juli", true, new GenerosEntity("DEATHMETALMAÑANERO"));
        peliculas = Arrays.asList(peli1, peli2, peli3);

        alqui1 = new AlquileresEntity(1,new Date(1-1-1990),new Date(2-1-1990),1,false,peli1,cli3);
        alqui2 = new AlquileresEntity(2,new Date(1-1-1990),new Date(3-1-1990),2,false,peli2,cli3);
        alqui3 = new AlquileresEntity(3,new Date(1-1-1990),new Date(4-1-1990),3,false,peli3,cli3);
        alquileres = Arrays.asList(alqui1,alqui2,alqui3);

        captor = ArgumentCaptor.forClass(ClienteEntity.class);

        Mockito.when(repoClientes.save(captor.capture())).thenReturn(cli2);
        Mockito.when(repoClientes.findClienteEntityByDni(cli1.getDni())).thenReturn(Optional.ofNullable(cli1));
        Mockito.when(repoClientes.findClienteEntityByDni(cli3.getDni())).thenReturn(Optional.ofNullable(cli3));
        Mockito.when(repoClientes.existsClienteEntityByDni(cli1.getDni())).thenReturn(true);
        Mockito.when(repoClientes.existsClienteEntityByDni(cli2.getDni())).thenReturn(false);
        Mockito.when(repoClientes.existsClienteEntityByDni(cli3.getDni())).thenReturn(true);
        Mockito.when(repoClientes.findAllByActivoIsTrue()).thenReturn(clientes);
        Mockito.when(repoClientes.findById(cli1.getIdcliente())).thenReturn(Optional.ofNullable(cli1));
        Mockito.when(repoClientes.findById(cli2.getIdcliente())).thenReturn(Optional.ofNullable(cli2));
        Mockito.when(repoClientes.findById(cli3.getIdcliente())).thenReturn(Optional.ofNullable(cli3));
        Mockito.when(repoAlquileres.existsAlquileresEntityByClienteByIdclienteAndDevueltoIsFalse(any(ClienteEntity.class))).thenReturn(true);
        Mockito.when(repoAlquileres.findAlquileresEntityByClienteByIdclienteIsAndDevueltoIsFalse(any(ClienteEntity.class))).thenReturn(alquileres);
    }

    @Test
    public void encontrarTodosLosActivos() {
        assertEquals(clienteService.encontrarTodosLosActivos(),clientes);
    }

    @Test
    public void encontrarPorId() {
        assertEquals(clienteService.encontrarPorId(cli2.getIdcliente()), cli2);
    }

    @Test
    public void encontrarClientePorDni() {
      assertEquals(cli1,clienteService.encontrarClientePorDni(cli1.getDni()));
    }

    @Test(expected = ClienteFoundException.class)
    public void crearClienteYaExistenteActivo() {
        clienteService.crearCliente(cli3);
    }

    @Test
    public void crearCliente() {
        clienteService.crearCliente(cli2);
        assertEquals(captor.getValue(), cli2);
    }

    @Test
    public void crearClienteInactivo() {
        clienteService.crearCliente(cli1);
        assertEquals(captor.getValue().getActivo(),true);
    }

    @Test(expected = AlquilerFoundException.class)
    public void borrarClienteConAlquileres() {
        clienteService.borrarCliente(cli3);
    }

    @Test
    public void encontrarPeliculasPorIdcliente() {
        List<PeliculasEntity> lista = clienteService.encontrarPeliculasPorIdcliente(cli2.getIdcliente());
        assertEquals(lista,peliculas);
    }
}