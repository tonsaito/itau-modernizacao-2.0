package br.com.itau.modernizacao.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import br.com.itau.modernizacao.entity.UserEntity;
import br.com.itau.modernizacao.entity.WorkingTimeEntity;
import br.com.itau.modernizacao.entity.WorkingTimeEntity.TYPE;
import br.com.itau.modernizacao.model.WorkingTimeListModel;
import br.com.itau.modernizacao.model.WorkingTimeSubmitModel;
import br.com.itau.modernizacao.service.UserService;
import br.com.itau.modernizacao.service.WorkingTimeService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
	private static final String SAIDA = "SAIDA";
	private static final String ENTRADA = "ENTRADA";
	private static final Integer ID = 1;
	private static final String NOME = "nome";
	private static final String CPF = "111.111.111-11";
	private static final String EMAIL = "email@email.com";
	private static final String DATA_ENTRADA_1 = "2020-04-11 09:00:00";
	private static final String DATA_SAIDA_1 = "2020-04-11 12:00:00";
	private static final String DATA_ENTRADA_2 = "2020-04-11 13:00:00";
	private static final String DATA_SAIDA_2 = "2020-04-11 18:00:00";
	private static final Gson gson = new Gson();

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;

	@MockBean
	private WorkingTimeService workingTimeService;

	@Test
	void shouldReturn200_whenFindAll() throws Exception {
		mockMvc.perform(get("/v1/users").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void shouldReturn404_whenFindById() throws Exception {
		mockMvc.perform(get("/v1/users/1").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value())).andReturn();

		verify(userService, times(1)).findById(any(Integer.class));
	}

	@Test
	void shouldReturn200_whenFindById() throws Exception {
		when(userService.findById(any())).thenReturn(Optional.of(getEntity(ID)));
		MvcResult result = mockMvc
				.perform(get("/v1/users/1").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		verify(userService, times(1)).findById(any(Integer.class));
		UserEntity userEntity = gson.fromJson(result.getResponse().getContentAsString(), UserEntity.class);
		assertThat(userEntity.getNome()).isEqualTo(NOME);
	}

	@Test
	void shouldReturn201_whenSave() throws Exception {
		when(userService.save(any())).thenReturn(getEntity(ID));
		mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(getEntity(null))))
				.andExpect(status().is(HttpStatus.CREATED.value()));

		ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
		verify(userService, times(1)).save(userCaptor.capture());
		assertThat(userCaptor.getValue().getNome()).isEqualTo(NOME);
	}

	@Test
	void shouldReturn200_whenUpdate() throws Exception {
		when(userService.save(any())).thenReturn(getEntity(ID));
		mockMvc.perform(put("/v1/users/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(getEntity(null)))).andExpect(status().isOk());

		ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
		verify(userService, times(1)).update(any(), userCaptor.capture());
		assertThat(userCaptor.getValue().getNome()).isEqualTo(NOME);
	}

	@Test
	void shouldReturn404_whenGetUserLogtime() throws Exception {
		mockMvc.perform(get("/v1/users/1/logtime").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value())).andReturn();

		verify(userService, times(1)).findById(any(Integer.class));
	}

	@Test
	void shouldReturn200_whenGetUserLogtime() throws Exception {
		when(userService.findById(any())).thenReturn(Optional.of(getEntity(ID)));
		when(workingTimeService.findByUser(any())).thenReturn(getWorkingTimeListModel());
		MvcResult result = mockMvc
				.perform(get("/v1/users/1/logtime").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();

		verify(workingTimeService, times(1)).findByUser(any(UserEntity.class));
		assertThat(result.getResponse().getContentAsString()).contains(NOME, CPF, EMAIL);
	}

	@Test
	void shouldReturn404_whenCreateUserLogtime() throws Exception {
		when(workingTimeService.save(any())).thenReturn(getEntityWorkingTime(ID));
		mockMvc.perform(post("/v1/users/1/logtime").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(getWorkingTimeSubmitModel(DATA_ENTRADA_1, ENTRADA))))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

	@Test
	void shouldReturn201_whenCreateUserLogtimeENTRADA() throws Exception {
		when(userService.findById(any())).thenReturn(Optional.of(getEntity(ID)));
		when(workingTimeService.save(any())).thenReturn(getEntityWorkingTime(ID));
		mockMvc.perform(post("/v1/users/1/logtime").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(getWorkingTimeSubmitModel(DATA_ENTRADA_1, ENTRADA))))
				.andExpect(status().is(HttpStatus.CREATED.value()));
		verify(workingTimeService, times(1)).save(any());
	}

	@Test
	void shouldReturn201_whenCreateUserLogtimeSAIDA() throws Exception {
		when(userService.findById(any())).thenReturn(Optional.of(getEntity(ID)));
		when(workingTimeService.save(any())).thenReturn(getEntityWorkingTime(ID));
		mockMvc.perform(post("/v1/users/1/logtime").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(getWorkingTimeSubmitModel(DATA_SAIDA_1, SAIDA))))
				.andExpect(status().is(HttpStatus.CREATED.value()));
		verify(workingTimeService, times(1)).save(any());
	}

	private UserEntity getEntity(Integer id) {
		return new UserEntity(id, NOME, CPF, EMAIL, new Date());
	}

	private WorkingTimeEntity getEntityWorkingTime(Integer id) {
		return new WorkingTimeEntity(id, getEntity(id), new Date(), TYPE.ENTRADA);
	}

	private WorkingTimeListModel getWorkingTimeListModel() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		UserEntity userEntity = getEntity(1);
		List<WorkingTimeEntity> workingTimeEntityList = new ArrayList<WorkingTimeEntity>();
		WorkingTimeListModel workingTimeListModel = new WorkingTimeListModel(userEntity, workingTimeEntityList, "");

		workingTimeEntityList
				.add(new WorkingTimeEntity(1, userEntity, formatter.parse(DATA_ENTRADA_1), TYPE.ENTRADA));
		workingTimeEntityList
				.add(new WorkingTimeEntity(1, userEntity, formatter.parse(DATA_SAIDA_1), TYPE.SAIDA));
		workingTimeEntityList
				.add(new WorkingTimeEntity(1, userEntity, formatter.parse(DATA_ENTRADA_2), TYPE.ENTRADA));
		workingTimeEntityList
				.add(new WorkingTimeEntity(1, userEntity, formatter.parse(DATA_SAIDA_2), TYPE.SAIDA));

		workingTimeListModel.calculateWorkingTime();

		return workingTimeListModel;
	}

	private WorkingTimeSubmitModel getWorkingTimeSubmitModel(String date, String type) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return new WorkingTimeSubmitModel(formatter.parse(date), TYPE.valueOf(type));
	}

}
