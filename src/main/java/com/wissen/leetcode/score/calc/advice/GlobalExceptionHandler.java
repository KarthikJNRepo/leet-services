package com.wissen.leetcode.score.calc.advice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wissen.leetcode.score.calc.exception.AlreadyExistException;
import com.wissen.leetcode.score.calc.exception.InValidInput;
import com.wissen.leetcode.score.calc.exception.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final String ERROR = "error";

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleInvalidExcetion(MethodArgumentNotValidException ex) {
		Map<String, String> errorMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
		return errorMap;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NotFoundException.class)
	public Map<String, String> handleContentNotFoundExcption(NotFoundException ex) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put(ERROR, ex.getMessage());
		return errorMap;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public Map<String, String> nonUniqueResultException() {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put(ERROR, "empId is already registered");
		return errorMap;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InValidInput.class)
	public Map<String, String> inValidInputException(InValidInput ex) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put(ERROR, ex.getMessage());
		return errorMap;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AlreadyExistException.class)
	public Map<String, String> alreadyExistException(AlreadyExistException ex) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put(ERROR, ex.getMessage());
		return errorMap;
	}
}
