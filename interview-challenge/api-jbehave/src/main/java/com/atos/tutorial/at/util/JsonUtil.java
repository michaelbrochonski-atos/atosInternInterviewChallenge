package com.wipro.tutorial.at.util;

import com.google.common.io.Resources;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JsonProvider;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Component
public class JsonUtil {

    private static Logger LOGGER = Logger.getLogger(JsonUtil.class);
    private static Configuration JSON_CONFIG = Configuration.defaultConfiguration();

    @Value("templates/${environment}/")
    private String templatePrefix;

    public DocumentContext loadTemplate(String template) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = null;
        try {
            URL url = Resources.getResource(templatePrefix + template + ".json");
            String text = Resources.toString(url, StandardCharsets.UTF_8);
            return JsonPath.parse(text, JSON_CONFIG);
        } catch (IOException e) {
            LOGGER.error("Failed to load template: " + template, e);
            throw new IllegalArgumentException("Can not load template: " + template, e);
        }
    }

    public DocumentContext loadJson(String input) {
        if(input.matches("^loadTemplate[(].*[)]$")) {
            String template = input.split("loadTemplate[(]|[)]$")[1];
            return loadTemplate(template);
        }

        return JsonPath.parse(input, JSON_CONFIG);
    }
}
