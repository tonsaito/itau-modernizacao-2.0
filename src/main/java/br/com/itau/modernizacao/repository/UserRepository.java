package br.com.itau.modernizacao.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.itau.modernizacao.entity.UserEntity;

/**
 * User Repository to CRUD UserEntity
 * @author tonsaito
 *
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer>{

	/**
	 * Query para buscar usuario por cpf ou email
	 * @param cpf
	 * @param email
	 * @return UserEntity usuario
	 */
	@Query("SELECT u FROM UserEntity u WHERE (u.cpf = ?1 OR u.email = ?2) AND u.id != ?3")
	UserEntity findByCpfOrEmail(String cpf, String email, Integer id);
	
	
}
