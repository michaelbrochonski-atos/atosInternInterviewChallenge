package com.atos.tutorial.at.context;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class TestContext {

	private Map<String, Object> resourceMap;
	
	public TestContext() {
		resourceMap = new HashMap<>();
	}
	
	public void saveResource(String key, Object object) {
		resourceMap.put(key, object);
	}
	
	public Object getResource(String key) {
		return resourceMap.get(key);
	}
	
	public void clearContext() {
		resourceMap.clear();
	}
}
