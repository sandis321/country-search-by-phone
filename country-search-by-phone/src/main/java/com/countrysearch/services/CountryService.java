package com.countrysearch.services;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class CountryService {

	private static final String REPLACE_PATTERN = "[\\s+\\+\\-]";
	private static final String DIGIT_PATTERN = "\\d{11}";
	private static final String PLUSS_SIGNE = "+";

	public boolean isValidPhoneNumbr(String phoneNumber) {
		Pattern pattern = Pattern.compile(DIGIT_PATTERN);
		Matcher matcher = pattern.matcher(phoneNumber.replaceAll(REPLACE_PATTERN, ""));
		return matcher.matches() ? true : false;
	}

	public String getCountryByPhoneNumber(String phoneNumber, Map<String, String> phoneCodeMap) {
		String resultingPhoneNoString = PLUSS_SIGNE + phoneNumber.replaceAll(REPLACE_PATTERN, "");
		String country = null;
		int i = 5;
		while (0 < i) {
			country = phoneCodeMap.get(resultingPhoneNoString.substring(0, i));
			if (country != null) {
				break;
			}
			i--;
		}
		return country;
	}

}
