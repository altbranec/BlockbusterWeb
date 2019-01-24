package ar.com.nec.pasantia.blockbuster.services;

import ar.com.nec.pasantia.blockbuster.entities.GenerosEntity;
import ar.com.nec.pasantia.blockbuster.exception.GenerosFoundException;
import ar.com.nec.pasantia.blockbuster.repository.GenerosRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GeneroServiceTest {

    @Mock
    GenerosRepository repoGenero;

    @InjectMocks
    GeneroService genServ;

    GenerosEntity genero1;

    @Before
    public void setUp() throws Exception {
        genero1 = new GenerosEntity();
        repoGenero = mock(GenerosRepository.class);
        genServ = new GeneroService(repoGenero);
    }

    @Test(expected = GenerosFoundException.class)
    public void crearGeneroQueYaExiste() throws GenerosFoundException {


        genero1.setNombre("CualquierGeneroQueExista");
        when(repoGenero.existsByNombreIgnoreCase(genero1.getNombre())).thenReturn(true);
        //Obligo a que la peli que busco se haya encontrado
        genServ.crearGenero(genero1);


        /* when(repoGenero.existsByNombreIgnoreCase("ScFiccion")).thenReturn(true);
        assertTrue(repoGenero.existsByNombreIgnoreCase(genero1.getNombre()));

        when(repoGenero.existsByNombreIgnoreCase("ScFiccion")).thenThrow(GenerosFoundException.class);
        assertTrue(repoGenero.existsByNombreIgnoreCase(genero1.getNombre()));*/
    }

    @Test()
    public void crearGeneroQueNoExiste() throws GenerosFoundException {
        genero1.setNombre("CualquierGeneroQueNoExista");
        when(repoGenero.existsByNombreIgnoreCase(genero1.getNombre())).thenReturn(false);
        assertTrue(genServ.crearGenero(genero1));
    }




    }