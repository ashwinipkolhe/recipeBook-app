package com.abn.recipe.exception.handler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.abn.recipe.error.response.ErrorResponse;
import com.abn.recipe.exception.EmptyIngredientListException;

@RestControllerAdvice
public class IngredientExceptionHandler {
	@ExceptionHandler(EmptyIngredientListException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleEmptyIngredientListException(EmptyIngredientListException ex) {
		return new ErrorResponse(HttpStatus.NOT_FOUND.value(), "EMPTY_INGREDIENT_LIST_EXCEPTION",
				"Ingredients List Cannot Be Empty, Please Add Atleast One Ingredient", LocalDateTime.now());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		Map<String, String> errors = new HashMap<>();
		result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return errors;
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION",
				"Argument type is not valid, id should be a number", LocalDateTime.now());
	}

}
