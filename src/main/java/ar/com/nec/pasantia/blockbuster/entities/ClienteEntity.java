package ar.com.nec.pasantia.blockbuster.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cliente", schema = "blockbusterweb", catalog = "")
public class ClienteEntity {
    private int idcliente;
    private String nombre;
    private String dni;

    @Id
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
}
