package com.countrysearch.dao;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class PhoneDao {

	private static final Logger log = LoggerFactory.getLogger(PhoneDao.class);

	private Map<String, String> phoneCodeMap;
	private static final String RESOURCE_URL = "https://query.wikidata.org/bigdata/namespace/wdq/sparql?query=SELECT%20%3Fcc%20%3FitemLabel%0AWHERE%20%7B%0A%20%20%3Fitem%20wdt%3AP474%20%3Fcc%20.%0A%20%20%3Fitem%20wdt%3AP297%20%20%3Falpha2%20.%0A%20%20SERVICE%20wikibase%3Alabel%20%7B%20bd%3AserviceParam%20wikibase%3Alanguage%20%22en%2Cen%22%20%20%7D%20%20%20%20%0A%7D%0Aorder%20by%20%3Fcc%20&format=json";
	private static final String VALUE = "value";

	public Map<String, String> getPhoneNumberMap() {
		return phoneCodeMap;
	}

	public void loadData(Map<String, String> phoneCodeMap) {
		this.phoneCodeMap = phoneCodeMap;
	}

	public void initData() {
		try {
			JSONObject obj = new JSONObject(
					new RestTemplate().getForObject(new URL(RESOURCE_URL).toURI(), String.class));
			obj = obj.getJSONObject("results");
			JSONArray geodata = obj.getJSONArray("bindings");
			this.phoneCodeMap = new HashMap<>();
			geodata.forEach(e -> {
				JSONObject row = (JSONObject) e;
				phoneCodeMap.put((String) row.getJSONObject("cc").get(VALUE),
						(String) row.getJSONObject("itemLabel").get(VALUE));
			});
		} catch (Exception e) {
			log.error("phoneDao data load error", e);
		}
	}
}
