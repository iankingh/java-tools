package io.github.iankingh.javatools.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/** JSON and form HTTP client with standard TLS validation and explicit failures. */
public final class JsonHttpClient {
    private static final String JSON_CONTENT_TYPE = "application/json; charset=UTF-8";
    private static final String FORM_CONTENT_TYPE =
            "application/x-www-form-urlencoded; charset=UTF-8";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final Duration requestTimeout;

    /** Creates a client from explicit, reusable HTTP and JSON dependencies. */
    public JsonHttpClient(
            HttpClient httpClient, ObjectMapper objectMapper, Duration requestTimeout) {
        this.httpClient = Objects.requireNonNull(httpClient, "httpClient");
        this.objectMapper = Objects.requireNonNull(objectMapper, "objectMapper");
        this.requestTimeout = requirePositive(requestTimeout, "requestTimeout");
    }

    /** Creates a client that never follows redirects and uses platform TLS validation. */
    public static JsonHttpClient create(Duration connectTimeout, Duration requestTimeout) {
        HttpClient client =
                HttpClient.newBuilder()
                        .connectTimeout(requirePositive(connectTimeout, "connectTimeout"))
                        .followRedirects(HttpClient.Redirect.NEVER)
                        .build();
        return new JsonHttpClient(
                client, new ObjectMapper().findAndRegisterModules(), requestTimeout);
    }

    /** Sends a GET request and deserializes a successful JSON response. */
    public <T> T get(URI uri, Class<T> responseType) throws IOException, InterruptedException {
        HttpRequest request =
                HttpRequest.newBuilder(requireHttpUri(uri)).timeout(requestTimeout).GET().build();
        return send(request, responseType);
    }

    /** Sends a JSON POST request and deserializes a successful JSON response. */
    public <T> T post(URI uri, Object requestBody, Class<T> responseType)
            throws IOException, InterruptedException {
        return objectMapper.readValue(postForText(uri, requestBody), responseType);
    }

    /** Sends a JSON POST request and returns the successful response body. */
    public String postForText(URI uri, Object requestBody)
            throws IOException, InterruptedException {
        Objects.requireNonNull(requestBody, "requestBody");
        String json = objectMapper.writeValueAsString(requestBody);
        HttpRequest request =
                HttpRequest.newBuilder(requireHttpUri(uri))
                        .timeout(requestTimeout)
                        .header("Content-Type", JSON_CONTENT_TYPE)
                        .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                        .build();
        return sendForText(request);
    }

    /** Sends a URL-encoded form and returns the successful response body. */
    public String postForm(URI uri, Map<String, String> values)
            throws IOException, InterruptedException {
        Objects.requireNonNull(values, "values");
        String body =
                values.entrySet().stream()
                        .map(
                                entry ->
                                        encode(
                                                        Objects.requireNonNull(
                                                                entry.getKey(),
                                                                "Form keys must not be null"))
                                                + "="
                                                + encode(Objects.toString(entry.getValue(), "")))
                        .collect(Collectors.joining("&"));
        HttpRequest request =
                HttpRequest.newBuilder(requireHttpUri(uri))
                        .timeout(requestTimeout)
                        .header("Content-Type", FORM_CONTENT_TYPE)
                        .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                        .build();
        return sendForText(request);
    }

    /** Sends a caller-provided request and deserializes a successful JSON response. */
    public <T> T send(HttpRequest request, Class<T> responseType)
            throws IOException, InterruptedException {
        Objects.requireNonNull(responseType, "responseType");
        String responseBody = sendForText(request);
        return objectMapper.readValue(responseBody, responseType);
    }

    /** Sends a caller-provided request and returns a successful response body. */
    public String sendForText(HttpRequest request) throws IOException, InterruptedException {
        Objects.requireNonNull(request, "request");
        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new HttpStatusException(response.statusCode(), response.body());
        }
        return response.body();
    }

    private static URI requireHttpUri(URI uri) {
        Objects.requireNonNull(uri, "uri");
        if (!"http".equalsIgnoreCase(uri.getScheme())
                && !"https".equalsIgnoreCase(uri.getScheme())) {
            throw new IllegalArgumentException("Only HTTP and HTTPS URIs are supported");
        }
        return uri;
    }

    private static Duration requirePositive(Duration value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isZero() || value.isNegative()) {
            throw new IllegalArgumentException(name + " must be positive");
        }
        return value;
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
