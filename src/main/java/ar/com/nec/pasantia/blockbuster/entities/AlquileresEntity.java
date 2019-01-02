package ar.com.nec.pasantia.blockbuster.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "alquileres", schema = "blockbusterweb", catalog = "")
public class AlquileresEntity {
    private int idalquileres;
    private Date fechaalquiler;
    private Date fechadevuelto;
    private int diasalquilado;
    private byte devuelto;
    private PeliculasEntity peliculasByIdpelicula;
    private ClienteEntity clienteByIdcliente;

    @Id
    @Column(name = "idalquileres", nullable = false)
    public int getIdalquileres() {
        return idalquileres;
    }

    public void setIdalquileres(int idalquileres) {
        this.idalquileres = idalquileres;
    }

    @Basic
    @Column(name = "fechaalquiler", nullable = false)
    public Date getFechaalquiler() {
        return fechaalquiler;
    }

    public void setFechaalquiler(Date fechaalquiler) {
        this.fechaalquiler = fechaalquiler;
    }

    @Basic
    @Column(name = "fechadevuelto", nullable = true)
    public Date getFechadevuelto() {
        return fechadevuelto;
    }

    public void setFechadevuelto(Date fechadevuelto) {
        this.fechadevuelto = fechadevuelto;
    }

    @Basic
    @Column(name = "diasalquilado", nullable = false)
    public int getDiasalquilado() {
        return diasalquilado;
    }

    public void setDiasalquilado(int diasalquilado) {
        this.diasalquilado = diasalquilado;
    }

    @Basic
    @Column(name = "devuelto", nullable = false)
    public byte getDevuelto() {
        return devuelto;
    }

    public void setDevuelto(byte devuelto) {
        this.devuelto = devuelto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlquileresEntity that = (AlquileresEntity) o;
        return idalquileres == that.idalquileres &&
                diasalquilado == that.diasalquilado &&
                devuelto == that.devuelto &&
                Objects.equals(fechaalquiler, that.fechaalquiler) &&
                Objects.equals(fechadevuelto, that.fechadevuelto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idalquileres, fechaalquiler, fechadevuelto, diasalquilado, devuelto);
    }

    @ManyToOne
    @JoinColumn(name = "idpelicula", referencedColumnName = "idpeliculas", nullable = false)
    public PeliculasEntity getPeliculasByIdpelicula() {
        return peliculasByIdpelicula;
    }

    public void setPeliculasByIdpelicula(PeliculasEntity peliculasByIdpelicula) {
        this.peliculasByIdpelicula = peliculasByIdpelicula;
    }

    @ManyToOne
    @JoinColumn(name = "idcliente", referencedColumnName = "idcliente", nullable = false)
    public ClienteEntity getClienteByIdcliente() {
        return clienteByIdcliente;
    }

    public void setClienteByIdcliente(ClienteEntity clienteByIdcliente) {
        this.clienteByIdcliente = clienteByIdcliente;
    }
}
