package br.com.itau.modernizacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.itau.modernizacao.entity.UserEntity;
import br.com.itau.modernizacao.entity.WorkingTimeEntity;
import br.com.itau.modernizacao.model.WorkingTimeListModel;
import br.com.itau.modernizacao.repository.WorkingTimeRepository;
import br.com.itau.modernizacao.service.WorkingTimeService;

@Component
public class WorkingTimeServiceImpl implements WorkingTimeService{
	
	@Autowired
	private WorkingTimeRepository repository;

	@Override
	public WorkingTimeListModel findByUser(UserEntity userEntity) {
		WorkingTimeListModel listModel = new WorkingTimeListModel();
		List<WorkingTimeEntity> workingTimeList = repository.findAllByUserId(userEntity.getId());
		listModel.setWorkingTimeEntityList(workingTimeList);
		listModel.calculateWorkingTime();
		listModel.setUserEntity(userEntity);
		return listModel;
	}

	@Override
	public WorkingTimeEntity save(WorkingTimeEntity workingHourEntity) {
		repository.save(workingHourEntity);
		return null;
	}


}
