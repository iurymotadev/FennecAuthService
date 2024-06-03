package com.fennec.authservice.exception.handler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fennec.authservice.exception.BusinessException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<Exception.Field> fields = new ArrayList<>();

		for (ObjectError error : ex.getBindingResult().getAllErrors()) {

			String name = ((FieldError) error).getField();
			String message = error.getDefaultMessage();

			fields.add(new Exception.Field(name, message));

		}

		Exception exception = new Exception();
		exception.setStatus(status.value());
		exception.setTime(LocalDateTime.now());
		exception.setTitle("One or more fields were incorrectly filled. Fill correctly and try again.");
		exception.setFields(fields);

		return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;

		Exception exception = new Exception();
		exception.setStatus(status.value());
		exception.setTime(LocalDateTime.now());
		exception.setTitle(ex.getMessage());

		return handleExceptionInternal(ex, exception, new HttpHeaders(), status, request);

	}

}
