package ar.com.nec.pasantia.blockbuster.services;

import ar.com.nec.pasantia.blockbuster.entities.GenerosEntity;
import ar.com.nec.pasantia.blockbuster.exception.GenerosFoundException;
import ar.com.nec.pasantia.blockbuster.repository.GenerosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("generoService")
public class GeneroService {

    private GenerosRepository repoGeneros;

    @Autowired
    public GeneroService(GenerosRepository repoGeneros) {
        this.repoGeneros = repoGeneros;
    }

    public List<GenerosEntity> encontrarTodos() {
        return (List<GenerosEntity>) repoGeneros.findAll();
    }

    public boolean crearGenero(GenerosEntity genero) throws GenerosFoundException {
        if (repoGeneros.existsByNombreIgnoreCase(genero.getNombre())) {
            throw new GenerosFoundException("El genero: " + genero.getNombre() + "ya existe");
        }
        repoGeneros.save(genero);
        return true;
    }
}
