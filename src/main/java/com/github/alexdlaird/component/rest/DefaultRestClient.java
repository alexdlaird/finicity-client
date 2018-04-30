/*
 * Copyright (c) 2016 Alex Laird
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.alexdlaird.component.rest;

import com.github.alexdlaird.component.FinicityPersister;
import com.github.alexdlaird.component.StringUtils;
import com.github.alexdlaird.component.Token;

import org.simpleframework.xml.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The default implementation of a {@link RestClient}. <p> Each request has the "Finicity-App-Key" header added to it
 * with the appKey. If a token exists, the "Finicity-App-Token" header is also set with that value. <p> If no body is
 * given, the "Content-Length" header is set to 0.
 */
public class DefaultRestClient implements RestClient {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(DefaultRestClient.class));

    /**
     * Base URL for the Finicity API.
     */
    private static final String BASE_URL = "https://api.finicity.com/aggregation";

    /**
     * Serializer for parsing requests and responses.
     */
    private final Serializer serializer;

    /**
     * Finicity appKey.
     */
    private final String appKey;

    /**
     * Default encoding for requests.
     */
    private String encoding;

    /**
     * Default contentType header for requests.
     */
    private String contentType;

    /**
     * Finicity authentication token.
     */
    private Token token = null;

    /**
     * Construct a default client that can perform basic REST operations.
     *
     * @param appKey      Finicity appKey.
     * @param encoding    Default encoding for the client.
     * @param contentType Default contentType header for the client.
     */
    public DefaultRestClient(final String appKey, final String encoding, final String contentType) {
        this.appKey = appKey;

        this.encoding = encoding;
        this.contentType = contentType;

        this.serializer = createSerializer();
    }

    private void ensureTokenIsValid() {
        if (token != null && System.currentTimeMillis() > token.getExpiration()) {
            throw new RestClientTokenExpiredException("The authentication token has expired and needs to be refreshed");
        }
    }

    private String getBodyString(final Body body) throws Exception {
        if (body != null) {
            final StringWriter stringWriter = new StringWriter();
            serializer.write(body, stringWriter);
            return stringWriter.toString();
        } else {
            return null;
        }
    }

    /**
     * Update this client's authentication token.
     *
     * @param token The token to be updated.
     */
    public void refreshToken(final Token token) {
        this.token = token;
    }

    @Override
    public Response executeGet(final String url, final List<Parameter> parameters,
                               final Map<String, String> additionalHeaders) {
        ensureTokenIsValid();

        try {
            return execute(urlWithParameters(BASE_URL + url, parameters), null, "GET",
                    additionalHeaders);
        } catch (Exception ex) {
            throw new RestClientException("Rest client error", ex);
        }
    }

    @Override
    public Response executePost(final String url, final Body body, final List<Parameter> parameters,
                                final Map<String, String> additionalHeaders) {
        ensureTokenIsValid();

        try {
            return execute(urlWithParameters(BASE_URL + url, parameters), getBodyString(body), "POST",
                    additionalHeaders);
        } catch (Exception ex) {
            throw new RestClientException("Rest client error", ex);
        }
    }

    @Override
    public Response executePut(final String url, final Body body, final List<Parameter> parameters,
                               final Map<String, String> additionalHeaders) {
        ensureTokenIsValid();

        try {
            return execute(urlWithParameters(BASE_URL + url, parameters), getBodyString(body), "PUT",
                    additionalHeaders);
        } catch (Exception ex) {
            throw new RestClientException("Rest client error", ex);
        }
    }

    @Override
    public Response executeDelete(final String url, final List<Parameter> parameters,
                                  final Map<String, String> additionalHeaders) {
        ensureTokenIsValid();

        try {
            return execute(urlWithParameters(BASE_URL + url, parameters), null, "DELETE",
                    additionalHeaders);
        } catch (Exception ex) {
            throw new RestClientException("Rest client error", ex);
        }
    }

    private String urlWithParameters(final String url, final List<Parameter> parameters)
            throws UnsupportedEncodingException {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);

        if (parameters != null && parameters.size() > 0) {
            boolean first = true;
            for (Parameter parameter : parameters) {
                if (!first) {
                    stringBuilder.append("&");
                } else {
                    stringBuilder.append("?");

                    first = false;
                }

                stringBuilder.append(URLEncoder.encode(parameter.getName(), encoding));
                stringBuilder.append("=");
                stringBuilder.append(URLEncoder.encode(parameter.getValue(), encoding));
            }
        }

        return stringBuilder.toString();
    }

    private Response execute(final String url, final String body, final String method,
                             final Map<String, String> additionalHeaders) {
        HttpURLConnection httpUrlConnection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            httpUrlConnection = createHttpUrlConnection(url);
            httpUrlConnection.setRequestMethod(method);

            appendFinicityDefaultsToConnection(httpUrlConnection, additionalHeaders);
            modifyConnection(httpUrlConnection);

            if (StringUtils.isNotBlank(body)) {
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.connect();

                outputStream = httpUrlConnection.getOutputStream();

                outputStream.write(body.getBytes(Charset.forName(encoding)));
            } else {
                httpUrlConnection.setRequestProperty("Content-Length", "0");
                httpUrlConnection.connect();
            }

            inputStream = httpUrlConnection.getInputStream();

            return new Response(httpUrlConnection.getResponseCode(),
                    StringUtils.streamToString(inputStream, Charset.forName(encoding)),
                    httpUrlConnection.getHeaderFields());
        } catch (Exception ex) {
            String msg = "An unknown error occurred when performing the operation";

            if (httpUrlConnection != null) {
                try {
                    String errorString = StringUtils.streamToString(httpUrlConnection.getErrorStream(), Charset.forName(encoding));

                    msg = "An error occurred when performing the operation (" + httpUrlConnection.getResponseCode() + "): " + errorString;
                } catch (IOException | NullPointerException ignored) {
                }
            }

            throw new RestClientException(msg, ex);
        } finally {
            if (httpUrlConnection != null) {
                httpUrlConnection.disconnect();
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                LOGGER.log(Level.INFO, "Unable to close connection", ex);
            }
        }
    }

    private void appendFinicityDefaultsToConnection(final HttpURLConnection httpUrlConnection,
                                                    final Map<String, String> additionalHeaders) {
        httpUrlConnection.setRequestProperty("Content-Type", contentType);
        httpUrlConnection.setRequestProperty("Finicity-App-Key", appKey);
        if (token != null) {
            httpUrlConnection.setRequestProperty("Finicity-App-Token", token.getToken());
        }
        if (additionalHeaders != null) {
            for (Map.Entry<String, String> entry : additionalHeaders.entrySet()) {
                httpUrlConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Override this method if you would like to implement a custom {@link Serializer}. If so, it is recommended you
     * extend {@link FinicityPersister}, as that properly handles Finicity enums.
     *
     * @return A {@link FinicityPersister} instance of a {@link Serializer}.
     */
    protected FinicityPersister createSerializer() {
        return new FinicityPersister();
    }

    /**
     * Override this method if you could like to extend {@link DefaultRestClient} and perform customer HTTP operations
     * before {@link HttpURLConnection#connect()} is called on the instance of the passed in connection.
     *
     * @param httpUrlConnection The URL connection to modify.
     */
    protected void modifyConnection(final HttpURLConnection httpUrlConnection) {
    }

    /**
     * Override this method if you could like to implement a custom URL connection.
     *
     * @param url The URL to connect to.
     * @return A URL connection.
     * @throws IOException An I/O exception has occurred.
     */
    protected HttpURLConnection createHttpUrlConnection(final String url) throws IOException {
        return (HttpURLConnection) new URL(url).openConnection();
    }

    /**
     * Set the default encoding. If this is only meant to modify the encoding for the next call (and no others), be sure
     * to set the encoding back to the default after the call has been executed.
     *
     * @param encoding The encoding string to set.
     */
    public void setEncoding(final String encoding) {
        this.encoding = encoding;
    }

    /**
     * Set the content type. If this is only meant to modify the content type for the next call (and no others), be sure
     * to set the encoding back to the default after the call has been executed.
     *
     * @param contentType The content type string to set.
     */
    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }
}
