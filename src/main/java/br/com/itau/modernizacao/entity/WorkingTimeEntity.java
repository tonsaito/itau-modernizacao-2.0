package br.com.itau.modernizacao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "workingtime", uniqueConstraints = { @UniqueConstraint(columnNames = "id") })
public class WorkingTimeEntity {

	public static enum TYPE {
		ENTRADA(1), SAIDA(2);
		
		private final int value;

		TYPE(final int newValue) {
            value = newValue;
        }

		public int getValue() {
			return value;
		}
	}

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@JsonIgnore
	private UserEntity usuario;

	@Column
	private Date data;

	@Column
	@Enumerated(EnumType.STRING)
	private TYPE tipo;
}
