package it.vrad.motivational.telegram.bot.infrastructure.web.util;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebUtility {

    /**
     * Reads the response body from the given {@link ContentCachingResponseWrapper}.
     * <p>
     * If the response's input stream has already been read and is empty, this method returns the cached content
     * as a byte array. Otherwise, it reads the content directly from the input stream.
     *
     * @param response the {@code ContentCachingResponseWrapper} to read the body from
     * @return the response body as a byte array; returns an empty array if the body is empty
     * @throws IOException if an I/O error occurs while reading the response body
     */
    public static byte[] readBodyResponse(ContentCachingResponseWrapper response) throws IOException {
        byte[] body = response.getContentInputStream().readAllBytes();

        // If input stream was already read and is empty, return cached content
        if (body.length == 0) {
            return response.getContentAsByteArray();
        }

        return body;
    }

    /**
     * Wraps the request in a {@link ContentCachingRequestWrapper} if not already wrapped.
     */
    public static ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
        return (request instanceof ContentCachingRequestWrapper)
                ? (ContentCachingRequestWrapper) request
                : new ContentCachingRequestWrapper(request);
    }

    /**
     * Wraps the response in a {@link ContentCachingResponseWrapper} if not already wrapped.
     */
    public static ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
        return (response instanceof ContentCachingResponseWrapper)
                ? (ContentCachingResponseWrapper) response
                : new ContentCachingResponseWrapper(response);
    }
}
