package br.com.itau.modernizacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.itau.modernizacao.entity.WorkingTimeEntity;

/**
 * User Repository to CRUD WorkingTimeEntity
 * @author tonsaito
 *
 */
@Repository
public interface WorkingTimeRepository extends CrudRepository<WorkingTimeEntity, Integer>{

	/**
	 * Query para buscar todos as batidas de ponto de um usuario ordenado por data
	 * @param userId
	 * @return List<WorkingTimeEntity>
	 */
	@Query("SELECT w FROM WorkingTimeEntity w WHERE w.user.id = ?1 ORDER BY w.date ASC")
	List<WorkingTimeEntity> findAllByUserId(Integer userId);
}
