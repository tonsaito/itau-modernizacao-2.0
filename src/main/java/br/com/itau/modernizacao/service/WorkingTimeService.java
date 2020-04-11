package br.com.itau.modernizacao.service;

import br.com.itau.modernizacao.entity.WorkingTimeEntity;
import br.com.itau.modernizacao.model.WorkingTimeListModel;

public interface WorkingTimeService {
	WorkingTimeListModel findByUserId(Integer userId);
	WorkingTimeEntity save(WorkingTimeEntity workingHourEntity);
}
