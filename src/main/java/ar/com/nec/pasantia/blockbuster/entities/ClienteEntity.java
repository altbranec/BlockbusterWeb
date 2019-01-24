package ar.com.nec.pasantia.blockbuster.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "cliente", schema = "blockbusterweb", catalog = "")
public class ClienteEntity {
    private int idcliente;
    private String nombre;
    private String dni;
    private Collection<AlquileresEntity> alquileresByIdcliente;
    private boolean activo = true;

    public ClienteEntity(int idcliente, String nombre, String dni, boolean activo) {
        this.idcliente = idcliente;
        this.nombre = nombre;
        this.dni = dni;
        this.activo = activo;
    }

    public ClienteEntity() {
    }

    public ClienteEntity(String nombre, String dni, boolean activo) {
        this.nombre = nombre;
        this.dni = dni;
        this.activo = activo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcliente", nullable = false)
    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
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
    @Column(name = "dni", nullable = false, length = 45)
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Basic
    @Column(name = "activo", nullable = false)
    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteEntity that = (ClienteEntity) o;
        return idcliente == that.idcliente &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(dni, that.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idcliente, nombre, dni);
    }

    @OneToMany(mappedBy = "clienteByIdcliente")
    public Collection<AlquileresEntity> getAlquileresByIdcliente() {
        return alquileresByIdcliente;
    }

    public void setAlquileresByIdcliente(Collection<AlquileresEntity> alquileresByIdcliente) {
        this.alquileresByIdcliente = alquileresByIdcliente;
    }
}
