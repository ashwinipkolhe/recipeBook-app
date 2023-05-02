package com.abn.recipe.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
*/
@Entity
@Table(name = "recipe")
public class Recipe {

	@Id
	@GeneratedValue
	Long id;

	@Column(name = "name")
	String name;

	@Column(name = "dateOfCreation")
	LocalDateTime dateOfCreation;

	@Column(name = "isVegetarian")
	boolean isVegetarian;

	@Column(name = "noOfPeople")
	int noOfPeople;

	@Column(name = "ingredients")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "recipe_id")
	List<Ingredient> ingredients = new ArrayList<>();

	@Column(name = "cookingInstructions")
	String cookingInstructions;

	public Recipe() {
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

	public int getNoOfPeople() {
		return noOfPeople;
	}

	public void setNoOfPeople(int noOfPeople) {
		this.noOfPeople = noOfPeople;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public String getCookingInstructions() {
		return cookingInstructions;
	}

	public void setCookingInstructions(String cookingInstructions) {
		this.cookingInstructions = cookingInstructions;
	}

	public Recipe(Long id, String name, LocalDateTime dateOfCreation, boolean isVegetarian, int noOfPeople,
			List<Ingredient> ingredients, String cookingInstructions) {
		super();
		this.id = id;
		this.name = name;
		this.dateOfCreation = dateOfCreation;
		this.isVegetarian = isVegetarian;
		this.noOfPeople = noOfPeople;
		this.ingredients = ingredients;
		this.cookingInstructions = cookingInstructions;
	}

}