package com.jonathan.colegiogeneration.api.controller;

import java.time.LocalDateTime;


import com.jonathan.colegiogeneration.api.exception.AlunoNaoEncontradoException;
import com.jonathan.colegiogeneration.api.exception.ApiErrors;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Classe manipulador de erros que recebe os erros da aplicação
 * que foram declarados aqui e os modifica
 *
 * @author hsjon
 *
 */
@RestControllerAdvice
public class ApplicationControllerAdvice {

	@ExceptionHandler(AlunoNaoEncontradoException.class)
//	 @ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ApiErrors> handleAlunoNotFoundException(AlunoNaoEncontradoException ex,
																		   HttpServletRequest request) {
		ApiErrors error = new ApiErrors(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

//	Exception padrão OLD
//    @ExceptionHandler(AlunoNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ApiErrors handleCategoriaProvaNotFoundException(AlunoNotFoundException ex) {
//        String mensagemErro = ex.getMessage();
//        return new ApiErrors(mensagemErro);
//    }



}
