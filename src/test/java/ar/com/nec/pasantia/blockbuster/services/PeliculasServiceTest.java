package ar.com.nec.pasantia.blockbuster.services;

import ar.com.nec.pasantia.blockbuster.DTO.PeliculaCrearDTO;
import ar.com.nec.pasantia.blockbuster.DTO.PeliculasStockCantidadDTO;
import ar.com.nec.pasantia.blockbuster.entities.GenerosEntity;
import ar.com.nec.pasantia.blockbuster.entities.PeliculasEntity;
import ar.com.nec.pasantia.blockbuster.repository.AlquileresRepository;
import ar.com.nec.pasantia.blockbuster.repository.PeliculasRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;

public class PeliculasServiceTest {

    private GenerosEntity gene;
    private PeliculasEntity peli;
    private PeliculaCrearDTO film;
    private PeliculasStockCantidadDTO pdto;

    private List<PeliculasEntity> listEnt;
    private List<PeliculasStockCantidadDTO> listDTO;

    private ArgumentCaptor<PeliculasEntity> capturador;

    @Mock
    private PeliculasRepository repoPeliculas;

    @Mock
    private AlquileresRepository repoAlquileres;

    @InjectMocks
    private PeliculasService peliculasServiceTest;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        gene = new GenerosEntity(1,"generoTest");

        peli = new PeliculasEntity(19, "nombreTest", true, gene);
        peli.setAlquileresByIdpeliculas(null);

        film = new PeliculaCrearDTO();
        film.setGenerosByIdgenero(peli.getGenerosByIdgenero());
        film.setNombre(peli.getNombre());
        film.setStock(1);

        pdto = new PeliculasStockCantidadDTO();
        pdto.setPelicula(peli);
        pdto.incrementarStock();
        pdto.incrementarCantidad();

        listEnt = Arrays.asList(peli);
        listDTO = Arrays.asList(pdto);

        capturador = ArgumentCaptor.forClass(PeliculasEntity.class);

        Mockito.when(repoPeliculas.save(capturador.capture()))
                .thenReturn(null);
        Mockito.when(repoPeliculas.findById(anyInt()))
                .thenReturn(java.util.Optional.ofNullable(peli));
        Mockito.when(repoPeliculas.findByActivoIsTrueAndNombre(anyString()))
                .thenReturn(listEnt);
        Mockito.when(repoPeliculas.findAllByActivoIsTrueOrderByNombreAsc())
                .thenReturn(listEnt);
    }

    @Test
    public void listadoPeliculasConCantidadYStock() {
        // Run the test
        final List<PeliculasStockCantidadDTO> result = peliculasServiceTest.listadoPeliculasConCantidadYStock();

        // Verify the results
        assertEquals(result.get(0).getPelicula(), listDTO.get(0).getPelicula());
    }

    @Test
    public void buscarPelicula() {
        // Set up
        final int id = 19;

        // Run the test
        final PeliculasEntity result = peliculasServiceTest.buscarPelicula(id);

        // Verify the results
        assertEquals(id, result.getIdpeliculas());
    }

    @Test
    public void eliminarPelicula() {
        // Set up
        final String nombre = "nombreTest";

        //Run the test
        final boolean result = peliculasServiceTest.eliminarPelicula(nombre);

        // Verify the results
        assertTrue(result);
    }

    @Test
    public void crearPelicula() {
        // Setup
        final String nombre = "nombreTest";

        //Run the test
        peliculasServiceTest.crearPelicula(film);

        // Verify the results
        assertEquals(capturador.getValue().getNombre(), nombre);

    }

}