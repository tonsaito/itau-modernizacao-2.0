package br.com.itau.modernizacao.service;

import java.util.List;
import java.util.Optional;

import br.com.itau.modernizacao.entity.UserEntity;

public interface UserService {
	List<UserEntity> findAll();
	Optional<UserEntity> findById(Integer id);
	Optional<UserEntity> findByCpfOrEmail(String cpf, String email, Integer id);
	UserEntity save(UserEntity userEntity);
	UserEntity update(Integer id, UserEntity userEntity);
}
