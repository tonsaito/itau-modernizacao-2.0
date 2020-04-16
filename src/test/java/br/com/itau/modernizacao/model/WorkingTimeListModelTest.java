package br.com.itau.modernizacao.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.itau.modernizacao.entity.UserEntity;
import br.com.itau.modernizacao.entity.WorkingTimeEntity;
import br.com.itau.modernizacao.entity.WorkingTimeEntity.TYPE;

public class WorkingTimeListModelTest {

	
	@Test
	public void calculateWorkingTime() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		UserEntity userEntity = new UserEntity(1, "teste", "111.111.111-11", "teste@email.com", new Date());
		List<WorkingTimeEntity> workingTimeEntityList = new ArrayList<WorkingTimeEntity>();
		WorkingTimeListModel workingTimeListModel = new WorkingTimeListModel(userEntity, workingTimeEntityList, "");
		
		workingTimeEntityList.add(new WorkingTimeEntity(1, userEntity, formatter.parse("2020-04-11 09:00:00"), TYPE.ENTRADA));
		workingTimeEntityList.add(new WorkingTimeEntity(1, userEntity, formatter.parse("2020-04-11 12:00:00"), TYPE.SAIDA));
		workingTimeEntityList.add(new WorkingTimeEntity(1, userEntity, formatter.parse("2020-04-11 13:00:00"), TYPE.ENTRADA));
		workingTimeEntityList.add(new WorkingTimeEntity(1, userEntity, formatter.parse("2020-04-11 18:00:00"), TYPE.SAIDA));
		
		workingTimeListModel.calculateWorkingTime();
		assertEquals("8 horas 0 minutos 0 segundos", workingTimeListModel.getTotalWorkLog());
		
		workingTimeEntityList.add(new WorkingTimeEntity(1, userEntity, formatter.parse("2020-04-11 09:00:00"), TYPE.ENTRADA));
		workingTimeEntityList.add(new WorkingTimeEntity(1, userEntity, formatter.parse("2020-04-11 12:00:00"), TYPE.SAIDA));
		workingTimeEntityList.add(new WorkingTimeEntity(1, userEntity, formatter.parse("2020-04-11 13:00:00"), TYPE.ENTRADA));
		workingTimeEntityList.add(new WorkingTimeEntity(1, userEntity, formatter.parse("2020-04-11 18:00:00"), TYPE.SAIDA));
		
		workingTimeEntityList.clear();
		workingTimeEntityList.add(new WorkingTimeEntity(1, userEntity, formatter.parse("2020-04-11 12:00:00"), TYPE.ENTRADA));
		workingTimeEntityList.add(new WorkingTimeEntity(1, userEntity, formatter.parse("2020-04-11 15:21:34"), TYPE.SAIDA));
		
		workingTimeListModel.calculateWorkingTime();
		assertEquals("3 horas 21 minutos 34 segundos", workingTimeListModel.getTotalWorkLog());
		
		workingTimeEntityList.clear();
		workingTimeEntityList.add(new WorkingTimeEntity(1, userEntity, formatter.parse("2020-04-11 08:00:00"), TYPE.ENTRADA));
		workingTimeEntityList.add(new WorkingTimeEntity(1, userEntity, formatter.parse("2020-04-11 13:21:01"), TYPE.SAIDA));
		workingTimeEntityList.add(new WorkingTimeEntity(1, userEntity, formatter.parse("2020-04-11 14:45:00"), TYPE.ENTRADA));
		workingTimeEntityList.add(new WorkingTimeEntity(1, userEntity, formatter.parse("2020-04-11 19:23:00"), TYPE.SAIDA));
		
		workingTimeListModel.calculateWorkingTime();
		assertEquals("9 horas 59 minutos 1 segundo", workingTimeListModel.getTotalWorkLog());
		
		workingTimeEntityList.clear();
		workingTimeEntityList.add(new WorkingTimeEntity(1, userEntity, formatter.parse("2020-04-11 09:00:00"), TYPE.ENTRADA));
		workingTimeEntityList.add(new WorkingTimeEntity(1, userEntity, formatter.parse("2020-04-11 09:02:01"), TYPE.ENTRADA));
		
		workingTimeListModel.calculateWorkingTime();
		assertEquals("0 horas 0 minutos 0 segundos", workingTimeListModel.getTotalWorkLog());
	}
}
