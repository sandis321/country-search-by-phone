package com.countrysearch.entity;

import org.hibernate.validator.constraints.NotBlank;

public class SearchCriteria {

	@NotBlank(message = "phonenumber can't be empty!")
	String phonenumber;

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
}