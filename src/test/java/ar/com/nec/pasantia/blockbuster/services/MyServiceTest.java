package ar.com.nec.pasantia.blockbuster.services;

import ar.com.nec.pasantia.blockbuster.entities.AlquileresEntity;
import ar.com.nec.pasantia.blockbuster.entities.ClienteEntity;
import ar.com.nec.pasantia.blockbuster.entities.GenerosEntity;
import ar.com.nec.pasantia.blockbuster.entities.PeliculasEntity;
import ar.com.nec.pasantia.blockbuster.repository.AlquileresRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MyServiceTest {

    private ClienteEntity cli1;
    private PeliculasEntity peli1;
    private PeliculasEntity peli2;
    private PeliculasEntity peli3;
    private AlquileresEntity alqui1;

    private List<PeliculasEntity> peliculas;
    private List<AlquileresEntity> alquileres;

    @Mock
    private AlquileresRepository repoAlquileres;

    @InjectMocks
    private MyService myService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        cli1 = new ClienteEntity(1,"emilio","12121212",false);

        peli1 = new PeliculasEntity(1, "mrBean", true, new GenerosEntity("comedia"));
        peli2 = new PeliculasEntity(2, "mrBean", true, new GenerosEntity("comedia"));
        peli3 = new PeliculasEntity(3, "juli", true, new GenerosEntity("DEATHMETALMAÑANERO"));
        peliculas = Arrays.asList(peli2, peli3);

        alqui1 = new AlquileresEntity(1,new Date(1-1-1990),new Date(2-1-1990),1,false,peli2,cli1);
        alquileres = Arrays.asList(alqui1);

        Mockito.when(repoAlquileres.findAlquileresEntityByDevueltoIsFalse()).thenReturn(alquileres);
    }

    @Test
    public void verSiEstaAlquilada(){
        assertTrue(myService.verSiEstaAlquilada(peli2));
        assertFalse(myService.verSiEstaAlquilada(peli3));
    }
    @Test
    public void equalsSinId() {
        assertTrue(myService.equalsSinId(peli1, peli2));
    }
}
