package org.example.cartapi.service;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class CorreiosService extends WebServiceGatewaySupport {
    public Object getPrecoPrazo(String url, Object request){
        return getWebServiceTemplate().marshalSendAndReceive(url, request);
    }
}
