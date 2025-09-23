package it.vrad.motivational.telegram.bot.integration.util;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.ContentPattern;
import com.github.tomakehurst.wiremock.matching.MultipartValuePatternBuilder;
import it.vrad.motivational.telegram.bot.infrastructure.testutil.FileTestUtility;
import it.vrad.motivational.telegram.bot.infrastructure.testutil.JsonTestUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.anyRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.binaryEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.request;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

/**
 * Base class for integration tests that require HTTP server mocking with WireMock.
 * <p>
 * Provides utility methods for stubbing and verifying HTTP requests and responses,
 * including support for JSON and binary payloads, multipart requests, and request body matching.
 * <p>
 * All tests extending this class will have WireMock automatically configured on a random port.
 * <p>
 * <b>Usage:</b>
 * <ul>
 *   <li>Extend this class in your integration test classes.</li>
 *   <li>Use the provided protected methods to mock HTTP interactions and verify request counts.</li>
 * </ul>
 */
@Slf4j
@AutoConfigureWireMock(port = 0)
public abstract class BaseRestMockTest {

    @AfterEach
    protected void resetMock() {
        WireMock.reset();
    }

    /* -----------------------
       Public convenience API
       ----------------------- */

    /**
     * Mocks a successful JSON response for the given URL, HTTP method, request JSON path, and response JSON path.
     *
     * @param url              the endpoint URL
     * @param httpMethod       the HTTP method
     * @param requestJsonPath  the path to the request JSON resource
     * @param responseJsonPath the path to the response JSON resource
     */
    protected void mockSuccessfulJsonResponse(String url, HttpMethod httpMethod, String requestJsonPath, String responseJsonPath) {
        setupMockResponse(
                url, httpMethod,
                jsonBodyPattern(requestJsonPath),
                FileTestUtility.readResourceAsString(responseJsonPath),
                HttpStatus.OK
        );
    }

    /**
     * Mocks a successful JSON response for a multipart request.
     *
     * @param url              the endpoint URL
     * @param httpMethod       the HTTP method
     * @param multipartRequest the multipart request data
     * @param responseJsonPath the path to the response JSON resource
     */
    protected void mockSuccessfulJsonResponse(String url, HttpMethod httpMethod, MultiValueMap<String, Object> multipartRequest,
                                              String responseJsonPath) {
        setupMockMultipartResponse(
                url, httpMethod,
                toMultipartValuePatternBuilders(multipartRequest),
                FileTestUtility.readResourceAsString(responseJsonPath),
                HttpStatus.OK
        );
    }

