package io.github.iankingh.javatools.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonHttpClientTest {
    private HttpServer server;
    private JsonHttpClient client;
    private URI baseUri;

    @BeforeEach
    void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(0), 0);
        server.createContext("/json", exchange -> respond(exchange, 200, "{\"name\":\"Ian\"}"));
        server.createContext("/echo", exchange -> respond(exchange, 200, readBody(exchange)));
        server.createContext(
                "/form",
                exchange -> {
                    assertEquals(
                            "application/x-www-form-urlencoded; charset=UTF-8",
                            exchange.getRequestHeaders().getFirst("Content-Type"));
                    respond(exchange, 200, readBody(exchange));
                });
        server.createContext("/empty", exchange -> respond(exchange, 204, ""));
        server.createContext("/error", exchange -> respond(exchange, 422, "invalid"));
        server.createContext(
                "/latin",
                exchange -> {
                    byte[] bytes = "caf\u00e9".getBytes(StandardCharsets.ISO_8859_1);
                    exchange.getResponseHeaders()
                            .set("Content-Type", "text/plain; charset=ISO-8859-1");
                    exchange.sendResponseHeaders(200, bytes.length);
                    exchange.getResponseBody().write(bytes);
                    exchange.close();
                });
        server.start();
        baseUri = URI.create("http://127.0.0.1:" + server.getAddress().getPort());
        client = JsonHttpClient.create(Duration.ofSeconds(2), Duration.ofSeconds(2));
    }

    @AfterEach
    void stopServer() {
        server.stop(0);
    }

    @Test
    void getsAndPostsJson() throws Exception {
        assertEquals(new Payload("Ian"), client.get(baseUri.resolve("/json"), Payload.class));
        assertEquals(
                new Payload("posted"),
                client.post(baseUri.resolve("/echo"), new Payload("posted"), Payload.class));
    }

    @Test
    void postsFormsWithUtf8Encoding() throws Exception {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("message", "hello world");
        values.put("optional", null);

        assertEquals(
                "message=hello+world&optional=", client.postForm(baseUri.resolve("/form"), values));
    }

    @Test
    void supportsCallerProvidedRequestsAndEmptyResponses() throws Exception {
        HttpRequest request = HttpRequest.newBuilder(baseUri.resolve("/json")).GET().build();

        assertEquals(new Payload("Ian"), client.send(request, Payload.class));
        assertEquals(
                "",
                client.sendForText(
                        HttpRequest.newBuilder(baseUri.resolve("/empty")).GET().build()));
        assertEquals(
                "caf\u00e9",
                client.sendForText(
                        HttpRequest.newBuilder(baseUri.resolve("/latin")).GET().build()));
    }

    @Test
    void exposesNonSuccessResponsesWithoutSwallowingThem() {
        HttpStatusException exception =
                assertThrows(
                        HttpStatusException.class,
                        () -> client.get(baseUri.resolve("/error"), Payload.class));

        assertEquals(422, exception.statusCode());
        assertEquals("invalid", exception.responseBody());
        assertTrue(exception.getMessage().contains("422"));
    }

    @Test
    void validatesUrisTimeoutsAndArguments() {
        assertThrows(
                IllegalArgumentException.class,
                () -> JsonHttpClient.create(Duration.ZERO, Duration.ofSeconds(1)));
        assertThrows(
                IllegalArgumentException.class,
                () -> JsonHttpClient.create(Duration.ofSeconds(1), Duration.ZERO));
        assertThrows(
                IllegalArgumentException.class,
                () -> client.get(URI.create("file:///tmp/a"), Payload.class));
        assertThrows(
                NullPointerException.class, () -> client.postForm(baseUri.resolve("/form"), null));
        assertThrows(NullPointerException.class, () -> client.sendForText(null));
    }

    private static String readBody(HttpExchange exchange) throws IOException {
        return new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
    }

    private static void respond(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, status == 204 ? -1 : bytes.length);
        if (status != 204) {
            exchange.getResponseBody().write(bytes);
        }
        exchange.close();
    }

    private record Payload(String name) {}
}
