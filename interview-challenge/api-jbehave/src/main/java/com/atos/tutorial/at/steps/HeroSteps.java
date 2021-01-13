package com.wipro.tutorial.at.steps;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.wipro.tutorial.at.util.RestUtil;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class HeroSteps extends AbstractSteps {

    @Value("${app.base.url}")
    private String appUrl;

    @Given("I am on a hero store with model '$template'")
    public void givenAHero(@Named("template") String template) {
        DocumentContext json = jsonUtil.loadJson(template);
        context.saveResource("payload", json);
    }

    @Given("I am on a hero store")
    public void givenAHero() {
    }

    @When("I request the list of heroes")
    public void requestHeroes() {
        String result = RestUtil.sendGet(appUrl);
        DocumentContext documentContext = JsonPath.parse(result);
        context.saveResource("result", documentContext);
    }

    @When("I set the hero's name to '$name'")
    public void setHeroName(@Named("name") String name) {
        DocumentContext json = (DocumentContext)context.getResource("payload");
        json.set("name", name);
    }

    @When("I set the hero's superpower to '$superpower'")
    public void setHeroSuperpower(@Named("superpower") String superpower) {
        DocumentContext json = (DocumentContext)context.getResource("payload");
        json.set("superpower", superpower);
    }

    @When("I add the hero to store")
    public void setHeroStore() {
        DocumentContext json = (DocumentContext)context.getResource("payload");
        LOGGER.info("JSON: " + json.jsonString());
        String result = RestUtil.sendPost(appUrl, json.jsonString());
        context.saveResource("result", result);
    }

    @Then("I should not see any error")
    public void thenNoErrors() {
        String result = (String) context.getResource("result");
        LOGGER.info("REST_RESULT: " + result);
    }

    @Then("I should receive a list of heroes")
    public void thenReceiveHeroes() {
        DocumentContext result = (DocumentContext) context.getResource("result");
        LOGGER.info("Result: " + result.jsonString());
    }
}
