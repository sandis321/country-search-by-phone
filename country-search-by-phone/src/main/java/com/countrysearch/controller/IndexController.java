package com.countrysearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import com.countrysearch.dao.PhoneDao;

@Controller
public class IndexController {

	@Autowired
	private PhoneDao phoneDao;

	@GetMapping("/")
	public String index() {
		return !CollectionUtils.isEmpty(phoneDao.getPhoneNumberMap()) ? "index" : "errorPage";
	}

}