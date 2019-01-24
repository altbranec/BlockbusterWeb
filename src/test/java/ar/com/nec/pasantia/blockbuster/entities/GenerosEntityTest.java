package ar.com.nec.pasantia.blockbuster.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class GenerosEntityTest {

    GenerosEntity genero1;
    GenerosEntity genero2;
    PeliculasEntity pelicula1;
    PeliculasEntity pelicula2;
    PeliculasEntity pelicula3;


    @Before
    public void setUp() throws Exception {
        genero1 = new GenerosEntity();
        genero2 = new GenerosEntity();
        pelicula1 = new PeliculasEntity();
        pelicula2 = new PeliculasEntity();
        pelicula3 = new PeliculasEntity();
        genero1.setIdgeneros(300);
        genero1.setIdgeneros(400);

        pelicula1.setGenerosByIdgenero(genero1);
        pelicula2.setGenerosByIdgenero(genero1);
        pelicula3.setGenerosByIdgenero(genero2);
    }

    @Test
    public void verQueSeCreaElGenero() {
        assertNotNull(genero1);
        assertTrue(genero2 instanceof GenerosEntity);
    }

    @Test
    public void getAndSetIdgeneros() {
        genero1.setIdgeneros(300);
        Integer expected = 300;
        Integer generoIdRecibido = genero1.getIdgeneros();
        assertEquals(expected, generoIdRecibido);
    }

    @Test
    public void getAndSetNombre() {
        genero1.setNombre("GeneroA");
        String expected = "GeneroA";
        String generoNombreRecibido = genero1.getNombre();
        assertEquals(expected, generoNombreRecibido);
    }
}