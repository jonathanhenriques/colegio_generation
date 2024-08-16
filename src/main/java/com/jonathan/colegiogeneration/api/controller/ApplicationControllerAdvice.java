//package com.jonathan.colegiogeneration.api.controller;
//
//import java.time.LocalDateTime;
//
//
//import com.jonathan.colegiogeneration.api.exception.AlunoNaoEncontradoException;
//import com.jonathan.colegiogeneration.api.exception.ApiErrors;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.ConstraintViolationException;
//import org.springframework.dao.DataAccessException;
//import org.springframework.dao.InvalidDataAccessResourceUsageException;
//import org.springframework.dao.support.PersistenceExceptionTranslationInterceptor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.transaction.TransactionSystemException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//import org.springframework.web.servlet.resource.NoResourceFoundException;
//
//
///**
// * Classe manipulador de erros que recebe os erros da aplicação
// * que foram declarados aqui e os modifica
// *
// * @author hsjon
// *
// */
//@RestControllerAdvice
//public class ApplicationControllerAdvice {
//
//	@ExceptionHandler(AlunoNaoEncontradoException.class)
////	 @ResponseStatus(HttpStatus.NOT_FOUND)
//	public ResponseEntity<ApiErrors> handleAlunoNotFoundException(AlunoNaoEncontradoException ex,
//																		   HttpServletRequest request) {
//		ApiErrors error = new ApiErrors(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
//				request.getRequestURI());
//
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
//	}
//
//	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
////	 @ResponseStatus(HttpStatus.NOT_FOUND)
//	public ResponseEntity<ApiErrors> handleMethodArgumentTypeMismatchException(AlunoNaoEncontradoException ex,
//																  HttpServletRequest request) {
//		ApiErrors error = new ApiErrors(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
//				request.getRequestURI());
//
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//	}
//
////	@ExceptionHandler(InvalidDataAccessResourceUsageException.class)
//////	 @ResponseStatus(HttpStatus.NOT_FOUND)
////	public ResponseEntity<ApiErrors> handleInvalidDataAccessResourceUsageException(AlunoNaoEncontradoException ex,
////																			   HttpServletRequest request) {
////		ApiErrors error = new ApiErrors(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(),
////				request.getRequestURI());
////
////		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
////	}
//
//	@ExceptionHandler(NoResourceFoundException.class)
////	 @ResponseStatus(HttpStatus.NOT_FOUND)
//	public ResponseEntity<ApiErrors> handleNoResourceFoundException(AlunoNaoEncontradoException ex,
//																				   HttpServletRequest request) {
//		ApiErrors error = new ApiErrors(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
//				request.getRequestURI());
//
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
//	}
//
//	@ExceptionHandler(HttpMessageNotReadableException.class)
////	 @ResponseStatus(HttpStatus.NOT_FOUND)
//	public ResponseEntity<ApiErrors> handleHttpMessageNotReadableException(AlunoNaoEncontradoException ex,
//																	HttpServletRequest request) {
//		ApiErrors error = new ApiErrors(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
//				request.getRequestURI());
//
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//	}
//
//	@ExceptionHandler(TransactionSystemException.class)
////	 @ResponseStatus(HttpStatus.NOT_FOUND)
//	public ResponseEntity<ApiErrors> handleTransactionSystemException(AlunoNaoEncontradoException ex,
//																		   HttpServletRequest request) {
//		ApiErrors error = new ApiErrors(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(),
//				request.getRequestURI());
//
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//	}
//
//	@ExceptionHandler(IllegalArgumentException.class)
////	 @ResponseStatus(HttpStatus.NOT_FOUND)
//	public ResponseEntity<ApiErrors> handleIllegalArgumentException(AlunoNaoEncontradoException ex,
//																	  HttpServletRequest request) {
//		ApiErrors error = new ApiErrors(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(),
//				request.getRequestURI());
//
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//	}
//
//	@ExceptionHandler(ConstraintViolationException.class)
////	 @ResponseStatus(HttpStatus.NOT_FOUND)
//	public ResponseEntity<ApiErrors> handleConstraintViolationException(AlunoNaoEncontradoException ex,
//																	HttpServletRequest request) {
//		ApiErrors error = new ApiErrors(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(),
//				request.getRequestURI());
//
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//	}
//
//
//
//	@ExceptionHandler(DataAccessException.class)
////	 @ResponseStatus(HttpStatus.NOT_FOUND)
//	public ResponseEntity<ApiErrors> handleDataAccessException(AlunoNaoEncontradoException ex,
//																		HttpServletRequest request) {
//		ApiErrors error = new ApiErrors(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(),
//				request.getRequestURI());
//
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//	}
//
//
//
//
////	Exception padrão OLD
////    @ExceptionHandler(AlunoNotFoundException.class)
////    @ResponseStatus(HttpStatus.NOT_FOUND)
////    public ApiErrors handleCategoriaProvaNotFoundException(AlunoNotFoundException ex) {
////        String mensagemErro = ex.getMessage();
////        return new ApiErrors(mensagemErro);
////    }
//
//
//
//}
