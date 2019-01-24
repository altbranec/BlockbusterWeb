package ar.com.nec.pasantia.blockbuster.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClienteEntityTest {

    ClienteEntity cliente1;
    ClienteEntity cliente2;


    @Before
    public void setUp() throws Exception {
        cliente1 = new ClienteEntity();
        cliente2 = new ClienteEntity();
    }

    @Test
    public void verQueSeCreoElCliente() {
        assertNotNull(cliente1);
        assertNotNull(cliente2);
    }

    @Test
    public void getAndSetIdcliente() {
        cliente1.setIdcliente(80);
        Integer idClienteSeteado=cliente1.getIdcliente();
        Integer esperado=80;
        assertEquals(esperado,idClienteSeteado);
        }

    @Test
    public void getAndSetNombre() {
        cliente1.setNombre("Nombre");
        String nombreClienteSeteado=cliente1.getNombre();
        String esperado="Nombre";
        assertEquals(esperado,nombreClienteSeteado);
    }

    @Test
    public void getAndSetDni() {
        cliente1.setDni("12345678");
        String dniClienteSeteado=cliente1.getDni();
        String esperado="12345678";
        assertEquals(esperado,dniClienteSeteado);

    }

    @Test
    public void getAndSetActivo() {
        cliente1.setActivo(false);
        Boolean estadoClienteSeteado=cliente1.getActivo();
        Boolean esperado=false;
        assertEquals(esperado,estadoClienteSeteado);
        cliente1.setActivo(true);
        estadoClienteSeteado=cliente1.getActivo();
        esperado=true;
        assertEquals(esperado,estadoClienteSeteado);
    }

}