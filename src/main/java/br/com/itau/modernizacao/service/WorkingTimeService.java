package br.com.itau.modernizacao.service;

import br.com.itau.modernizacao.entity.UserEntity;
import br.com.itau.modernizacao.entity.WorkingTimeEntity;
import br.com.itau.modernizacao.model.WorkingTimeListModel;

public interface WorkingTimeService {
	WorkingTimeListModel findByUser(UserEntity userEntity);
	WorkingTimeEntity save(WorkingTimeEntity workingHourEntity);
}
