package br.com.itau.modernizacao.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.itau.modernizacao.entity.UserEntity;
import br.com.itau.modernizacao.entity.WorkingTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class WorkingTimeListModel {

	@JsonProperty(value = "usuario")
	private UserEntity userEntity;
	@JsonProperty(value = "logs")
	private List<WorkingTimeEntity> workingTimeEntity;
	@JsonProperty(value = "horasTrabalhadas")
	private String totalWorkLog;

	public void calculateWorkingTime() {
		Date lastEntrance = null;
		long seconds = 0;
		for (WorkingTimeEntity workingTime : this.workingTimeEntity) {
			if (workingTime.getTipo() == WorkingTimeEntity.TYPE.ENTRADA) {
				lastEntrance = workingTime.getData();
			} else {
				if (lastEntrance != null) {
					seconds += getSecondsBetweenDates(lastEntrance, workingTime.getData());
					lastEntrance = null;
				}
			}
		}
		this.totalWorkLog = getTotalWorkLogTime(seconds);
	}

	private long getSecondsBetweenDates(Date date1, Date date2) {
		return (date2.getTime() - date1.getTime()) / 1000;
	}
	
	private String getTotalWorkLogTime(long seconds) {
		StringBuilder worklogTime = new StringBuilder();
	    int hours = (int) seconds / 3600;
	    int remainder = (int) seconds - hours * 3600;
	    int mins = remainder / 60;
	    remainder = remainder - mins * 60;
	    int secs = remainder;

	    worklogTime.append(hours);
	    if(hours == 1) {
	    	worklogTime.append(" hora ");
	    } else {
	    	worklogTime.append(" horas ");
	    }
	    worklogTime.append(mins);
	    if(mins == 1) {
	    	worklogTime.append(" minuto ");
	    } else {
	    	worklogTime.append(" minutos ");
	    }
	    worklogTime.append(secs);
	    if(secs == 1) {
	    	worklogTime.append(" segundo");
	    } else {
	    	worklogTime.append(" segundos");
	    }
	    return worklogTime.toString();
	}

}
