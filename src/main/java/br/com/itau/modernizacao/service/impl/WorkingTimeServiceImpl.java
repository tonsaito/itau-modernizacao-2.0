package br.com.itau.modernizacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.itau.modernizacao.entity.WorkingTimeEntity;
import br.com.itau.modernizacao.model.WorkingTimeListModel;
import br.com.itau.modernizacao.repository.WorkingTimeRepository;
import br.com.itau.modernizacao.service.WorkingTimeService;

@Component
public class WorkingTimeServiceImpl implements WorkingTimeService{
	
	@Autowired
	private WorkingTimeRepository repository;

	@Override
	public WorkingTimeListModel findByUserId(Integer userId) {
		WorkingTimeListModel listModel = new WorkingTimeListModel();
		List<WorkingTimeEntity> workingTimeList = repository.findAllByUserId(userId);
		listModel.setUserEntity(workingTimeList.get(0).getUsuario());
		listModel.setWorkingTimeEntity(workingTimeList);
		listModel.calculateWorkingTime();
		return listModel;
	}

	@Override
	public WorkingTimeEntity save(WorkingTimeEntity workingHourEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
