package com.abn.recipe.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class RecipeBookService {
	@Autowired
	RecipeBookRepository recipeBookRepository;

	@Autowired
	IngredientMasterRepository ingredientMasterRepository;

	@Autowired
	IngredientRepository ingredientRepository;

	/**
	 * 
	 * This method will create New Recipe in Database
	 * 
	 * @param newRecipeDto
	 * @throws RecipeAlreadyExistsException
	 * @throws EmptyIngredientListException
	 */
	public void createRecipe(RecipeDto newRecipeDto) throws RecipeAlreadyExistsException, EmptyIngredientListException {

		Recipe recipe = new Recipe();
		recipe.setName(newRecipeDto.getName());
		recipe.setNoOfPeople(newRecipeDto.getNoOfPeople());
		recipe.setVegetarian(newRecipeDto.getIsVegetarian());
		recipe.setDateOfCreation(LocalDateTime.now());
		recipe.setCookingInstructions(newRecipeDto.getCookingInstructions());
		Optional<Recipe> recipes = recipeBookRepository.findByNameIgnoreCase(recipe.getName());
		if (recipes.isPresent()) {
			throw new RecipeAlreadyExistsException("Recipe with this name already exists");

		} else {

			List<Ingredient> list = new ArrayList<>();
			if (newRecipeDto.getIngredients().isEmpty()) {
				throw new EmptyIngredientListException(
						"Ingredient List Cannot be empty, Please add atleast one ingredient");
			}

			for (IngredientDto ingredientDto : newRecipeDto.getIngredients()) {
				Ingredient ingredient = new Ingredient();
				Optional<IngredientMaster> ingredientMasterOptional = ingredientMasterRepository
						.findByNameIgnoreCase(ingredientDto.getName());

				IngredientMaster ingredientMaster;

				if (ingredientMasterOptional.isPresent()) {
					ingredientMaster = ingredientMasterOptional.get();
				} else {
					ingredientMaster = new IngredientMaster();
					ingredientMaster.setName(ingredientDto.getName());
					ingredientMasterRepository.save(ingredientMaster);
				}
				ingredient.setIngredientMasterId(ingredientMaster);
				ingredient.setRecipe(recipe);
				list.add(ingredient);

			}
			recipe.setIngredients(list);
			recipeBookRepository.save(recipe);
		}
	}

	/**
	 * This method will delete Recipe from database based on recipeId
	 * 
	 * @param recipeId
	 * @throws RecipeNotFoundException
	 */
	@Transactional
	public void deleteRecipe(Long recipeId) throws RecipeNotFoundException {

		Recipe existingRecipe = recipeBookRepository.findById(recipeId)
				.orElseThrow(() -> new RecipeNotFoundException("No recipe found with this recipe id"));

		recipeBookRepository.deleteById(recipeId);
	}

	/**
	 * This method will return all recipes from database along with their Ingrdients
	 * list
	 * 
	 * @throws EmptyRecipeListException
	 */
	public List<RecipeWithIngredientsDto> getAllRecipesWithIngredients() throws EmptyRecipeListException {
		List<Recipe> recipes = recipeBookRepository.findAll();

		if (recipes.isEmpty()) {
			throw new EmptyRecipeListException("No Recipes Found");
		}
		List<RecipeWithIngredientsDto> recipewithIngredientsList = new ArrayList<>();
		for (Recipe recipe : recipes) {

			RecipeWithIngredientsDto recipeWithIngredients = new RecipeWithIngredientsDto();
			recipeWithIngredients.setId(recipe.getId());
			recipeWithIngredients.setName(recipe.getName());
			recipeWithIngredients.setCookingInstructions(recipe.getCookingInstructions());
			recipeWithIngredients.setVegetarian(recipe.isVegetarian());
			recipeWithIngredients.setNoOfPeople(recipe.getNoOfPeople());
			recipeWithIngredients.setDateOfCreation(recipe.getDateOfCreation());

			List<Ingredient> ingredients = recipe.getIngredients();

			List<String> ingredientNames = new ArrayList<>();

			/**
			 * find list of ingredient names
			 */
			for (Ingredient ingredient : ingredients) {
				ingredientNames.add(ingredient.getIngredientMasterId().getName());

			}

			recipeWithIngredients.setList(ingredientNames);
			recipewithIngredientsList.add(recipeWithIngredients);

		}

		return recipewithIngredientsList;

	}

	/**
	 * This method will update Recipe and its respective contents into database
	 * based on recipeId
	 * 
	 * @param recipeId
	 * @param updatedRecipeDto
	 * @throws RecipeNotFoundException
	 * @throws EmptyIngredientListException
	 */
	@Transactional
	public void updateRecipe(Long recipeId, RecipeDto updatedRecipeDto)
			throws RecipeNotFoundException, EmptyIngredientListException {

		// Find existing Recipe from Database using recipeId
		Recipe existingRecipe = recipeBookRepository.findById(recipeId)
				.orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id " + recipeId));

		// Update values
		existingRecipe.setName(updatedRecipeDto.getName());
		existingRecipe.setNoOfPeople(updatedRecipeDto.getNoOfPeople());
		existingRecipe.setVegetarian(updatedRecipeDto.getIsVegetarian());
		// recipe.setDateOfCreation(recipe.getDateOfCreation());
		existingRecipe.setCookingInstructions(updatedRecipeDto.getCookingInstructions());

		// Check if ingredient List is empty
		List<Ingredient> list = new ArrayList<>();
		if (updatedRecipeDto.getIngredients().isEmpty()) {
			throw new EmptyIngredientListException(
					"Ingredient List Cannot be empty, Please add atleast one ingredient");
		}

		for (IngredientDto ingredientDto : updatedRecipeDto.getIngredients()) {
			Ingredient ingredient = new Ingredient();
			Optional<IngredientMaster> ingredientMasterOptional = ingredientMasterRepository
					.findByNameIgnoreCase(ingredientDto.getName());

			IngredientMaster ingredientMaster;

			if (ingredientMasterOptional.isPresent()) {
				ingredientMaster = ingredientMasterOptional.get();
			} else {
				ingredientMaster = new IngredientMaster();
				ingredientMaster.setName(ingredientDto.getName());
				ingredientMasterRepository.save(ingredientMaster);
			}
			ingredient.setIngredientMasterId(ingredientMaster);
			ingredient.setRecipe(existingRecipe);
			list.add(ingredient);

		}
		existingRecipe.getIngredients().clear();
		existingRecipe.getIngredients().add(list.get(0));
		existingRecipe.getIngredients().addAll(list);
		recipeBookRepository.save(existingRecipe);
	}

}
