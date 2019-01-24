package ar.com.nec.pasantia.blockbuster.DTO;

import ar.com.nec.pasantia.blockbuster.entities.PeliculasEntity;

public class PeliculasStockCantidadDTO {

    private PeliculasEntity pelicula;
    private int cantidad = 0;
    private int stock = 0;

   public void incrementarCantidad(){
       cantidad++;
   }

    public void incrementarStock(){
        stock++;
    }

    public PeliculasEntity getPelicula() {
        return pelicula;
    }

    public void setPelicula(PeliculasEntity pelicula) {
        this.pelicula = pelicula;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getStock() {
        return stock;
    }

}
