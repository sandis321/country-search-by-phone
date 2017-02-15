package com.countrysearch;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.countrysearch.controller.SearchController;
import com.countrysearch.dao.PhoneDao;
import com.countrysearch.services.CountryService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
public class SearchControllerIntegrationTest {

	@Mock
	private CountryService countryService;
	@Mock
	private PhoneDao phoneDao;

	@InjectMocks
	private SearchController searchController;

	@Configuration
	public static class Config {
		@Bean
		public CountryService createCountryService() {
			return new CountryService();
		}
	}

	private MockMvc mockMvc;
	private Map<String, String> phoneNumberMap;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
		phoneNumberMap = new HashMap<>();
		phoneNumberMap.put("+371", "Latvia");
		phoneNumberMap.put("+370", "Lithuania");
		when(phoneDao.getPhoneNumberMap()).thenReturn(phoneNumberMap);
	}

	@Test
	public void testGetCountryByPhoneNoFieldIsEmpty() throws Exception {
		mockMvc.perform(post("/api/search").contentType(MediaType.APPLICATION_JSON).content("{}")).andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().json("{\"msg\": \"phonenumber can't be empty!\",\"result\": null}"));
	}

	@Test
	public void testGetCountryByPhoneNoIsInvalidPhoneNo() throws Exception {
		mockMvc.perform(
				post("/api/search").contentType(MediaType.APPLICATION_JSON).content("{\"phonenumber\":\"1212\"}"))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(content().json("{\"msg\": \"Invalid phone number!\",\"result\": null}"));
	}

	@Test
	public void testGetCountryByPhoneNoIsValid() throws Exception {
		when(countryService.isValidPhoneNumbr(any())).thenReturn(true);
		when(countryService.getCountryByPhoneNumber(any(), any())).thenReturn(phoneNumberMap.get("+371"));
		mockMvc.perform(post("/api/search").contentType(MediaType.APPLICATION_JSON)
				.content("{\"phonenumber\":\"+37126123000\"}")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json("{\"msg\": \"success\",\"result\": \"Latvia\"}"));
	}

}