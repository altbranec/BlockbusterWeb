package ar.com.nec.pasantia.blockbuster.entities;

import ar.com.nec.pasantia.blockbuster.repository.AlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "peliculas", schema = "blockbusterweb", catalog = "")
public class PeliculasEntity {
    private int idpeliculas;
    private String nombre;
    private Collection<AlquileresEntity> alquileresByIdpeliculas;
    private GenerosEntity generosByIdgenero;
    private boolean activo = true;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Basic
    @Column(name = "activo", nullable = false)
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeliculasEntity that = (PeliculasEntity) o;
        return idpeliculas == that.idpeliculas &&
                Objects.equals(nombre, that.nombre);
    }

    public List<String> toList() {
       List<String> aRetornar = new ArrayList<>();
       aRetornar.add(nombre);
       aRetornar.add(generosByIdgenero.getNombre());
       return aRetornar;
    }

    public boolean verSiEstaAlquilada(AlquilerRepository repoAlquileres) {
        List<AlquileresEntity> alquileresSinDevolver = repoAlquileres.findAlquileresEntityByDevueltoIsFalse();
        List<PeliculasEntity> pelisSinDevolver = new ArrayList<>();
        for (AlquileresEntity alqui : alquileresSinDevolver) {
            pelisSinDevolver.add(alqui.getPeliculasByIdpelicula());
        }

        return pelisSinDevolver.contains(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idpeliculas, nombre);
    }

    @OneToMany(mappedBy = "peliculasByIdpelicula")
    public Collection<AlquileresEntity> getAlquileresByIdpeliculas() {
        return alquileresByIdpeliculas;
    }

    public void setAlquileresByIdpeliculas(Collection<AlquileresEntity> alquileresByIdpeliculas) {
        this.alquileresByIdpeliculas = alquileresByIdpeliculas;
    }

    @ManyToOne
    @JoinColumn(name = "idgenero", referencedColumnName = "idgeneros", nullable = false)
    public GenerosEntity getGenerosByIdgenero() {
        return generosByIdgenero;
    }

    public void setGenerosByIdgenero(GenerosEntity generosByIdgenero) {
        this.generosByIdgenero = generosByIdgenero;
    }
}
