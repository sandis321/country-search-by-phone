package com.countrysearch;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.countrysearch.controller.IndexController;
import com.countrysearch.dao.PhoneDao;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
public class IndexControllerIntegrationTest {

	@Mock
	private PhoneDao phoneDao;

	@InjectMocks
	private IndexController indexController;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
	}

	@Test
	public void testIndexDataLoadSucces() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("key", "val");
		when(phoneDao.getPhoneNumberMap()).thenReturn(map);
		mockMvc.perform(get("/").accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("index"));
	}

	@Test
	public void testIndexDataLoadError() throws Exception {
		when(phoneDao.getPhoneNumberMap()).thenReturn(null);
		mockMvc.perform(get("/").accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("errorPage"));
	}

}