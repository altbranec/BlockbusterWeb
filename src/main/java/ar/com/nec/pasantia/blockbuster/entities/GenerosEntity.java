package ar.com.nec.pasantia.blockbuster.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "generos", schema = "blockbusterweb", catalog = "")
public class GenerosEntity {
    private int idgeneros;
    private String nombre;
    private Collection<PeliculasEntity> peliculasByIdgeneros;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgeneros", nullable = false)
    public int getIdgeneros() {
        return idgeneros;
    }

    public void setIdgeneros(int idgeneros) {
        this.idgeneros = idgeneros;
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
        GenerosEntity that = (GenerosEntity) o;
        return idgeneros == that.idgeneros &&
                Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idgeneros, nombre);
    }

    @OneToMany(mappedBy = "generosByIdgenero")
    public Collection<PeliculasEntity> getPeliculasByIdgeneros() {
        return peliculasByIdgeneros;
    }

    public void setPeliculasByIdgeneros(Collection<PeliculasEntity> peliculasByIdgeneros) {
        this.peliculasByIdgeneros = peliculasByIdgeneros;
    }
}
