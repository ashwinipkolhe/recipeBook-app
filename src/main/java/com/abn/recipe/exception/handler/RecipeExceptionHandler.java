package com.abn.recipe.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.abn.recipe.error.response.ErrorResponse;
import com.abn.recipe.exception.EmptyRecipeListException;
import com.abn.recipe.exception.RecipeAlreadyExistsException;
import com.abn.recipe.exception.RecipeNotFoundException;

@RestControllerAdvice
public class RecipeExceptionHandler {

	@ExceptionHandler(RecipeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleRecipeNotFoundException(RecipeNotFoundException ex) {
		return new ErrorResponse(HttpStatus.NOT_FOUND.value(), "RECIPE_NOT_FOUND_EXCEPTION",
				"No Recipe Found With This Recipe Id", LocalDateTime.now());
	}

	@ExceptionHandler(RecipeAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorResponse handleRecipeAlreadyExistsException(RecipeAlreadyExistsException ex) {
		return new ErrorResponse(HttpStatus.CONFLICT.value(), "RECIPE_ALREADY_EXISTS_EXCEPTION",
				"Recipe With Same Name Already Exists", LocalDateTime.now());
	}

	@ExceptionHandler(EmptyRecipeListException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleEmptyRecipeListException(EmptyRecipeListException ex) {
		return new ErrorResponse(HttpStatus.NOT_FOUND.value(), "EMPTY_RECIPE_LIST_EXCEPTION", "No Recipes Found",
				LocalDateTime.now());
	}

}
