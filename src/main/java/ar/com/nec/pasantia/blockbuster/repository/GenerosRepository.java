package ar.com.nec.pasantia.blockbuster.repository;

import ar.com.nec.pasantia.blockbuster.entities.GenerosEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GenerosRepository extends CrudRepository<GenerosEntity,Integer> {
	List<GenerosEntity> findGenerosEntityByIdgeneros(int Id);
}
