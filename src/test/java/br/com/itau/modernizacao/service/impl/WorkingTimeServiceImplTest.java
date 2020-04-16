package br.com.itau.modernizacao.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.itau.modernizacao.entity.UserEntity;
import br.com.itau.modernizacao.entity.WorkingTimeEntity;
import br.com.itau.modernizacao.entity.WorkingTimeEntity.TYPE;
import br.com.itau.modernizacao.model.WorkingTimeListModel;
import br.com.itau.modernizacao.repository.WorkingTimeRepository;
import br.com.itau.modernizacao.service.WorkingTimeService;

public class WorkingTimeServiceImplTest {
	private static final Integer ID = 1;
	private static final String NOME = "nome";
	private static final String CPF = "111.111.111-11";
	private static final String EMAIL = "email@email.com";
	private WorkingTimeRepository repository;
	private WorkingTimeService service;

	@BeforeEach
	public void setup() {
		repository = mock(WorkingTimeRepository.class);
		service = new WorkingTimeServiceImpl();
		ReflectionTestUtils.setField(service, "repository", repository, WorkingTimeRepository.class);
	}

	@Test
	public void shouldFindById() {
		when(repository.findAllByUserId(any(Integer.class))).thenReturn(Arrays.asList(getEntity(TYPE.ENTRADA), getEntity(TYPE.SAIDA)));
		WorkingTimeListModel workingTimeListModel = service.findByUser(getUserEntity(ID));
		assertEquals(NOME, workingTimeListModel.getUserEntity().getNome());
		assertEquals(CPF, workingTimeListModel.getUserEntity().getCpf());
		assertEquals(EMAIL, workingTimeListModel.getUserEntity().getEmail());
		assertEquals(TYPE.ENTRADA, workingTimeListModel.getWorkingTimeEntityList().get(0).getType());
		assertEquals(TYPE.SAIDA, workingTimeListModel.getWorkingTimeEntityList().get(1).getType());
	}
	
	@Test
	public void shouldSaveENTRADA() {
		service.save(getEntity(TYPE.ENTRADA));
		verify(repository, times(1)).save(any(WorkingTimeEntity.class));
	}

	

	private UserEntity getUserEntity(Integer id) {
		return new UserEntity(id, NOME, CPF, EMAIL, new Date());
	}
	
	private WorkingTimeEntity getEntity(TYPE type) {
		return new WorkingTimeEntity(ID, getUserEntity(ID), new Date(), type);
	}

}
