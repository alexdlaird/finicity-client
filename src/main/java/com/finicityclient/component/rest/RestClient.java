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

package com.finicityclient.component.rest;

import java.util.List;
import java.util.Map;

/**
 * A REST client for executing HTTP operations against Finicity's API.
 */
public interface RestClient {
    /**
     * Perform GET operation against Finicity's API.
     *
     * @param url               The URL relative to the Finicity base URL on which to perform the operation.
     * @param parameters        An arbitrary number of parameters to add to the URL.
     * @param additionalHeaders Additional headers for the request.
     * @return The results of the query.
     */
    Response executeGet(String url, List<Parameter> parameters, Map<String, String> additionalHeaders);

    /**
     * Perform POST operation against Finicity's API.
     *
     * @param url               The URL relative to the Finicity base URL on which to perform the operation.
     * @param body              The element to be serialized into the request body.
     * @param parameters        An arbitrary number of parameters to add to the URL.
     * @param additionalHeaders Additional headers for the request.
     * @return The results of the query.
     */
    Response executePost(String url, Body body, List<Parameter> parameters, Map<String, String> additionalHeaders);

    /**
     * Perform PUT operation against Finicity's API.
     *
     * @param url               The URL relative to the Finicity base URL on which to perform the operation.
     * @param body              The element to be serialized into the request body.
     * @param parameters        An arbitrary number of parameters to add to the URL.
     * @param additionalHeaders Additional headers for the request.
     * @return The results of the query.
     */
    Response executePut(String url, Body body, List<Parameter> parameters, Map<String, String> additionalHeaders);

    /**
     * Perform DELETE operation against Finicity's API.
     *
     * @param url               The URL relative to the Finicity base URL on which to perform the operation.
     * @param parameters        An arbitrary number of parameters to add to the URL.
     * @param additionalHeaders Additional headers for the request.
     * @return The results of the query.
     */
    Response executeDelete(String url, List<Parameter> parameters, Map<String, String> additionalHeaders);

    class RestClientException extends RuntimeException {
        public RestClientException(String msg) {
            super(msg);
        }

        public RestClientException(String msg, Exception cause) {
            super(msg, cause);
        }
    }

    class RestClientTokenExpiredException extends RestClientException {
        public RestClientTokenExpiredException(String msg) {
            super(msg);
        }
    }
}
