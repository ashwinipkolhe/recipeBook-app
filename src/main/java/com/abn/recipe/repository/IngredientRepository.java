package com.abn.recipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abn.recipe.dto.IngredientDto;
import com.abn.recipe.model.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

	List<Ingredient> findByRecipeId(Long id);

	void deleteByRecipeId(Long id);

	void save(List<Ingredient> list);

	
}
