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

package com.github.alexdlaird.operation;

import com.github.alexdlaird.component.FinicityPersister;
import com.github.alexdlaird.component.Token;
import com.github.alexdlaird.component.rest.RestClient;
import org.simpleframework.xml.Serializer;

abstract class DefaultOperations {
    /**
     * Serializer for parsing XML strings.
     */
    protected final Serializer serializer;

    /**
     * REST client for operations.
     */
    protected final RestClient restClient;

    /**
     * Finicity appKey.
     */
    private final String appKey;

    /**
     * Finicity authentication token.
     */
    private Token token;

    /**
     * Construct a default {@link com.finicityclient.operation operations} instance.
     *
     * @param restClient A REST client for operations.
     * @param appKey     Finicity appKey.
     * @param token      Finicity authentication token.
     */
    public DefaultOperations(RestClient restClient, String appKey, Token token) {
        this.restClient = restClient;

        this.appKey = appKey;
        this.token = token;

        this.serializer = createSerializer();
    }

    /**
     * Update this client's authentication token.
     *
     * @param token The token to be updated.
     */
    public void refreshToken(Token token) {
        this.token = token;
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
}
