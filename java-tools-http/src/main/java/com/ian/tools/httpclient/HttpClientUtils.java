package com.ian.tools.httpclient;

import io.github.iankingh.javatools.http.JsonHttpClient;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.HashMap;

/**
 * @deprecated Use {@link JsonHttpClient}. The replacement always performs normal TLS certificate
 *     and hostname validation.
 */
@Deprecated(forRemoval = true, since = "1.0")
public final class HttpClientUtils {
    private final JsonHttpClient client;

    public HttpClientUtils() {
        this(JsonHttpClient.create(Duration.ofSeconds(10), Duration.ofSeconds(30)));
    }

    HttpClientUtils(JsonHttpClient client) {
        this.client = client;
    }

    /**
     * @deprecated Use {@link JsonHttpClient#post(URI, Object, Class)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public String send(String url, HashMap<?, ?> request) throws IOException {
        try {
            return client.postForText(URI.create(url), request);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IOException("HTTP request was interrupted", exception);
        }
    }
}
