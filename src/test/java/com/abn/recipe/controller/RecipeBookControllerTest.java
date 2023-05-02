package com.abn.recipe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abn.recipe.dto.IngredientDto;
import com.abn.recipe.dto.RecipeDto;
import com.abn.recipe.dto.RecipeWithIngredientsDto;
import com.abn.recipe.exception.EmptyIngredientListException;
import com.abn.recipe.exception.EmptyRecipeListException;
import com.abn.recipe.exception.RecipeAlreadyExistsException;
import com.abn.recipe.exception.RecipeNotFoundException;
import com.abn.recipe.service.RecipeBookService;

@ExtendWith(MockitoExtension.class)
public class RecipeBookControllerTest {

	@InjectMocks
	@Spy
	RecipeBookController recipeBookController;

	@Mock
	RecipeBookService recipeBookService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

	}

	@Test
	void testCreateRecipe_success() throws RecipeAlreadyExistsException, EmptyIngredientListException {

		RecipeDto recipeDto = new RecipeDto();
		recipeDto.setName("test recipe");
		recipeBookController.createRecipe(recipeDto);
		verify(recipeBookService).createRecipe(recipeDto);

	}

	@Test
	void testCreateRecipe_alreadyExists() throws RecipeAlreadyExistsException, EmptyIngredientListException {

		RecipeDto recipeDto = new RecipeDto();
		recipeDto.setName("test recipe");
		doThrow(RecipeAlreadyExistsException.class).when(recipeBookService).createRecipe(any(RecipeDto.class));
		assertThrows(RecipeAlreadyExistsException.class, () -> recipeBookController.createRecipe(recipeDto));
	}

	@Test
	void testDeleteRecipe_success() throws RecipeNotFoundException {
		Long id = 1l;
		recipeBookController.deleteRecipe(id);
		verify(recipeBookService).deleteRecipe(id);
	}

	@Test
	void getAllRecipesWithIngredients_success() throws EmptyRecipeListException {

		RecipeWithIngredientsDto recipe1 = new RecipeWithIngredientsDto();

		recipe1.setName("test recipe 1");
		RecipeWithIngredientsDto recipe2 = new RecipeWithIngredientsDto();
		recipe2.setName("test recipe 2");

		List<RecipeWithIngredientsDto> expectedRecipes = new ArrayList<>();
		expectedRecipes.add(recipe1);
		expectedRecipes.add(recipe2);

		when(recipeBookService.getAllRecipesWithIngredients()).thenReturn(expectedRecipes);

		List<RecipeWithIngredientsDto> actualRecipes = recipeBookController.getAllRecipesWithIngredients();

		assertEquals(expectedRecipes, actualRecipes);

	}

	@Test
	void updateRecipe_Success() throws RecipeNotFoundException, EmptyIngredientListException {

		// Given
		Long recipeId = 1l;
		RecipeDto recipeDto = new RecipeDto();
		recipeDto.setName("test recipe");
		IngredientDto ingredientDto = new IngredientDto();
		ingredientDto.setName("test ingredient01");

		recipeDto.setIngredients(Collections.singletonList(ingredientDto));

		// when
		recipeBookController.updateRecipe(recipeId, recipeDto);

		// then
		verify(recipeBookService).updateRecipe(recipeId, recipeDto);

	}

}
