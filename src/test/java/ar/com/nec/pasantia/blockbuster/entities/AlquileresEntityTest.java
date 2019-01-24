package ar.com.nec.pasantia.blockbuster.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;


public class AlquileresEntityTest {

    GenerosEntity genero;
    PeliculasEntity pelicula;
    ClienteEntity cliente;
    AlquileresEntity alquiler;

    @Before
    public void setUp() throws Exception {
        genero = new GenerosEntity();
        pelicula = new PeliculasEntity();
        cliente = new ClienteEntity();
        alquiler = new AlquileresEntity();
    }

    @Test
    public void getAndSetIdalquileres() {
    alquiler.setIdalquileres(99);
    Integer alquiActual = alquiler.getIdalquileres();
    Integer alquiEsperado = 99;
    assertEquals(alquiEsperado, alquiActual);
    }


    @Test
    public void getAndSetFechaalquiler() {
        alquiler.setFechaalquiler(new java.sql.Date(2019,1,21));
        Date fechaActual = alquiler.getFechaalquiler();
        Date fechaEsperado = new Date(2019,1,21);
        assertEquals(fechaEsperado,fechaActual);
    }

    @Test
    public void getAndSetFechadevuelto() {
        alquiler.setFechaalquiler(new java.sql.Date(2019,1,26));
        Date fechaActual = alquiler.getFechaalquiler();
        Date fechaEsperado = new Date(2019,1,26);
        assertEquals(fechaEsperado,fechaActual);
    }

    @Test
    public void getAndSetDiasalquilado() {
        alquiler.setDiasalquilado(7);
        Integer diasActual = alquiler.getDiasalquilado();
        Integer diasEsperado = 7;
        assertEquals(diasEsperado, diasActual);
    }

    @Test
    public void getAndSetDevuelto() {
        alquiler.setDevuelto(true);
        boolean devueltoActual = alquiler.getDevuelto();
        boolean devueltoEsperado = true;
        assertEquals(devueltoEsperado, devueltoActual);
    }
}