package com.abn.recipe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abn.recipe.model.Recipe;

@Repository
public interface RecipeBookRepository extends JpaRepository<Recipe, Long> {
	List<Recipe> deleteByName(String name);

	Optional<Recipe> findByNameIgnoreCase(String name);
}
