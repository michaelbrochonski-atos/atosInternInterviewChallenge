package com.atos.tutorial.at.steps;

import com.atos.tutorial.at.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.atos.tutorial.at.context.TestContext;

public abstract class AbstractSteps {
	
	@Autowired
	protected TestContext context;

	@Autowired
	protected JsonUtil jsonUtil;
	
	protected Logger LOGGER = Logger.getLogger(this.getClass());
}
