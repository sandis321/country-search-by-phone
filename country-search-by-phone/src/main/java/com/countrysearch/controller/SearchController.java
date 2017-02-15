package com.countrysearch.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.countrysearch.dao.PhoneDao;
import com.countrysearch.entity.ResponseBody;
import com.countrysearch.entity.SearchCriteria;
import com.countrysearch.services.CountryService;

@RestController
public class SearchController {

	@Autowired
	private CountryService countryService;
	@Autowired
	private PhoneDao phoneDao;

	private static final String INVALID_PHONE_NO_MSG = "Invalid phone number!";

	@PostMapping("/api/search")
	public ResponseEntity<ResponseBody> getCountryByPhoneNo(@Valid @RequestBody SearchCriteria searchResult,
			Errors errors) {

		ResponseBody result = new ResponseBody();

		if (errors.hasErrors()) {
			result.setMsg(
					errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
			return ResponseEntity.badRequest().body(result);
		} else if (!countryService.isValidPhoneNumbr(searchResult.getPhonenumber())) {
			result.setMsg(INVALID_PHONE_NO_MSG);
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(getPhoneNumberSearchResult(searchResult));

	}

	private ResponseBody getPhoneNumberSearchResult(SearchCriteria searchResult) {
		String country = countryService.getCountryByPhoneNumber(searchResult.getPhonenumber(),
				phoneDao.getPhoneNumberMap());
		return country == null ? new ResponseBody(INVALID_PHONE_NO_MSG, null) : new ResponseBody("success", country);
	}

}
