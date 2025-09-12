package it.vrad.motivational.telegram.bot.infrastructure.http;

import it.vrad.motivational.telegram.bot.infrastructure.http.interceptor.HeadersInterceptor;
import it.vrad.motivational.telegram.bot.infrastructure.http.interceptor.LogInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Provides additional features for API clients
 */
@RequiredArgsConstructor
@Component
public class ApiClientFeatureProvider {
    private final LogInterceptor logInterceptor;

    /**
     * Returns a list of interceptors to be registered with the application's RestTemplate.
     *
     * @return list of {@link ClientHttpRequestInterceptor}
     */
    public List<ClientHttpRequestInterceptor> getRestTemplateInterceptors() {
        // Add custom headers and logging interceptors, order count!
        return Arrays.asList(new HeadersInterceptor(), logInterceptor);
    }
}
