package ar.com.nec.pasantia.blockbuster.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "peliculas", schema = "blockbusterweb", catalog = "")
public class PeliculasEntity {
    private int idpeliculas;
    private String nombre;

    @Id
    @Column(name = "idpeliculas", nullable = false)
    public int getIdpeliculas() {
        return idpeliculas;
    }

    public void setIdpeliculas(int idpeliculas) {
        this.idpeliculas = idpeliculas;
    }

    @Basic
    @Column(name = "nombre", nullable = false, length = 45)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeliculasEntity that = (PeliculasEntity) o;
        return idpeliculas == that.idpeliculas &&
                Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idpeliculas, nombre);
    }
}
