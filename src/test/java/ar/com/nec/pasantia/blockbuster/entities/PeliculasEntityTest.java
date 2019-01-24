package ar.com.nec.pasantia.blockbuster.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class PeliculasEntityTest {

    PeliculasEntity peli1;
    PeliculasEntity peli2;
    PeliculasEntity peliCompleta;
    GenerosEntity genero;
    AlquileresEntity alquiler;

    @Before
    public void setUp() throws Exception {
        peli1 = new PeliculasEntity();
        peli2 = new PeliculasEntity();
        genero = new GenerosEntity();
        peliCompleta = new PeliculasEntity();

        genero.setIdgeneros(55);
        genero.setNombre("generoNombre");

        peliCompleta.setIdpeliculas(99);
        peliCompleta.setActivo(true);
        peliCompleta.setNombre("nombrePeli");
        peliCompleta.setGenerosByIdgenero(genero);

        alquiler = new AlquileresEntity();


    }

    @Test
    public void getAndSetIdpeliculas() {
        peli1.setIdpeliculas(99);
        Integer idPeliActual = peli1.getIdpeliculas();
        Integer idPeliEsperado = 99;
        assertEquals(idPeliEsperado, idPeliActual);
    }

    @Test
    public void getAndSetNombre() {
        peli1.setNombre("Nombre1");
        String nombrePeliActual = peli1.getNombre();
        String nombrePeliEsperado = ("Nombre1");
        assertEquals(nombrePeliEsperado, nombrePeliActual);
    }

    @Test
    public void getAndSetActivo() {
        peli2.setActivo(false);
        Boolean estadoActual = peli2.getActivo();
        Boolean estadoEsperado = false;
        assertEquals(estadoEsperado, estadoActual);

    }

    @Test
    public void toList() {


        List<String> lista = new ArrayList<>();
        lista = peliCompleta.toList();
        assertEquals("nombrePeli", lista.get(0));
        assertEquals("generoNombre", lista.get(1));


    }

  /*  @Test
    public void verSiEstaAlquilada() {

        alquiler.setPeliculasByIdpelicula(peliCompleta);

        List<AlquileresEntity> lista = new ArrayList<>();

        //List.of(alquiler);
        lista.add(alquiler);
        AlquileresRepository repoAlquileres = mock(AlquileresRepository.class);

        when(repoAlquileres.findAlquileresEntityByDevueltoIsFalse()).thenReturn(lista);

        assertTrue(peliCompleta.verSiEstaAlquilada(repoAlquileres));


    }*/

}