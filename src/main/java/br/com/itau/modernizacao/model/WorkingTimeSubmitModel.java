package br.com.itau.modernizacao.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.itau.modernizacao.entity.WorkingTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class WorkingTimeSubmitModel {

	@JsonProperty(value = "data")
	private Date date;
	
	@JsonProperty(value = "tipo")
	private WorkingTimeEntity.TYPE type;

}
