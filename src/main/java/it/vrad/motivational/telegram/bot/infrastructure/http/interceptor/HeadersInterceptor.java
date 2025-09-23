package it.vrad.motivational.telegram.bot.infrastructure.http.interceptor;

import it.vrad.motivational.telegram.bot.infrastructure.logging.constants.MdcConstants;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Interceptor for adding custom headers (such as request ID) to outgoing HTTP requests.
 */
public class HeadersInterceptor implements ClientHttpRequestInterceptor {

    /**
     * Intercepts the given HTTP request to add custom headers before execution.
     *
     * @param request   the HTTP request
     * @param body      the request body
     * @param execution the request execution
     * @return the HTTP response
     * @throws IOException if an I/O error occurs
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();

        // Retrieve the request ID from MDC and add it to the headers
        String requestId = MDC.get(MdcConstants.REQUEST_ID);
        headers.add(MdcConstants.REQUEST_ID_HEADER_NAME, requestId);

        return execution.execute(request, body);
    }
}
