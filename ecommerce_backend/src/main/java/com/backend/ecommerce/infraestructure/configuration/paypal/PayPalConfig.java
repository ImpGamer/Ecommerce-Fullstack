package com.backend.ecommerce.infraestructure.configuration.paypal;

import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalConfig {
    @Value("${paypal.client.id}")
    private String clientId;
    @Value("${paypal.client.secret}")
    private String secretKey;
    @Value("${paypal.mode}")
    private String mode;

    @Bean //Lo marcamos como bean para que se ejecute al iniciar la aplicacion
    public APIContext apiContext () {
        return new APIContext(clientId,secretKey,mode);
    }
}
