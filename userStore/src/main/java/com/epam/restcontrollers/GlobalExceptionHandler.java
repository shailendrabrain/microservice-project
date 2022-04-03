package com.epam.restcontrollers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.epam.dto.ExceptionResponse;
import com.epam.exceptions.UserException;


@RestControllerAdvice
public class GlobalExceptionHandler {

	
	
	@ExceptionHandler(value=UserException.class)
	public ResponseEntity<ExceptionResponse> bookNotFound(UserException bnf, WebRequest request)
	{
		ExceptionResponse er=new ExceptionResponse();
		er.setPath(request.getDescription(false));
		er.setStatus(HttpStatus.NOT_FOUND.toString());
		er.setTimestemp(new Date().toString());
		er.setError(bnf.getMessage());
		
	  return new ResponseEntity<>(er,HttpStatus.NOT_FOUND);
			
	}




	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> methodArgumentNotValidException(MethodArgumentNotValidException manf,
			WebRequest request) {
		List<String> error = new ArrayList<>();
		manf.getBindingResult().getAllErrors().forEach(errors -> error.add(errors.getDefaultMessage()));

		ExceptionResponse exceptionResponse = new ExceptionResponse();

		exceptionResponse.setError(error.toString());
		exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
		exceptionResponse.setTimestemp(new Date().toString());
		exceptionResponse.setPath(request.getDescription(false));

		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

}
