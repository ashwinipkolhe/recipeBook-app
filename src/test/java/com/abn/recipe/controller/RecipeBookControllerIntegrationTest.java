package com.abn.recipe.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.abn.recipe.dto.RecipeDto;
import com.abn.recipe.service.RecipeBookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RecipeBookController.class)
public class RecipeBookControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private RecipeBookService recipeBookService;

	@Test
	@WithMockUser(username = "user", password = "password", roles = "ADMIN")
	void createRecipe_Success() throws Exception {
		RecipeDto recipeDto = new RecipeDto();
		recipeDto.setName("test recipe");

		mockMvc.perform(
				post("/create").contentType("application/json").content(objectMapper.writeValueAsString(recipeDto)))
				.andExpect(status().isOk());

	}

	@Test
	@WithMockUser(username = "user", password = "password", roles = "ADMIN")
	void deleteRecipe_success() throws Exception {
		Long id = 1l;
		mockMvc.perform(delete("/delete").param("id", id.toString())).andExpect(status().isOk());
	}

	/*
	 * @Test
	 * 
	 * @WithMockUser(username = "user", password = "password", roles = "ADMIN") void
	 * createRecipe_alreadyExists() throws Exception { RecipeDto recipeDto = new
	 * RecipeDto(); recipeDto.setName("test recipe");
	 * doThrow(RecipeAlreadyExistsException.class).when(recipeBookService).
	 * createRecipe(any(RecipeDto.class));
	 * 
	 * mockMvc.perform(post("/create").contentType("application/json")).andExpect(
	 * status().is5xxServerError())
	 * .andExpect(jsonPath("$.message").value("Recipe with this name already exists"
	 * ));
	 * 
	 * }
	 */
}
