package com.countrysearch.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryServiceTest {

	@Autowired
	private CountryService countryService;

	static List<String> phoneNoList = Arrays.asList("+12421231231", "+21821231231", "+11211312441", "+11211ss");

	@Test
	public void testGetCountryByPhoneNumber() {
		String countryBahamas = "The Bahamas";
		String countryLibya = "Libya";
		Map<String, String> phoneCodeMap = new HashMap<>();
		phoneCodeMap.put("+1242", countryBahamas);
		phoneCodeMap.put("+218", countryLibya);
		String countryResult1 = countryService.getCountryByPhoneNumber(phoneNoList.get(0), phoneCodeMap);
		String countryResult2 = countryService.getCountryByPhoneNumber(phoneNoList.get(1), phoneCodeMap);
		String countryResult3 = countryService.getCountryByPhoneNumber(phoneNoList.get(2), phoneCodeMap);
		assertEquals(countryResult1, countryBahamas);
		assertEquals(countryResult2, countryLibya);
		assertEquals(countryResult3, null);
	}

	@Test
	public void testIsValidPhoneNumbr() {
		assertTrue(countryService.isValidPhoneNumbr(phoneNoList.get(0)));
		assertFalse(countryService.isValidPhoneNumbr(phoneNoList.get(3)));
	}
}
