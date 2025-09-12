package it.vrad.motivational.telegram.bot.infrastructure.web.filter;

import it.vrad.motivational.telegram.bot.infrastructure.web.constants.FilterConstants;
import it.vrad.motivational.telegram.bot.infrastructure.web.constants.MdcConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Servlet filter for managing the request ID in the MDC (Mapped Diagnostic Context).
 * Ensures each request is associated with a unique request ID for logging and tracing.
 */
@Component
@Order(FilterConstants.REQUEST_ID_FILTER_ORDER)
public class RequestIdFilter extends OncePerRequestFilter {

    /**
     * Sets the request ID in the MDC for the duration of the request.
     *
     * @param request     the HTTP servlet request
     * @param response    the HTTP servlet response
     * @param filterChain the filter chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            // Put the request ID from the header into the MDC for logging/tracing
            MDC.put(MdcConstants.REQUEST_ID, getRequestID(request));
            filterChain.doFilter(request, response);
        } finally {
            // Remove the request ID from the MDC after the request is processed
            MDC.remove(MdcConstants.REQUEST_ID);
        }

    }

    /**
     * Retrieves the request ID from the HTTP header.
     *
     * @param request the HTTP servlet request
     * @return the request ID from the header, or null if not present
     */
    private String getRequestID(HttpServletRequest request) {
        return request.getHeader(MdcConstants.REQUEST_ID_HEADER_NAME);
    }
}
