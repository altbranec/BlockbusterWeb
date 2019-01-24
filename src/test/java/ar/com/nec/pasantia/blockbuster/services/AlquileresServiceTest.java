package ar.com.nec.pasantia.blockbuster.services;

import ar.com.nec.pasantia.blockbuster.entities.AlquileresEntity;
import ar.com.nec.pasantia.blockbuster.entities.ClienteEntity;
import ar.com.nec.pasantia.blockbuster.entities.GenerosEntity;
import ar.com.nec.pasantia.blockbuster.entities.PeliculasEntity;
import ar.com.nec.pasantia.blockbuster.exception.AlquilerNotFoundException;
import ar.com.nec.pasantia.blockbuster.repository.AlquileresRepository;
import ar.com.nec.pasantia.blockbuster.repository.PeliculasRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class AlquileresServiceTest {

    @Mock
    private AlquileresRepository repoAlquileres;

    @InjectMocks
    private AlquileresService alquileresService;

    @Mock
    private PeliculasRepository repoPeliculas;

    @Mock
    private PeliculasService peliAlquileresService;


    GenerosEntity genero1;
    GenerosEntity genero2;
    PeliculasEntity peli1;
    PeliculasEntity peli2;
    PeliculasEntity peli3;
    ClienteEntity cliente1;
    ClienteEntity cliente2;
    AlquileresEntity alquiler1;
    Optional<AlquileresEntity> alqui;

    List<PeliculasEntity> listaPelisTotal;
    List<AlquileresEntity> listaDeAlquileresConLasPelisAlquiladas;
    List<PeliculasEntity> listaPelisConStock;

    private ArgumentCaptor<AlquileresEntity> capturador;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        genero1 = new GenerosEntity("generoGenerico");
        genero2 = new GenerosEntity("generoGenerico2");
        peli1 = new PeliculasEntity(100, "peli1", true, genero1);
        peli2 = new PeliculasEntity(110, "peli2", true, genero2);
        peli3 = new PeliculasEntity(120, "peli3", true, genero2);
        cliente1 = new ClienteEntity(200, "nombre", "12345678", true);
        cliente2 = new ClienteEntity(210, "nombre2", "87654321", true);
        alquiler1 = new AlquileresEntity(300, new Date(2019, 01, 01), new Date(2019, 01, 15), 14, false, peli1, cliente1);

        //idalquileres Date fechaalquiler Date fechadevuelto int diasalquilado boolean devuelto,
        //PeliculasEntity peliculasByIdpelicula, ClienteEntity clienteByIdcliente
        listaPelisTotal = new ArrayList<>();
        listaDeAlquileresConLasPelisAlquiladas = new ArrayList<>();
        listaPelisConStock = new ArrayList<>();
        listaPelisTotal.add(peli1);
        listaPelisTotal.add(peli2);
        listaPelisTotal.add(peli3);

        listaDeAlquileresConLasPelisAlquiladas.add(alquiler1);

        listaPelisConStock.add(peli2);
        listaPelisConStock.add(peli3);

        alqui = Optional.empty();
        Mockito.when(repoAlquileres.findById(any(Integer.class))).thenReturn(alqui);
        Mockito.when(repoPeliculas.findAllByActivoIsTrueOrderByNombreAsc()).thenReturn(listaPelisTotal);
        Mockito.when(peliAlquileresService.verSiEstaAlquilada(peli1)).thenReturn(true);
        Mockito.when(peliAlquileresService.verSiEstaAlquilada(peli2)).thenReturn(false);
        Mockito.when(peliAlquileresService.verSiEstaAlquilada(peli3)).thenReturn(false);
        Mockito.when(repoAlquileres.findAlquileresEntityByDevueltoIsFalse()).thenReturn(listaDeAlquileresConLasPelisAlquiladas);
    }

    @Test(expected = AlquilerNotFoundException.class)
    public void encontrarAlquilerPorId() throws AlquilerNotFoundException {
        alquileresService.encontrarAlquilerPorId(300);
    }

    @Test
    public void encontrarPeliculasConStock() {
        assertEquals(alquileresService.encontrarPeliculasConStock().get(0).getNombre(), listaPelisConStock.get(0).getNombre());
        assertEquals(alquileresService.encontrarPeliculasConStock().get(1).getNombre(), listaPelisConStock.get(1).getNombre());
    }

    @Test
    public void crearAlquileres() {
    }

    @Test
    public void borrarAlquiler() {
    }

    @Test
    public void editarAlquiler() {
    }

    @Test
    public void leerLog() {
    }
}