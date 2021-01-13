package com.wipro.tutorial.at.util;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class RestUtil {
    private static Logger LOGGER = Logger.getLogger(RestUtil.class);
    private static SimpleClientHttpRequestFactory REQ_FACTORY = new SimpleClientHttpRequestFactory();

    public static void configureProxy(Environment env) {
        if(! Boolean.valueOf(env.getProperty("proxy.enabled"))) {
            return;
        }

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
                env.getProperty("proxy.host"),
                Integer.parseInt(env.getProperty("proxy.port"))));
        REQ_FACTORY.setProxy(proxy);
    }


    public static String sendRequest(HttpMethod method, String url, HttpEntity httpEntity) {
        RestTemplate template = new RestTemplate(REQ_FACTORY);

        CloseableHttpClient httpClient = HttpClients.custom().build();
        template.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        template.getMessageConverters().add(0, new StringHttpMessageConverter());
        HttpEntity<String> response = template.exchange(url, method, httpEntity, String.class);
        String responseBody = response.getBody();
        LOGGER.info("RES[" + url + "]: " + responseBody);
        return responseBody;
    }

    public static String sendGet(String url) {
        LOGGER.info("REQ[" + url + "]: GET");
        return sendRequest(HttpMethod.GET, url, null);
    }

    public static String sendPost(String url, String payload) {
        LOGGER.info("REQ[" + url + "]: " + payload);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(payload, headers);
        return sendRequest(HttpMethod.POST, url, request);
    }
}