    /**
     * Mocks a failed JSON response for the given URL, HTTP method, request JSON path, and response JSON path.
     *
     * @param url              the endpoint URL
     * @param httpMethod       the HTTP method
     * @param requestJsonPath  the path to the request JSON resource
     * @param responseJsonPath the path to the response JSON resource
     */
    protected void mockFailedJsonResponse(String url, HttpMethod httpMethod, String requestJsonPath, String responseJsonPath) {
        setupMockResponse(
                url, httpMethod,
                jsonBodyPattern(requestJsonPath),
                FileTestUtility.readResourceAsString(responseJsonPath),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    /**
     * Mocks a failed response for the given URL, HTTP method, and request JSON path.
     *
     * @param url             the endpoint URL
     * @param httpMethod      the HTTP method
     * @param requestJsonPath the path to the request JSON resource
     */
    protected void mockFailedResponse(String url, HttpMethod httpMethod, String requestJsonPath) {
        setupMockResponse(
                url, httpMethod,
                jsonBodyPattern(requestJsonPath),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    /**
     * Mocks a successful binary response for the given URL, HTTP method, request JSON path, and response binary path.
     *
     * @param url                the endpoint URL
     * @param httpMethod         the HTTP method
     * @param requestJsonPath    the path to the request JSON resource
     * @param responseBinaryPath the path to the response binary resource
     */
    protected void mockSuccessfulBinaryResponse(String url, HttpMethod httpMethod,
                                                String requestJsonPath, String responseBinaryPath) {
        setupMockResponse(
                url, httpMethod,
                jsonBodyPattern(requestJsonPath),
                FileTestUtility.readBytesFromResource(responseBinaryPath),
                HttpStatus.OK
        );
    }

    /**
     * Verifies that the specified number of HTTP requests were made to the given URL.
     *
     * @param count the expected number of requests
     * @param url   the endpoint URL
     */
    protected void verifyHttpRequestCount(int count, String url) {
        String path = removeHostAndPort(url);
        verify(count, anyRequestedFor(urlEqualTo(path)));
    }

    /* -----------------------
       Internal setup methods
       ----------------------- */

    private void setupMockResponse(String url, HttpMethod httpMethod,
                                   ContentPattern<?> requestBody,
                                   String responseBody, HttpStatus responseStatus) {
        stubFor(getMappingBuilderWithRequestBody(url, httpMethod, requestBody)
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)
                        .withStatus(responseStatus.value())
                ));
    }

    private void setupMockResponse(String url, HttpMethod httpMethod,
                                   ContentPattern<?> requestBody,
                                   byte[] responseBody, HttpStatus responseStatus) {
        stubFor(getMappingBuilderWithRequestBody(url, httpMethod, requestBody)
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/octet-stream")
                        .withBody(responseBody)
                        .withStatus(responseStatus.value())
                ));
    }

    private void setupMockResponse(String url, HttpMethod httpMethod,
                                   ContentPattern<?> requestBody, HttpStatus responseStatus) {
        stubFor(getMappingBuilderWithRequestBody(url, httpMethod, requestBody)
                .willReturn(aResponse()
                        .withStatus(responseStatus.value())
                ));
    }

    private MappingBuilder getMappingBuilderWithRequestBody(String url, HttpMethod httpMethod, ContentPattern<?> requestBody) {
        MappingBuilder builder = request(httpMethod.name(), urlEqualTo(removeHostAndPort(url)));

        if (Objects.nonNull(requestBody)) {
            return builder.withRequestBody(requestBody);
        }

        return builder;
    }

    private void setupMockMultipartResponse(String url, HttpMethod httpMethod,
                                            List<MultipartValuePatternBuilder> multipartMatchers,
                                            String responseBody, HttpStatus responseStatus) {
        MappingBuilder builder = request(httpMethod.name(), urlEqualTo(removeHostAndPort(url)));
        multipartMatchers.forEach(builder::withMultipartRequestBody);

        stubFor(builder
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)
                        .withStatus(responseStatus.value())
                ));
    }

    /* -----------------------
       Utilities
       ----------------------- */

    private String removeHostAndPort(String url) {
        try {
            URI uri = new URI(url);
            String path = uri.getPath();
            String query = uri.getQuery();
            return query == null ? path : path + "?" + query;
        } catch (URISyntaxException e) {
            log.warn("Invalid URL passed to removeHostAndPort: {}", url, e);
            return url;
        }
    }

    private ContentPattern<String> jsonBodyPattern(String requestPath) {
        return StringUtils.isNotEmpty(requestPath)
                ? equalToJson(FileTestUtility.readResourceAsString(requestPath))
                : null;
    }

    private List<MultipartValuePatternBuilder> toMultipartValuePatternBuilders(
            MultiValueMap<String, Object> multiValueMap) {

        return multiValueMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .map(part -> buildMultipartValue(entry.getKey(), part)))
                .toList();
    }

    private static MultipartValuePatternBuilder buildMultipartValue(String key, Object part) {
        MultipartValuePatternBuilder builder = new MultipartValuePatternBuilder().withName(key);

        return switch (part) {
            case Resource resource -> builder.withBody(
                    binaryEqualTo(FileTestUtility.readBytesFromResource(resource))
            );
            case String string -> builder.withBody(equalTo(string));
            case Number number -> builder.withBody(equalTo(number.toString()));
            default -> builder.withBody(equalToJson(JsonTestUtility.toJson(part)));
        };
    }
}
