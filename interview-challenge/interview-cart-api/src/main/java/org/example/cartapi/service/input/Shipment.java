package org.example.cartapi.service.input;

import br.com.techzee.correios.ws.enumeration.CorreiosTipoServico;

public class Shipment {
    private CorreiosTipoServico serviceType;
    private String cep;

    public CorreiosTipoServico getServiceType() {
        return serviceType;
    }

    public void setServiceType(CorreiosTipoServico serviceType) {
        this.serviceType = serviceType;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
