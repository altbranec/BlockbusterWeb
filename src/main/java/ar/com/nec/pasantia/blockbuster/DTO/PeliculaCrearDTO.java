package ar.com.nec.pasantia.blockbuster.DTO;

import ar.com.nec.pasantia.blockbuster.entities.GenerosEntity;


public class PeliculaCrearDTO {

    private String nombre;
    private GenerosEntity generosByIdgenero;
    private int stock;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public GenerosEntity getGenerosByIdgenero() {
        return generosByIdgenero;
    }

    public void setGenerosByIdgenero(GenerosEntity generosByIdgenero) {
        this.generosByIdgenero = generosByIdgenero;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
