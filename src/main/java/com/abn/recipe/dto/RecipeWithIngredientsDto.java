package com.abn.recipe.dto;

import java.time.LocalDateTime;
import java.util.List;

public class RecipeWithIngredientsDto {
	Long id;
	String name;
	private List<String> list;
	int noOfPeople;
	String cookingInstructions;
	LocalDateTime dateOfCreation;
	boolean isVegetarian;

	public RecipeWithIngredientsDto(Long id, String name, List<String> list, int noOfPeople, String cookingInstructions,
			LocalDateTime dateOfCreation, boolean isVegetarian) {
		super();
		this.id = id;
		this.name = name;
		this.list = list;
		this.noOfPeople = noOfPeople;
		this.cookingInstructions = cookingInstructions;
		this.dateOfCreation = dateOfCreation;
		this.isVegetarian = isVegetarian;
	}

	public RecipeWithIngredientsDto() {
	}

	public int getNoOfPeople() {
		return noOfPeople;
	}

	public void setNoOfPeople(int noOfPeople) {
		this.noOfPeople = noOfPeople;
	}

	public String getCookingInstructions() {
		return cookingInstructions;
	}

	public void setCookingInstructions(String cookingInstructions) {
		this.cookingInstructions = cookingInstructions;
	}

	public LocalDateTime getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(LocalDateTime dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public boolean isVegetarian() {
		return isVegetarian;
	}

	public void setVegetarian(boolean isVegetarian) {
		this.isVegetarian = isVegetarian;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> ingredientNames) {
		this.list = ingredientNames;
	}

}
