package it.vrad.motivational.telegram.bot.infrastructure.http.interceptor;

import it.vrad.motivational.telegram.bot.config.properties.LogProperties;
import it.vrad.motivational.telegram.bot.shared.util.CommonUtility;
import it.vrad.motivational.telegram.bot.shared.util.StringUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Interceptor for logging HTTP requests and responses made by RestTemplate.
 */
@Slf4j
@Component
public class LogInterceptor implements ClientHttpRequestInterceptor {

    private final LogProperties logProperties;

    public LogInterceptor(LogProperties logProperties) {
        this.logProperties = logProperties;
    }

    /**
     * Logs the outgoing HTTP request and the incoming HTTP response.
     * Logs at error level if the response status is 4xx or 5xx, otherwise at info level.
     *
     * @param request   the HTTP request
     * @param body      the request body
     * @param execution the request execution
     * @return the HTTP response
     * @throws IOException if an I/O error occurs
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        // Log the outgoing request
        log.info(getLogMessageRequest(request, body));

        // Execute the request and capture the response
        ClientHttpResponse response = execution.execute(request, body);

        // Prepare the response log message
        String logMessageResponse = getLogMessageResponse(request, response);
        HttpStatusCode httpStatus = response.getStatusCode();

        // Log at error level for 4xx/5xx, info otherwise
        if (httpStatus.is4xxClientError() || httpStatus.is5xxServerError()) {
            log.error(logMessageResponse);
        } else {
            log.info(logMessageResponse);
        }

        return response;
    }

    /**
     * Builds the log message for the outgoing HTTP request.
     */
    private String getLogMessageRequest(HttpRequest request, byte[] body) {
        // Shrink the request body if it exceeds the max length
        byte[] resizedBody = CommonUtility.shrinkArray(body, logProperties.getMaxRequestLength());

        return "\n--- HTTP Request ---\n" +
               "Request URI: " + request.getURI() + "\n" +
               "Request Method: " + request.getMethod() + "\n" +
               "Request Headers: " + request.getHeaders() + "\n" +
               "Request Body: " + StringUtility.newStringUTF8(resizedBody) +
               "\n------------------------------";
    }

    /**
     * Builds the log message for the incoming HTTP response.
     */
    private String getLogMessageResponse(HttpRequest request, ClientHttpResponse response) throws IOException {
        int status = response.getStatusCode().value();

        // Shrink the response body if it exceeds the max length
        byte[] resizedBody = CommonUtility.shrinkArray(
                response.getBody().readAllBytes(),
                logProperties.getMaxResponseLength()
        );
        String body = StringUtility.newStringUTF8(resizedBody);

        return "\n--- HTTP Response ---\n" +
               "Request URI: " + request.getURI() + "\n" +
               "Response Status Code: " + status + "\n" +
               "Response Status Text: " + response.getStatusText() + "\n" +
               "Response Headers: " + response.getHeaders() + "\n" +
               "Response Body: " + StringEscapeUtils.unescapeJava(body) +
               "\n------------------------------";
    }

}
