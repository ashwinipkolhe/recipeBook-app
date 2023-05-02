package com.abn.recipe.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ingredients")
public class Ingredient {

	@Id
	@GeneratedValue
	Long id;

	public Ingredient(Long id, Recipe recipe, IngredientMaster ingredientMasterId) {
		super();
		this.id = id;
		this.recipe = recipe;
		this.ingredientMasterId = ingredientMasterId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ingredient_master_id")
	private IngredientMaster ingredientMasterId;

	public IngredientMaster getIngredientMasterId() {
		return ingredientMasterId;
	}

	public void setIngredientMasterId(IngredientMaster ingredientMasterId) {
		this.ingredientMasterId = ingredientMasterId;
	}

	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", recipe=" + recipe + ", ingredientMasterId=" + ingredientMasterId + "]";
	}

	public Ingredient() {

	}

	public Long getIngredientId() {
		return id;
	}

	public void setIngredientId(Long id) {
		this.id = id;
	}

}
