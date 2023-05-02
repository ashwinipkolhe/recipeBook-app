package com.abn.recipe.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abn.recipe.dto.IngredientDto;
import com.abn.recipe.dto.RecipeDto;
import com.abn.recipe.dto.RecipeWithIngredientsDto;
import com.abn.recipe.exception.EmptyIngredientListException;
import com.abn.recipe.exception.EmptyRecipeListException;
import com.abn.recipe.exception.RecipeAlreadyExistsException;
import com.abn.recipe.exception.RecipeNotFoundException;
import com.abn.recipe.model.Ingredient;
import com.abn.recipe.model.IngredientMaster;
import com.abn.recipe.model.Recipe;
import com.abn.recipe.repository.IngredientMasterRepository;
import com.abn.recipe.repository.IngredientRepository;
import com.abn.recipe.repository.RecipeBookRepository;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

	@Mock
	RecipeBookRepository recipeBookRepository;

	@Mock
	IngredientMasterRepository ingredientMasterRepository;

	@Mock
	IngredientRepository ingredientRepository;

	@InjectMocks
	private RecipeBookService recipeBookService;

	private RecipeDto recipeDto;
	private IngredientDto ingredinetDto;

	@BeforeEach
	void setUp() {
		recipeDto = new RecipeDto();
		recipeDto.setName("pasta");
		recipeDto.setNoOfPeople(4);
		recipeDto.setIsVegetarian(false);
		recipeDto.setCookingInstructions("Cook on low flame");

		ingredinetDto = new IngredientDto();
		ingredinetDto.setName("Macroni");

		List<IngredientDto> ingredientList = new ArrayList<>();
		ingredientList.add(ingredinetDto);
		recipeDto.setIngredients(ingredientList);

	}

	@Test
	@DisplayName("Test createRecipe when recipe with same name Does not exist already in database")
	void testCreateRecipeSuccess() throws RecipeAlreadyExistsException, EmptyIngredientListException {
		Recipe recipe = new Recipe();
		recipe.setName(recipeDto.getName());
		recipe.setNoOfPeople(recipeDto.getNoOfPeople());
		recipe.setVegetarian(recipeDto.getIsVegetarian());
		recipe.setDateOfCreation(LocalDateTime.now());
		recipe.setCookingInstructions(recipeDto.getCookingInstructions());

		Optional<Recipe> recipes = Optional.empty();
		when(recipeBookRepository.findByNameIgnoreCase(recipe.getName())).thenReturn(recipes);

		Ingredient ingredient = new Ingredient();
		ingredient.setRecipe(recipe);
		IngredientMaster ingredientMaster = new IngredientMaster();
		ingredientMaster.setName(ingredinetDto.getName());
		Optional<IngredientMaster> ingredientMasterOptional = Optional.of(ingredientMaster);

		when(ingredientMasterRepository.findByNameIgnoreCase(ingredinetDto.getName()))
				.thenReturn(ingredientMasterOptional);

		ingredient.setIngredientMasterId(ingredientMaster);
		List<Ingredient> list = new ArrayList<>();
		recipe.setIngredients(list);
		list.add(ingredient);
		when(recipeBookRepository.save(Mockito.any(Recipe.class))).thenReturn(recipe);
		recipeBookService.createRecipe(recipeDto);
		verify(recipeBookRepository, times(1)).findByNameIgnoreCase(recipe.getName());
		verify(ingredientMasterRepository, times(1)).findByNameIgnoreCase(ingredinetDto.getName());
		verify(ingredientMasterRepository, never()).save(any(IngredientMaster.class));

	}

	@Test
	@DisplayName("Test createRecipe when recipe already exists with same name")
	void testCreateRecipeWhenRecipeAlreadyExists() {

		Recipe recipe = new Recipe();
		recipe.setName(recipeDto.getName());
		Optional<Recipe> recipes = Optional.of(recipe);
		when(recipeBookRepository.findByNameIgnoreCase(recipe.getName())).thenReturn(recipes);
		Assertions.assertThrows(RecipeAlreadyExistsException.class, () -> {
			recipeBookService.createRecipe(recipeDto);
		});

		verify(recipeBookRepository, times(1)).findByNameIgnoreCase(recipe.getName());
		verify(recipeBookRepository, never()).save(any(Recipe.class));
		verify(ingredientMasterRepository, never()).findByNameIgnoreCase(toString());
		verify(ingredientMasterRepository, never()).save(any(IngredientMaster.class));

	}

	@Test
	@DisplayName("Test deleteRecipe with invalid recipe ID")
	void testDeleteRecipeWithInvalidId() {

		Long invalidld = 99L;
		when(recipeBookRepository.findById(invalidld)).thenReturn(Optional.empty());
		// Act and Assert
		assertThrows(RecipeNotFoundException.class, () -> {
			recipeBookService.deleteRecipe(invalidld);
		});
		verify(recipeBookRepository, times(1)).findById(invalidld);
		verify(recipeBookRepository, never()).deleteById(invalidld);

	}

	@Test
	@DisplayName("Test deleteRecipe with valid recipe ID")
	void testDeleteRecipeWithValidId() throws RecipeNotFoundException {
		Recipe existingRecipe = new Recipe();
		existingRecipe.setId(1L);
		when(recipeBookRepository.findById(existingRecipe.getId())).thenReturn(Optional.of(existingRecipe));
		recipeBookService.deleteRecipe(existingRecipe.getId());

		verify(recipeBookRepository, times(1)).findById(existingRecipe.getId());
		verify(recipeBookRepository, times(1)).deleteById(existingRecipe.getId());

	}

	@Test
	@DisplayName("Test getAllRecipesWithIngredients when there are recipes")
	void testGetAllRecipesWithIngredientsWhenRecipesExist() throws EmptyRecipeListException {
		Recipe recipe1 = new Recipe();
		recipe1.setId(1L);
		recipe1.setName("Lasagna");
		IngredientMaster ingredient1 = new IngredientMaster();
		ingredient1.setName("Pasta");
		Ingredient ingredientM1 = new Ingredient();
		ingredientM1.setRecipe(recipe1);
		ingredientM1.setIngredientMasterId(ingredient1);
		recipe1.setIngredients(Arrays.asList(ingredientM1));

		Recipe recipe2 = new Recipe();
		recipe2.setId(2L);
		recipe2.setName("Burger");
		IngredientMaster ingredient2 = new IngredientMaster();
		ingredient2.setName("Bread");
		Ingredient ingredientM2 = new Ingredient();
		ingredientM2.setRecipe(recipe2);
		ingredientM2.setIngredientMasterId(ingredient2);
		recipe2.setIngredients(Arrays.asList(ingredientM2));

		List<Recipe> recipes = Arrays.asList(recipe1, recipe2);
		when(recipeBookRepository.findAll()).thenReturn(recipes);

		RecipeWithIngredientsDto recipeWithIngredients1 = new RecipeWithIngredientsDto();
		recipeWithIngredients1.setId(1L);
		recipeWithIngredients1.setName("Lasagna");
		recipeWithIngredients1.setList(Arrays.asList("Pasta"));

		RecipeWithIngredientsDto recipeWithIngredients2 = new RecipeWithIngredientsDto();
		recipeWithIngredients2.setId(2L);
		recipeWithIngredients2.setName("Burger");
		recipeWithIngredients2.setList(Arrays.asList("Bread"));

		List<RecipeWithIngredientsDto> expectedList = Arrays.asList(recipeWithIngredients1, recipeWithIngredients2);

		List<RecipeWithIngredientsDto> actualList = recipeBookService.getAllRecipesWithIngredients();

		Assertions.assertEquals(expectedList.get(0).getId(), actualList.get(0).getId());
		Assertions.assertEquals(expectedList.get(1).getId(), actualList.get(1).getId());
		Assertions.assertEquals(expectedList.get(0).getName(), actualList.get(0).getName());
		Assertions.assertEquals(expectedList.get(1).getName(), actualList.get(1).getName());
		Assertions.assertEquals(expectedList.get(0).getNoOfPeople(), actualList.get(0).getNoOfPeople());
		Assertions.assertEquals(expectedList.get(1).getNoOfPeople(), actualList.get(1).getNoOfPeople());

		verify(recipeBookRepository, times(1)).findAll();
	}
}
