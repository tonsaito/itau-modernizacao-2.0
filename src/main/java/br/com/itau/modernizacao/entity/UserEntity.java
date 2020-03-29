package br.com.itau.modernizacao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class UserEntity extends RepresentationModel<UserEntity>{
	
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;
	
	@Column
	private String nome;
	
	@Column
	private String cpf;
	
	@Column
	private String email;
	
	@Column
	private Date cadastro;

}
