package com.countrysearch;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetCountryByPhoneNoFieldIsEmpty() throws Exception {
		mockMvc.perform(post("/api/search").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("msg").value("phonenumber can't be empty!"));
	}

	@Test
	public void testGetCountryByPhoneNoIsInvalidPhoneNo() throws Exception {
		mockMvc.perform(
				post("/api/search").contentType(MediaType.APPLICATION_JSON).content("{\"phonenumber\":\"1212\"}"))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("msg").value("Invalid phone number!"));
	}

	@Test
	public void testGetCountryByPhoneNoIsValid() throws Exception {
		mockMvc.perform(post("/api/search").contentType(MediaType.APPLICATION_JSON)
				.content("{\"phonenumber\":\"+37126123000\"}")).andExpect(status().isOk())
				.andExpect(jsonPath("msg").value("success")).andExpect(jsonPath("result").value("Latvia"));
		mockMvc.perform(post("/api/search").contentType(MediaType.APPLICATION_JSON)
				.content("{\"phonenumber\":\"22826123000\"}")).andExpect(status().isOk())
				.andExpect(jsonPath("msg").value("success")).andExpect(jsonPath("result").value("Togo"));
		mockMvc.perform(post("/api/search").contentType(MediaType.APPLICATION_JSON)
				.content("{\"phonenumber\":\"+2512612 300 0\"}")).andExpect(status().isOk())
				.andExpect(jsonPath("msg").value("success")).andExpect(jsonPath("result").value("Ethiopia"));
	}

}