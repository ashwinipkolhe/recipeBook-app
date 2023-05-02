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

	/*
	 * @Autowired IngredientListRepository ingredientListRepository;
	 */

	public void createRecipe(RecipeDto recipeDto) throws RecipeAlreadyExistsException, EmptyIngredientListException {

		Recipe recipe = new Recipe();
		recipe.setName(recipeDto.getName());
		recipe.setNoOfPeople(recipeDto.getNoOfPeople());
		recipe.setVegetarian(recipeDto.getIsVegetarian());
		recipe.setDateOfCreation(LocalDateTime.now());
		recipe.setCookingInstructions(recipeDto.getCookingInstructions());
		Optional<Recipe> recipes = recipeBookRepository.findByNameIgnoreCase(recipe.getName());
		if (recipes.isPresent()) {
			throw new RecipeAlreadyExistsException("Recipe with this name already exists");

		} else {

			List<Ingredient> list = new ArrayList<>();
			if (recipeDto.getIngredients().isEmpty()) {
				throw new EmptyIngredientListException(
						"Ingredient List Cannot be empty, Please add atleast one ingredient");
			}

			for (IngredientDto ingredientDto : recipeDto.getIngredients()) {
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

	@Transactional
	public void deleteRecipe(Long id) throws RecipeNotFoundException {

		Recipe existingRecipe = recipeBookRepository.findById(id)
				.orElseThrow(() -> new RecipeNotFoundException("No recipe found with this recipe id"));

		recipeBookRepository.deleteById(id);
	}

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

	@Transactional
	public void updateRecipe(Long id, RecipeDto recipeDto)
			throws RecipeNotFoundException, EmptyIngredientListException {

		Recipe recipe = recipeBookRepository.findById(id)
				.orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id " + id));
		recipe.setName(recipeDto.getName());
		List<Ingredient> list = new ArrayList<>();
		if (recipeDto.getIngredients().isEmpty()) {
			throw new EmptyIngredientListException(
					"Ingredient List Cannot be empty, Please add atleast one ingredient");
		}

		for (IngredientDto ingredientDto : recipeDto.getIngredients()) {
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
		recipe.getIngredients().clear();
		recipe.getIngredients().add(list.get(0));
		recipe.getIngredients().addAll(list);
		recipeBookRepository.save(recipe);
	}

}
