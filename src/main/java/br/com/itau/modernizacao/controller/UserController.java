package br.com.itau.modernizacao.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.itau.modernizacao.entity.UserEntity;
import br.com.itau.modernizacao.exception.UserNotFoundException;
import br.com.itau.modernizacao.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserEntity> findAll() {
		 List<UserEntity> userEntityList = userService.findAll();
		 userEntityList.stream().forEach((UserEntity userEntity) -> {
			userEntity.add(linkTo(methodOn(this.getClass()).getUserById(userEntity.getId())).withRel("get-user-by-id"));
		});
		return userEntityList;
	}

	@GetMapping(value = "/v1/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserEntity> getUserById(@PathVariable("id") Integer id) {
		UserEntity userEntity = userService.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
		userEntity.add(linkTo(methodOn(this.getClass()).findAll()).withRel("all-users"));
		return ResponseEntity.ok(userEntity);
	}

	@PostMapping(value = "/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createUser(@Valid @RequestBody UserEntity userEntity) {
		UserEntity savedUserEntity = userService.save(userEntity);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedUserEntity.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(value = "/v1/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> editUser(@Valid @RequestBody UserEntity userEntity, @PathVariable("id") Integer id) {
		UserEntity savedUserEntity = userService.update(id, userEntity);
		return ResponseEntity.ok(savedUserEntity);
	}

}
