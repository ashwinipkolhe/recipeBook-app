package com.abn.recipe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abn.recipe.model.IngredientMaster;

@Repository
public interface IngredientMasterRepository extends JpaRepository<IngredientMaster, Long> {

	Optional<IngredientMaster> findByNameIgnoreCase(String name);

	
	List<IngredientMaster> getIdByNameIn(List<String> updatedNameList);

}
