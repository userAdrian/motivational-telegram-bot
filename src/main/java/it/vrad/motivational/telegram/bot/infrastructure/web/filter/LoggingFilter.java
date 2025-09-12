package it.vrad.motivational.telegram.bot.infrastructure.web.filter;

import it.vrad.motivational.telegram.bot.config.properties.LogProperties;
import it.vrad.motivational.telegram.bot.infrastructure.web.constants.FilterConstants;
import it.vrad.motivational.telegram.bot.infrastructure.util.CommonUtility;
import it.vrad.motivational.telegram.bot.infrastructure.util.StringUtility;
import jakarta.servlet.FilterChain;
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
import java.util.stream.Collectors;

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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        // Wrap the request and response to allow multiple reads of their content
        ContentCachingRequestWrapper wrappedRequest = wrapRequest(request);
        ContentCachingResponseWrapper wrappedResponse = wrapResponse(response);

        try {
            // Log the incoming HTTP request
            log.info(buildRequestLog(wrappedRequest));

            // Continue the filter chain
            filterChain.doFilter(wrappedRequest, wrappedResponse);

            // Log the outgoing HTTP response
            logResponse(wrappedResponse, wrappedRequest);
        } catch (Exception exception) {
            log.error("Error occurred during logging request and response", exception);
        }
    }

    /**
     * Wraps the request in a {@link ContentCachingRequestWrapper} if not already wrapped.
     */
    private ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
        return (request instanceof ContentCachingRequestWrapper)
                ? (ContentCachingRequestWrapper) request
                : new ContentCachingRequestWrapper(request);
    }

    /**
     * Wraps the response in a {@link ContentCachingResponseWrapper} if not already wrapped.
     */
    private ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
        return (response instanceof ContentCachingResponseWrapper)
                ? (ContentCachingResponseWrapper) response
                : new ContentCachingResponseWrapper(response);
    }

    /**
     * Logs the HTTP response, using error level for status 400/500 and info otherwise.
     * Copies the response body back to the output stream after logging.
     */
    private void logResponse(ContentCachingResponseWrapper response, ContentCachingRequestWrapper request) throws IOException {
        String logMessage = buildResponseLog(request, response);
        int responseStatus = response.getStatus();

        if (responseStatus == 400 || responseStatus == 500) {
            log.error(logMessage);
        } else {
            log.info(logMessage);
        }

        // Ensure the response body is written out after logging
        response.copyBodyToResponse();
    }

    /**
     * Builds a log message for the HTTP request, including URI, method, and headers.
     */
    private String buildRequestLog(ContentCachingRequestWrapper request) {
        return "\n--- HTTP Request ---\n" +
               "Request URI: " + request.getRequestURI() + "\n" +
               "Request Method: " + request.getMethod() + "\n" +
               "Request Headers:\n" + getHeaders(request) + "\n" +
               "------------------------------";
    }

    /**
     * Builds a log message for the HTTP response, including status, headers, and truncated bodies.
     */
    private String buildResponseLog(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) throws IOException {
        // Truncate request and response bodies according to logProperties
        byte[] resizedRequestBody = CommonUtility.shrinkArray(
                request.getContentAsByteArray(),
                logProperties.getMaxRequestLength()
        );

        byte[] resizedResponseBody = CommonUtility.shrinkArray(
                response.getContentInputStream().readAllBytes(),
                logProperties.getMaxResponseLength()
        );

        return "\n--- HTTP Response ---\n" +
               "Request URI: " + request.getRequestURI() + "\n" +
               "Request Body: " + StringUtility.newStringUTF8(resizedRequestBody) + "\n" +
               "Response Status Code: " + response.getStatus() + "\n" +
               "Response Headers: " + response.getHeaderNames() + "\n" +
               "Response Body: " + StringUtility.newStringUTF8(resizedResponseBody) + "\n" +
               "------------------------------";
    }

    /**
     * Returns a formatted string of all request headers.
     */
    private String getHeaders(ContentCachingRequestWrapper request) {
        Enumeration<String> headerNames = request.getHeaderNames();

        return Collections.list(headerNames).stream()
                .map(name -> name + ": " + request.getHeader(name))
                .collect(Collectors.joining("\n"));
    }

}