package com.abn.recipe.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abn.recipe.dto.RecipeDto;
import com.abn.recipe.dto.RecipeWithIngredientsDto;
import com.abn.recipe.exception.EmptyIngredientListException;
import com.abn.recipe.exception.EmptyRecipeListException;
import com.abn.recipe.exception.RecipeAlreadyExistsException;
import com.abn.recipe.exception.RecipeNotFoundException;
import com.abn.recipe.model.Recipe;
import com.abn.recipe.service.RecipeBookService;

@RestController
public class RecipeBookController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecipeBookController.class);

	@Autowired
	RecipeBookService recipeBookService;

	@PostMapping("/create")
	public void createRecipe(@Valid @RequestBody RecipeDto recipeDto)
			throws RecipeAlreadyExistsException, EmptyIngredientListException {
		LOGGER.info("Create recipe");
		recipeBookService.createRecipe(recipeDto);
	}

	@DeleteMapping("/delete")
	public void deleteRecipe(@Pattern(regexp = "^\\d{10}$") @RequestParam Long id) throws RecipeNotFoundException {
		recipeBookService.deleteRecipe(id);
	}

	@PutMapping("/update")
	public void updateRecipe(@Pattern(regexp = "^\\d{10}$") @RequestParam Long id,
			@Valid @RequestBody RecipeDto recipeDto) throws RecipeNotFoundException, EmptyIngredientListException {
		recipeBookService.updateRecipe(id, recipeDto);
	}

	@GetMapping("/findAllRecipes")
	public List<RecipeWithIngredientsDto> getAllRecipesWithIngredients() throws EmptyRecipeListException {
		return recipeBookService.getAllRecipesWithIngredients();
	}

}
