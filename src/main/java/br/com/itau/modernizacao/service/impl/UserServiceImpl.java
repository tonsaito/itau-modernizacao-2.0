package br.com.itau.modernizacao.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.itau.modernizacao.entity.UserEntity;
import br.com.itau.modernizacao.exception.UserAlreadyExistsException;
import br.com.itau.modernizacao.exception.UserNotFoundException;
import br.com.itau.modernizacao.repository.UserRepository;
import br.com.itau.modernizacao.service.UserService;

@Component
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository repository;

	/**
	 * Busca todos usuarios
	 * @return List<UserEntity> lista de usuarios
	 */
	@Override
	public List<UserEntity> findAll() {
		return (List<UserEntity>) repository.findAll();
	}

	/**
	 * Busca usuario por id
	 * @param id	ID do usuario
	 * @return Optional<UserEntity> usuario encontrado
	 */
	@Override
	public Optional<UserEntity> findById(Integer id) {
		return repository.findById(id);
	}
	
	/**
	 * Busca usuario por cpf ou email
	 * @param cpf		Cpf
	 * @param email 	Email
	 * @return Optional<UserEntity> usuario encontrado
	 */
	@Override
	public Optional<UserEntity> findByCpfOrEmail(String cpf, String email, Integer id) {
		return Optional.ofNullable(repository.findByCpfOrEmail(cpf, email, id));
	}
	

	/**
	 * Salva usuario
	 * @param UserEntity	usuario a ser salvo
	 * @return UserEntity 	usuario salvo
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public UserEntity save(UserEntity userEntity) {
		if(userEntity != null && userEntity.getId() == null) {
			if(this.findByCpfOrEmail(userEntity.getCpf(), userEntity.getEmail(), 0).isPresent()) {
				throw new UserAlreadyExistsException("CPF ou e-mail já existente.");
			}
		}
		return repository.save(userEntity);
	}

	@Override
	public UserEntity update(Integer id, UserEntity userEntity) {
		UserEntity saveUserEntity = findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
		if(StringUtils.isNotBlank(userEntity.getNome())) {
			saveUserEntity.setNome(userEntity.getNome());
		}
		if(StringUtils.isNotBlank(userEntity.getCpf())) {
			saveUserEntity.setCpf(userEntity.getCpf());
		}
		if(StringUtils.isNotBlank(userEntity.getEmail())){
			saveUserEntity.setEmail(userEntity.getEmail());
		}
		if(repository.findByCpfOrEmail(saveUserEntity.getCpf(), saveUserEntity.getEmail(), id) != null){
			throw new UserAlreadyExistsException("CPF ou e-mail já existente.");
		}
		return repository.save(saveUserEntity);
	}

}
