package com.abn.recipe.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RecipeDto {

	@NotBlank
	private String name;
	private List<IngredientDto> ingredients;
	private LocalDateTime dateOfCreation;
	private boolean isVegetarian;
	private int noOfPeople;
	public String cookingInstructions;

	public String getCookingInstructions() {
		return cookingInstructions;
	}

	public void setCookingInstructions(String cookingInstructions) {
		this.cookingInstructions = cookingInstructions;
	}

	public void setVegetarian(boolean isVegetarian) {
		this.isVegetarian = isVegetarian;
	}

	public RecipeDto() {

	}

	public RecipeDto(String name, List<IngredientDto> ingredients, LocalDateTime dateOfCreation, boolean isVegetarian,
			int noOfPeople) {
		super();
		this.name = name;
		this.ingredients = ingredients;
		this.dateOfCreation = dateOfCreation;
		this.isVegetarian = isVegetarian;
		this.noOfPeople = noOfPeople;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<IngredientDto> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<IngredientDto> ingredients) {
		this.ingredients = ingredients;
	}

	public LocalDateTime getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(LocalDateTime dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public boolean getIsVegetarian() {
		return isVegetarian;
	}

	public void setIsVegetarian(boolean isVegetarian) {
		this.isVegetarian = isVegetarian;
	}

	public int getNoOfPeople() {
		return noOfPeople;
	}

	public void setNoOfPeople(int noOfPeople) {
		this.noOfPeople = noOfPeople;
	}

	@Override
	public String toString() {
		return "RecipeDto [name=" + name + ", ingredients=" + ingredients + ", dateOfCreation=" + dateOfCreation
				+ ", isVegetarian=" + isVegetarian + ", noOfPeople=" + noOfPeople + "]";
	}

}
