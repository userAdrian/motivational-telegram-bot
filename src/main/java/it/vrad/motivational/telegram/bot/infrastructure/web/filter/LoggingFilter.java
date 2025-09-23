package it.vrad.motivational.telegram.bot.infrastructure.web.filter;

import it.vrad.motivational.telegram.bot.config.properties.LogProperties;
import it.vrad.motivational.telegram.bot.infrastructure.web.constants.FilterConstants;
import it.vrad.motivational.telegram.bot.shared.util.CommonUtility;
import it.vrad.motivational.telegram.bot.shared.util.StringUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static it.vrad.motivational.telegram.bot.infrastructure.web.util.WebUtility.readBodyResponse;
import static it.vrad.motivational.telegram.bot.infrastructure.web.util.WebUtility.wrapRequest;
import static it.vrad.motivational.telegram.bot.infrastructure.web.util.WebUtility.wrapResponse;

/**
 * Servlet filter for logging HTTP requests and responses.
 * Logs request URI, method, headers, and body, as well as response status, headers, and body.
 * Truncates request/response bodies according to {@link LogProperties} configuration.
 */
@Slf4j
@Component
@Order(FilterConstants.LOGGING_FILTER_ORDER)
public class LoggingFilter extends OncePerRequestFilter {
    private final LogProperties logProperties;

    public LoggingFilter(LogProperties logProperties) {
        this.logProperties = logProperties;
    }

    /**
     * Logs the HTTP request and response for each request.
     *
     * @param request     the HTTP servlet request
     * @param response    the HTTP servlet response
     * @param filterChain the filter chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Wrap the request and response to allow repeated reading of their content
        ContentCachingRequestWrapper wrappedRequest = wrapRequest(request);
        ContentCachingResponseWrapper wrappedResponse = wrapResponse(response);

        try {
            // Log the incoming HTTP request details
            log.info(buildRequestLog(wrappedRequest));

            // Proceed with the filter chain, allowing the request to be processed
            filterChain.doFilter(wrappedRequest, wrappedResponse);

            // Read the response body after the request has been processed
            byte[] responseBody = readBodyResponse(wrappedResponse);

            // Copy the cached response body to the actual response output stream
            // This ensures the response is sent to the client and headers are available
            wrappedResponse.copyBodyToResponse();

            // Log the outgoing HTTP response details (status, headers, body)
            logResponse(wrappedResponse, wrappedRequest, responseBody);
        } catch (Exception ex) {
            // Log any exception that occurs during request/response logging
            log.error("Error occurred during logging request and response", ex);
            throw ex;
        }
    }


    /**
     * Logs the HTTP response, using error level for status 400/500 and info otherwise.
     */
    private void logResponse(ContentCachingResponseWrapper response, ContentCachingRequestWrapper request,
                             byte[] responseBody) {
        String logMessage = buildResponseLog(response, request, responseBody);
        int responseStatus = response.getStatus();

        if (responseStatus == 400 || responseStatus == 500) {
            log.error(logMessage);
        } else {
            log.info(logMessage);
        }
    }

    /**
     * Builds a log message for the HTTP request, including URI, method, and headers.
     */
    private String buildRequestLog(ContentCachingRequestWrapper request) {
        return "\n--- HTTP Request ---\n" +
               "Request URI: " + request.getRequestURI() + "\n" +
               "Request Method: " + request.getMethod() + "\n" +
               "Request Headers: " + getHeaders(request) + "\n" +
               "------------------------------";
    }

    /**
     * Builds a log message for the HTTP response, including status, headers, and truncated bodies.
     */
    private String buildResponseLog(ContentCachingResponseWrapper response, ContentCachingRequestWrapper request,
                                    byte[] responseBody) {
        // Truncate request and response bodies according to logProperties
        byte[] resizedRequestBody = CommonUtility.shrinkArray(
                request.getContentAsByteArray(),
                logProperties.getMaxRequestLength()
        );

        byte[] resizedResponseBody = CommonUtility.shrinkArray(
                responseBody,
                logProperties.getMaxResponseLength()
        );

        return "\n--- HTTP Response ---\n" +
               "Request URI: " + request.getRequestURI() + "\n" +
               "Request Body: " + StringUtility.newStringUTF8(resizedRequestBody) + "\n" +
               "Response Status Code: " + response.getStatus() + "\n" +
               "Response Headers: " + getHeaders(response) + "\n" +
               "Response Body: " + StringUtility.newStringUTF8(resizedResponseBody) + "\n" +
               "------------------------------";
    }

    /**
     * Returns a formatted string of all request headers.
     */
    private String getHeaders(ContentCachingRequestWrapper request) {
        Enumeration<String> headerNames = request.getHeaderNames();

        return Collections.list(headerNames).stream()
                .map(name -> formatHeader(name, request.getHeader(name)))
                .collect(getJoiningHeaders());
    }

    /**
     * Returns a formatted string of all response headers.
     */
    private String getHeaders(ContentCachingResponseWrapper response) {
        return response.getHeaderNames().stream()
                .map(name -> formatHeader(name, response.getHeader(name)))
                .collect(getJoiningHeaders());
    }

    private static String formatHeader(String name, String value) {
        return name + ": " + value;
    }

    private static Collector<CharSequence, ?, String> getJoiningHeaders() {
        return Collectors.joining("\n\t", "\n\t", "");
    }

}