package io.github.iankingh.javatools.http;

import java.io.IOException;

/** Indicates that an HTTP response had a non-success status code. */
public final class HttpStatusException extends IOException {
    private static final long serialVersionUID = 1L;

    private final int statusCode;
    private final String responseBody;

    /** Creates an exception from the HTTP status and response body. */
    public HttpStatusException(int statusCode, String responseBody) {
        super("HTTP request failed with status " + statusCode);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    /** Returns the non-success HTTP status code. */
    public int statusCode() {
        return statusCode;
    }

    /** Returns the response body exactly as received. */
    public String responseBody() {
        return responseBody;
    }
}
