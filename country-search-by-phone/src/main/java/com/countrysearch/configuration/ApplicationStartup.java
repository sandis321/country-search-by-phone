package com.countrysearch.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.countrysearch.dao.PhoneDao;

@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private PhoneDao phoneDao;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		phoneDao.initData();
	}

}
