package br.com.itau.modernizacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException{
	private static final long serialVersionUID = 899045331917634559L;
	
	public UserAlreadyExistsException(String message) {
		super(message);
	}

}
