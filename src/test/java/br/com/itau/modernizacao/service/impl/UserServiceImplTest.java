package br.com.itau.modernizacao.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.itau.modernizacao.entity.UserEntity;
import br.com.itau.modernizacao.exception.UserAlreadyExistsException;
import br.com.itau.modernizacao.repository.UserRepository;
import br.com.itau.modernizacao.service.UserService;

public class UserServiceImplTest {
	private static final Integer ID = 1;
	private static final String NOME = "nome";
	private static final String CPF = "111.111.111-11";
	private static final String EMAIL = "email@email.com";
	private UserRepository repository;
	private UserService service;

	@BeforeEach
	public void setup() {
		repository = mock(UserRepository.class);
		service = new UserServiceImpl();
		ReflectionTestUtils.setField(service, "repository", repository, UserRepository.class);
	}

	@Test
	public void shouldFindAll() {
		when(repository.findAll()).thenReturn(Arrays.asList(getEntity(ID)));
		List<UserEntity> list = service.findAll();
		assertEquals(1, list.size());
		assertEquals(NOME, list.get(0).getNome());
	}

	@Test
	public void shouldFindById() {
		when(repository.findById(any(Integer.class))).thenReturn(Optional.of(getEntity(ID)));
		UserEntity entity = service.findById(1).get();
		assertEquals(NOME, entity.getNome());
	}

	@Test
	public void shouldFindByCpfOrEmail() {
		when(repository.findByCpfOrEmail(any(String.class), any(String.class), any(Integer.class))).thenReturn(getEntity(ID));
		UserEntity entity = service.findByCpfOrEmail(CPF, EMAIL, 0).get();
		assertEquals(NOME, entity.getNome());
	}
	
	@Test
	public void shouldSave() {
		service.save(getEntity(null));
		verify(repository, times(1)).save(any(UserEntity.class));
	}
	
	@Test
	public void shouldNotSaveDueTheConflict() {
		when(repository.findByCpfOrEmail(any(String.class), any(String.class), any())).thenReturn(getEntity(ID));
		assertThrows(UserAlreadyExistsException.class, () -> {
			service.save(getEntity(null));
		});
	}
	
	@Test
	public void shouldUpdate() {
		when(repository.findById(any(Integer.class))).thenReturn(Optional.of(getEntity(ID)));
		service.update(1, getEntity(ID));
		verify(repository, times(1)).findById(any(Integer.class));
		verify(repository, times(1)).save(any(UserEntity.class));
	}
	
	@Test
	public void shouldNotUpdateDueTheConflict() {
		when(repository.findById(any(Integer.class))).thenReturn(Optional.of(getEntity(ID)));
		when(repository.findByCpfOrEmail(any(String.class), any(String.class), any())).thenReturn(getEntity(ID));
		assertThrows(UserAlreadyExistsException.class, () -> {
			service.update(1, getEntity(ID));
		});
	}
	

	private UserEntity getEntity(Integer id) {
		return new UserEntity(id, NOME, CPF, EMAIL, new Date());
	}

}
