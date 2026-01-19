package Ecommerce_Completo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient viacepWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://viacep.com.br")
                .build();
    }
}
