/**
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

package com.finicityclient.component.rest;

import com.finicityclient.component.FinicityPersister;
import com.finicityclient.component.StringUtils;
import com.finicityclient.component.Token;
import org.simpleframework.xml.Serializer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The default implementation of a {@link RestClient}.
 *
 * Each request has the "Finicity-App-Key" header added to it with the appKey. If a token exists, the
 * "Finicity-App-Token" header is also set with that value.
 *
 * If no body is given, the "Content-Length" header is set to 0.
 */
public class DefaultRestClient implements RestClient {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(DefaultRestClient.class));

    private static final String BASE_URL = "https://api.finicity.com/aggregation";

    private final Serializer serializer = new FinicityPersister();

    private final String appKey;

    private String encoding;

    private String contentType;

    private Token token = null;

    public DefaultRestClient(String appKey, String encoding, String contentType) {
        this.appKey = appKey;

        this.encoding = encoding;
        this.contentType = contentType;
    }

    private void ensureTokenIsValid() {
        if (token != null && System.currentTimeMillis() > token.getExpiration()) {
            throw new RestClientTokenExpiredException("The authentication token has expired and needs to be refreshed");
        }
    }

    private String getBodyString(Body body) throws Exception {
        if (body != null) {
            StringWriter stringWriter = new StringWriter();
            serializer.write(body, stringWriter);
            return stringWriter.toString();
        } else {
            return null;
        }
    }

    public void refreshToken(Token token) {
        this.token = token;
    }

    @Override
    public Response executeGet(String url, List<Parameter> parameters, Map<String, String> additionalHeaders) {
        ensureTokenIsValid();

        try {
            return execute(urlWithParameters(BASE_URL + url, parameters), null, "GET", additionalHeaders);
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "Rest client error", ex);

            throw new RestClientException("Rest client error", ex);
        }
    }

    @Override
    public Response executePost(String url, Body body, List<Parameter> parameters, Map<String, String> additionalHeaders) {
        ensureTokenIsValid();

        try {
            return execute(urlWithParameters(BASE_URL + url, parameters), getBodyString(body), "POST", additionalHeaders);
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "Rest client error", ex);

            throw new RestClientException("Rest client error", ex);
        }
    }

    @Override
    public Response executePut(String url, Body body, List<Parameter> parameters, Map<String, String> additionalHeaders) {
        ensureTokenIsValid();

        try {
            return execute(urlWithParameters(BASE_URL + url, parameters), getBodyString(body), "PUT", additionalHeaders);
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "Rest client error", ex);

            throw new RestClientException("Rest client error", ex);
        }
    }

    @Override
    public Response executeDelete(String url, List<Parameter> parameters, Map<String, String> additionalHeaders) {
        ensureTokenIsValid();

        try {
            return execute(urlWithParameters(BASE_URL + url, parameters), null, "DELETE", additionalHeaders);
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "Rest client error", ex);

            throw new RestClientException("Rest client error", ex);
        }
    }

    private String urlWithParameters(String url, List<Parameter> parameters) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();
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

    private Response execute(String url, String body, String method, Map<String, String> additionalHeaders) {
        HttpURLConnection httpUrlConnection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            httpUrlConnection = (HttpURLConnection) new URL(url).openConnection();
            httpUrlConnection.setRequestMethod(method);
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
                    msg = "An error occurred when performing the operation (" + httpUrlConnection.getResponseCode() + "): " + StringUtils.streamToString(httpUrlConnection.getErrorStream(), Charset.forName(encoding));
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

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
