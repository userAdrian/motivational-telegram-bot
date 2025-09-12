package it.vrad.motivational.telegram.bot.config;

import it.vrad.motivational.telegram.bot.infrastructure.http.ApiClientFeatureProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for setting up the application's api client beans.
 */
@Configuration
public class ApiClientConfig {

    /**
     * Configures and provides a {@link RestTemplate} bean with interceptors.
     *
     * @param provider the provider for RestTemplate interceptors
     * @return the configured {@link RestTemplate}
     */
    @Bean
    public RestTemplate restTemplate(ApiClientFeatureProvider provider) {
        // Use BufferingClientHttpRequestFactory to allow multiple reads of the response body
        RestTemplate restTemplate = new RestTemplate(
                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

        // Register custom interceptors
        restTemplate.setInterceptors(provider.getRestTemplateInterceptors());

        return restTemplate;
    }

}
